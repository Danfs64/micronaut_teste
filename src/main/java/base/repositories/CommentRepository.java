package base.repositories;

import java.util.List;
import java.util.UUID;

import base.models.Comment;
import base.models.Post;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByPost(Post post);
}
