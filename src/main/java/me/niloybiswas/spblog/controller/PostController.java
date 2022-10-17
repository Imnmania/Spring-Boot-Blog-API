package me.niloybiswas.spblog.controller;

import me.niloybiswas.spblog.dto.ApiResponseDTO;
import me.niloybiswas.spblog.dto.PaginatedResponseDTO;
import me.niloybiswas.spblog.dto.PostDTO;
import me.niloybiswas.spblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    @Qualifier("postServiceImpl")
    private PostService postService;

    @PostMapping("/create/{userId}/{categoryId}")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO,
                                              @PathVariable Long userId,
                                              @PathVariable Long categoryId) {
        PostDTO newPost = postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<ApiResponseDTO> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponseDTO("Successfully Deleted!", true), HttpStatus.OK);
    }

    /// We are using query parameter to get those values which in spring boot is @RequestParam
    @GetMapping("/getAll")
    public ResponseEntity<PaginatedResponseDTO<List<PostDTO>>> getAllPosts(
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false)  Integer pageNumber
    ) {
        PaginatedResponseDTO<List<PostDTO>> paginatedPosts = postService.getAllPosts(pageNumber, pageSize);
        return new ResponseEntity<>(paginatedPosts, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PostDTO> getById(@PathVariable Long id) {
        PostDTO post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<PostDTO>> getAllByUserId(@PathVariable Long userId) {
        List<PostDTO> posts = postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/getByCategoryId/{categoryId}")
    public ResponseEntity<List<PostDTO>> getAllByCategoryId(@PathVariable Long categoryId) {
        List<PostDTO> posts = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable(name = "id") Long postId) {
        PostDTO updatedPostDTO =  this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<>(updatedPostDTO, HttpStatus.OK);
    }

}
