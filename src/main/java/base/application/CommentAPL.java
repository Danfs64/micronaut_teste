package base.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import base.models.Post;
import base.models.Comment;
import base.repositories.CommentRepository;
import io.micronaut.context.annotation.Prototype;
import lombok.RequiredArgsConstructor;

@Prototype
@RequiredArgsConstructor
public class CommentAPL {
    private final CommentRepository repo;

    public Comment save(Comment p) {
        return this.repo.save(p);
    }

    public List<Comment> findAll() {
        return this.repo.findAll();
    }
    
    public Optional<Comment> findById(UUID id) {
        return this.repo.findById(id);
    }
    
    public Comment update(Comment p) {
        return this.repo.update(p);
    }

    public void delete(Comment p) {
        this.repo.delete(p);
    }

    public List<Comment> findByPost(Post p) {
        return this.repo.findByPost(p);
    }
}
