package ms.inz.systemisaf.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class MeasurementDto {

    private Long id;
    private Long userId;       // Odniesienie do użytkownika przez ID, zamiast pełnej encji
    private Double weight;
    private Double height;
    private Double bmi;
    private LocalDateTime createdAt;
}