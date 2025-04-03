package telran.java57.forum.posts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telran.java57.forum.posts.dto.*;
import telran.java57.forum.posts.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class PostController {

    final PostService postService;

    @PostMapping("/post/{author}")
    public PostDto addNewPost(@PathVariable String author, @RequestBody NewPostDto newPostDto) {
        return postService.addNewPost(author,newPostDto);
    }

    @GetMapping("/post/{postId}")
    public PostDto findPostById(@PathVariable String postId){
        return postService.findPostById(postId);
    }

    @PutMapping("/post/{postId}")
    public PostDto updatePost(@PathVariable String postId, @RequestBody NewPostDto newPostDto) {
        return postService.updatePost(postId,newPostDto);
    }

    @DeleteMapping("/post/{postId}")
    public PostDto deletePost(@PathVariable String postId) {
        return postService.deletePost(postId);
    }

    @GetMapping("/posts/author/{user}")
    public Iterable<PostDto> findPostsByAuthor(@PathVariable String user) {
        return postService.findPostsByAuthor(user);
    }

    @PutMapping("/post/{postId}/comment/{user}")
    public PostDto addComment(@PathVariable String postId, @PathVariable String user, @RequestBody NewCommentDto newCommentDto) {
        return postService.addComment(postId, user, newCommentDto);
    }

    @PostMapping("/posts/tags")
    public Iterable<PostDto> findPostsByTags(@RequestBody List<String> tags) {
        return postService.findPostsByTags(tags);
    }

    @PostMapping("/posts/period")
    public Iterable<PostDto> findPostsByPeriod(@RequestBody PeriodDto periodDto) {
        return postService.findPostsByPeriod(periodDto);
    }

    @PutMapping("/post/{postId}/like")
    public void addLike(@PathVariable String postId) {
        postService.addLike(postId);
    }
}
