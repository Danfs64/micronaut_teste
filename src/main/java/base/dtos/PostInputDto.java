package base.dtos;

import javax.validation.constraints.NotBlank;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record PostInputDto(
    @NotBlank String title,
    @NotBlank String content
) {}
