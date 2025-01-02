package ms.inz.systemisaf.services;

import ms.inz.systemisaf.dto.UserRegistrationDto;
import ms.inz.systemisaf.enums.RoleEnum;
import ms.inz.systemisaf.model.User;
import ms.inz.systemisaf.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(UserRegistrationDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email cannot be blank");
        }

        if (!userDto.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email must contain '@'");
        }
        if (userDto.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (userDto.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }

         if (!userDto.getRole().equals(RoleEnum.USER) && !userDto.getRole().equals(RoleEnum.TRAINER)) {
            throw new IllegalArgumentException("Invalid role");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());

        userRepository.save(user);
    }

}