package telran.java57.forum.posts.service;

import telran.java57.forum.posts.dto.*;

import java.util.List;

public interface PostService {

    PostDto addNewPost(String author, NewPostDto newPostDto);

    PostDto findPostById(String postId);

    PostDto updatePost(String postId, NewPostDto newPostDto);

    PostDto deletePost(String postId);

    Iterable<PostDto> findPostsByAuthor(String user);

    PostDto addComment(String postId, String user, NewCommentDto newCommentDto);

    Iterable<PostDto> findPostsByTags(List<String> tags);

    Iterable<PostDto> findPostsByPeriod(PeriodDto periodDto);

    void addLike(String postId);
}
