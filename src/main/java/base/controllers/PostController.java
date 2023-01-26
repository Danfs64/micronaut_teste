package base.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import base.dtos.CreatePostCommand;
import base.dtos.PostDetailsDto;
import base.dtos.PostSummaryDto;
import base.exceptions.PostNotFoundException;
import base.models.Post;
import base.repositories.CommentRepository;
import base.repositories.PostRepository;

import io.micronaut.http.annotation.Put;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.validation.Validated;

import lombok.RequiredArgsConstructor;
import jakarta.inject.Inject;

@Controller("/posts")
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@Validated
public class PostController {
    private final PostRepository posts;
    private final CommentRepository comments;

    @Get(uri = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<PostSummaryDto>> getAll() {
        var body = posts.findAll()
            .stream()
            .map(p -> new PostSummaryDto(p.getId(), p.getTitle(), p.getCreatedAt()))
            .toList();
        return HttpResponse.ok(body);
    }

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> getById(@PathVariable UUID id) {

        return posts.findById(id)
            .map(p -> HttpResponse.ok(new PostDetailsDto(p.getId(), p.getTitle(), p.getContent(), p.getCreatedAt())))
            // .orElseGet(HttpResponse::notFound);
            .orElseThrow(() -> new PostNotFoundException(id));
    }

    @io.micronaut.http.annotation.Post(uri = "/", consumes = MediaType.APPLICATION_JSON)
    @Transactional
    public HttpResponse<Void> create(@Body @Valid CreatePostCommand dto) {
        var data = Post.builder()
            .title(dto.title())
            .content(dto.content())
            .build();
        var saved = this.posts.save(data);
        return HttpResponse.created(URI.create("/posts/" + saved.getId()));
    }

    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    @Transactional
    public HttpResponse<?> deleteById(@PathVariable UUID id) {
        return posts.findById(id)
            .map(p -> {
                this.posts.delete(p);
                return HttpResponse.noContent();
            })
            .orElseThrow(() -> new PostNotFoundException(id));
        //.orElseGet(HttpResponse::notFound);
    }

    @Put(uri = "/{id}", consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<?> updateById(@PathVariable UUID id, @Body @Valid CreatePostCommand dto) {
        return posts.findById(id)
            .map(p -> {
                p.setTitle(dto.title());
                p.setContent(dto.content());
                this.posts.update(p);
                return HttpResponse.ok(dto);
            })
            .orElseThrow(() -> new PostNotFoundException(id));
    }
}
