package base.dtos;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record CommentInputDto(
    @NotBlank UUID post_id,
    @NotBlank String content
) {}
