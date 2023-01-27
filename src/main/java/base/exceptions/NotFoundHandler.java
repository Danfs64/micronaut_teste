package base.exceptions;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Produces
@Singleton
@Requires(classes = { NotFoundException.class })
@RequiredArgsConstructor
public class NotFoundHandler implements ExceptionHandler<NotFoundException, HttpResponse<?>> {
    private final ErrorResponseProcessor<?> errorResponseProcessor;

    @Override
    public HttpResponse<?> handle(HttpRequest request, NotFoundException exception) {
        return errorResponseProcessor.processResponse(
            ErrorContext.builder(request)
                .cause(exception)
                .errorMessage(exception.getMessage())
                .build(),
            HttpResponse.notFound()
        );
    }
}
