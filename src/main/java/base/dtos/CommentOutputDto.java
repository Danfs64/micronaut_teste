package base.dtos;

import java.util.UUID;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

public record CommentOutputDto(
    @NotBlank UUID id,
    @NotBlank UUID post_id,
    @NotBlank String content,
    @PastOrPresent @NotNull LocalDateTime createdAt
) {}
