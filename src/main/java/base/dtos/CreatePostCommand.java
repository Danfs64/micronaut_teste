package base.dtos;

import javax.validation.constraints.NotBlank;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record CreatePostCommand(@NotBlank String title, @NotBlank String content) {
}
