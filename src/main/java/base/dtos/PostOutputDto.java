package base.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

public record PostOutputDto(
    @NotBlank UUID id,
    @NotBlank String title,
    @NotBlank String content,
    @PastOrPresent @NotNull LocalDateTime createdAt
) {}
