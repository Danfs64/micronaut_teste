package base.dtos;

// import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostSummaryDto {
    UUID id;
    String title;
    LocalDateTime createdAt;
}
