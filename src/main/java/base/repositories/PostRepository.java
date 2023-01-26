package base.repositories;

import java.util.UUID;

import base.models.Post;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID>{

}
