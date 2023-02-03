package base.dtos;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

public record PostOutputDto(
    @NotBlank UUID id,
    @NotBlank String title,
    @NotBlank String content,
    @NotNull List<UUID> comment_ids,
    @PastOrPresent @NotNull LocalDateTime createdAt
) {}
