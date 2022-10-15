package me.niloybiswas.spblog.controller;

import me.niloybiswas.spblog.dto.PostDTO;
import me.niloybiswas.spblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create/{userId}/{categoryId}")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO,
                                              @PathVariable BigInteger userId,
                                              @PathVariable BigInteger categoryId) {
        PostDTO newPost = postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

}
