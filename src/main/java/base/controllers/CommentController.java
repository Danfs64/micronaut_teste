package base.controllers;

import base.models.Comment;
import base.dtos.CommentInputDto;
import base.dtos.CommentOutputDto;
import base.application.CommentAPL;
import base.application.PostAPL;
import base.exceptions.CommentNotFoundException;
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

@Controller("/comments")
@RequiredArgsConstructor(onConstructor_ = {@Inject})
@Validated
public class CommentController {
    private final PostAPL post_app;
    private final CommentAPL comment_app;

    @io.micronaut.http.annotation.Post(uri = "/", consumes = MediaType.APPLICATION_JSON)
    @Transactional
    public HttpResponse<Void> create(@Body @Valid CommentInputDto dto) {
        var post = post_app.findById(dto.post_id()).orElseThrow(() -> new PostNotFoundException(dto.post_id()));
        var data = Comment.builder()
            .post(post)
            .content(dto.content())
            .build();
        // post.getComments().add(data);
        var saved = this.comment_app.save(data);
        return HttpResponse.created(URI.create("/comments/" + saved.getId()));
    }

    @Get(uri = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<CommentOutputDto>> getAll() {
        var body = comment_app.findAll()
            .stream()
            .map(p -> new CommentOutputDto(p.getId(), p.getPost().getId(), p.getContent(), p.getCreatedAt()))
            .toList();
        return HttpResponse.ok(body);
    }

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<CommentOutputDto> getById(@PathVariable UUID id) {

        return comment_app.findById(id)
            .map(p -> HttpResponse.ok(new CommentOutputDto(p.getId(), p.getPost().getId(), p.getContent(), p.getCreatedAt())))
            .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Put(uri = "/{id}", consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<?> updateById(@PathVariable UUID id, @Body @Valid CommentInputDto dto) {
        return comment_app.findById(id)
            .map(p -> {
                p.setPost(post_app.findById(dto.post_id()).orElseThrow(() -> new PostNotFoundException(dto.post_id())));
                p.setContent(dto.content());
                this.comment_app.update(p);
                return HttpResponse.ok(dto);
            })
            .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    @Transactional
    public HttpResponse<?> deleteById(@PathVariable UUID id) {
        return comment_app.findById(id)
            .map(p -> {
                this.comment_app.delete(p);
                return HttpResponse.noContent();
            })
            .orElseThrow(() -> new CommentNotFoundException(id));
    }
}
