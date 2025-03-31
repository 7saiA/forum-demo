package telran.java57.forum.posts.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.java57.forum.posts.model.Post;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

public interface PostRepository extends MongoRepository<Post, String> {

    Stream<Post> findPostsByAuthorIgnoreCase(String user);

    Stream<Post> findPostsByTagsIgnoreCaseIn(Set<String> tags);

    Stream<Post> findPostsByDateCreatedBetween(LocalDateTime from, LocalDateTime to);
}
