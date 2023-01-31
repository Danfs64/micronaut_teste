package base.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import base.models.Post;
import base.repositories.PostRepository;
import io.micronaut.context.annotation.Prototype;
import lombok.RequiredArgsConstructor;

@Prototype
@RequiredArgsConstructor
public class PostAPL {
    private final PostRepository repo;

    public Post save(Post p) {
        return this.repo.save(p);
    }

    public List<Post> findAll() {
        return this.repo.findAll();
    }

    public Optional<Post> findById(UUID id) {
        return this.repo.findById(id);
    }

    public Post update(Post p) {
        return this.repo.update(p);
    }

    public void delete(Post p) {
        this.repo.delete(p);
    }
}
