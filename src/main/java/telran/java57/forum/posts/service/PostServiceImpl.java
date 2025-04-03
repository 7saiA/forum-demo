package telran.java57.forum.posts.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import telran.java57.forum.posts.dao.PostRepository;
import telran.java57.forum.posts.dto.*;
import telran.java57.forum.posts.dto.exception.PostNotFoundException;
import telran.java57.forum.posts.model.Comment;
import telran.java57.forum.posts.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    final PostRepository postRepository;
    final ModelMapper modelMapper;

    @Override
    public PostDto addNewPost(String author, NewPostDto newPostDto) {
        Post post = new Post(newPostDto.getTitle(),newPostDto.getContent(),author,newPostDto.getTags());
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto findPostById(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePost(String postId, NewPostDto newPostDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        if (newPostDto.getTitle() != null) {
            post.setTitle(newPostDto.getTitle());
        }
        if(newPostDto.getTags() != null) {
            newPostDto.getTags().forEach(post::addTag);
        }
        if(newPostDto.getContent() != null) {
            post.setContent(newPostDto.getContent());
        }
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto deletePost(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        postRepository.deleteById(postId);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public Iterable<PostDto> findPostsByAuthor(String user) {
        return postRepository.findPostsByAuthorIgnoreCase(user)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }

    @Override
    public PostDto addComment(String postId, String user, NewCommentDto newCommentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        Comment comment = new Comment(newCommentDto.getMessage(),user);
        post.addComment(comment);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public Iterable<PostDto> findPostsByTags(List<String> tags) {
        return postRepository.findPostsByTagsIgnoreCaseIn(tags)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }

    @Override
    public Iterable<PostDto> findPostsByPeriod(PeriodDto periodDto) {
        return postRepository.findPostsByDateCreatedBetween(periodDto.getDateFrom(),periodDto.getDateTo())
                .map(p -> modelMapper.map(p,PostDto.class))
                .toList();
    }

    @Override
    public void addLike(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        post.addLike();
        postRepository.save(post);
    }
}
