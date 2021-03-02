package com.api.v1.controller;


import com.api.v1.exception.ResourceNotFoundException;
import com.api.v1.model.Post;
import com.api.v1.model.User;
import com.api.v1.repository.PostRepository;
import com.api.v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")

public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable(value = "id") Long postId)
            throws ResourceNotFoundException {
        Post post =
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new ResourceNotFoundException("Post not found on :: " + postId));
        return ResponseEntity.ok().body(post);
    }
    @PostMapping("/posts")
    public Post createPost(@Valid @RequestBody Post post) {
        return postRepository.save(post);
    }
    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable(value = "id") Long postId, @Valid @RequestBody Post postDetails)
            throws ResourceNotFoundException {

        Post post =
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new ResourceNotFoundException("Post not found on :: " + postId));

        post.setData(postDetails.getData());
        post.setPrivacy(postDetails.getPrivacy());
        post.setUpdatedAt(new Date());
        final Post updatedPost = postRepository.save(post);
        return ResponseEntity.ok(updatedPost);
    }
    @DeleteMapping("/user/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long postId) throws Exception {
        Post post =
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new ResourceNotFoundException("Post not found on :: " + postId));

        postRepository.delete(post);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
