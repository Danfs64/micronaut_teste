package base.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostDetailsDto {
    UUID id;
    String title;
    String content;
    LocalDateTime createdAt;
}
