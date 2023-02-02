package base.controllers;

import base.models.Post;
import base.dtos.PostInputDto;
import base.dtos.PostOutputDto;
import base.application.PostAPL;
import base.exceptions.PostNotFoundException;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import jakarta.inject.Inject;
import javax.validation.Valid;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;

@Controller("/posts")
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@Validated
public class PostController {
    private final PostAPL post_app;

    @io.micronaut.http.annotation.Post(uri = "/", consumes = MediaType.APPLICATION_JSON)
    @Transactional
    public HttpResponse<Void> create(@Body @Valid PostInputDto dto) {
        var data = Post.builder()
            .title(dto.title())
            .content(dto.content())
            .build();
        var saved = this.post_app.save(data);
        return HttpResponse.created(URI.create("/posts/" + saved.getId()));
    }

    @Get(uri = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PostOutputDto>> getAll() {
        var body = post_app.findAll()
            .stream()
            .map(p -> new PostOutputDto(
                p.getId(),
                p.getTitle(),
                p.getContent(),
                // p.getComments().stream().map(elem -> elem.getId()).toList(),
                p.getCreatedAt()
            ))
            .toList();
        return HttpResponse.ok(body);
    }

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<PostOutputDto> getById(@PathVariable UUID id) {

        return post_app.findById(id)
            .map(p -> HttpResponse.ok(new PostOutputDto(
                p.getId(),
                p.getTitle(),
                p.getContent(),
                // p.getComments().stream().map(elem -> elem.getId()).toList(),
                p.getCreatedAt())
            ))
            .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Put(uri = "/{id}", consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<?> updateById(@PathVariable UUID id, @Body @Valid PostInputDto dto) {
        return post_app.findById(id)
            .map(p -> {
                p.setTitle(dto.title());
                p.setContent(dto.content());
                this.post_app.update(p);
                return HttpResponse.ok(dto);
            })
            .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    @Transactional
    public HttpResponse<?> deleteById(@PathVariable UUID id) {
        return post_app.findById(id)
            .map(p -> {
                this.post_app.delete(p);
                return HttpResponse.noContent();
            })
            .orElseThrow(() -> new PostNotFoundException(id));
    }
}
