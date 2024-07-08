package com.social_network_Abbey.service;

import com.social_network_Abbey.dto.PostDTO;
import com.social_network_Abbey.dto.UserDTO;
import com.social_network_Abbey.entity.ApplicationUser;
import com.social_network_Abbey.entity.Follow;
import com.social_network_Abbey.entity.Post;
import com.social_network_Abbey.repository.ApplicationUserRepository;
import com.social_network_Abbey.repository.FollowRepository;
import com.social_network_Abbey.repository.PostRepository;
import com.social_network_Abbey.response.CustomResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtService jwtService;

    private final PostRepository postRepository;
    private final ApplicationUserRepository userRepository;
    private final FollowRepository followRepository;

    @Autowired
    public PostService(PostRepository postRepository, ApplicationUserRepository userRepository, FollowRepository followRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    // Create a new Post object
    public CustomResponse<Object> createPost(Post postRequest) {
        var token = jwtService.getJwtFromRequest(request);
        var loggedInUser = jwtService.extractUsername(token);

        ApplicationUser user = userRepository.findByUsername(loggedInUser).orElse(null);
        if (user == null) {
            return new CustomResponse<>(false, true, "User not found", HttpStatus.NOT_FOUND.value());
        }

        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setPostImage(postRequest.getPostImage());
        post.setAuthor(user);

//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setAuthorId(user.getId());
//        postDTO.setContent(post.getContent());
//        postDTO.setPostImage(post.getPostImage());
        postRepository.save(post);
        return new CustomResponse<>(true, false, String.valueOf(post), HttpStatus.CREATED.value());
    }

    public CustomResponse<Object> deletePost(Long id) {
        try {
            postRepository.deleteById(id);
            return new CustomResponse<>(true, false, "post deletion successful", HttpStatus.OK.value());
        } catch (Exception e) {
            return new CustomResponse<>(false, true, "deletion failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            return null;
        }

        List<PostDTO> allPosts = new ArrayList<>();

        for (Post post : posts) {
            PostDTO dtoPost = new PostDTO();
            dtoPost.setId(post.getId());
            dtoPost.setPostImage(post.getPostImage());
            dtoPost.setAuthor_Id(post.getAuthor().getId());
            dtoPost.setContent(post.getContent());
            dtoPost.setCreatedAt(post.getCreatedAt());
            allPosts.add(dtoPost);
        }

        return allPosts;
    }

    public CustomResponse<Object> getPostById(Long id) {
        return postRepository.findById(id)
                .map(post -> new CustomResponse<>(true, false, String.valueOf(convertToPostDTO(post)), HttpStatus.OK.value()))
                .orElseGet(() -> new CustomResponse<>(false, true, "Post not found", HttpStatus.NOT_FOUND.value()));
    }

    public CustomResponse<Object> getAllPostsByUser(Long userId) {
        List<Post> posts = postRepository.findAllByAuthor_Id(userId);
        List<PostDTO> postDTOs = posts.stream().map(this::convertToPostDTO).collect(Collectors.toList());
        return new CustomResponse<>(true, false, String.valueOf(postDTOs), HttpStatus.OK.value());
    }

    public CustomResponse<Object> getPostsByUserFollowing(Long userId) {
        List<Follow> follows = followRepository.findByFollowerId(userId);
        List<Post> posts = new ArrayList<>();

        for (Follow follow : follows) {
            posts.addAll(postRepository.findAllByAuthor_Id(follow.getFollowing().getId()));
        }

        posts.sort(Comparator.comparing(Post::getId).reversed());

        if (posts.isEmpty()) {
            return new CustomResponse<>(false, true, "No posts found", HttpStatus.OK.value());
        }

        List<PostDTO> postDTOs = posts.stream().map(this::convertToPostDTO).collect(Collectors.toList());
        return new CustomResponse<>(true, false, String.valueOf(postDTOs), HttpStatus.OK.value());
    }

    private PostDTO convertToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setAuthor_Id(post.getAuthor().getId());
        postDTO.setContent(post.getContent());
        postDTO.setPostImage(post.getPostImage());
        return postDTO;
    }
}
