package me.niloybiswas.spblog.controller;

import me.niloybiswas.spblog.config.AppConstants;
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
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)  Integer pageNumber,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PaginatedResponseDTO<List<PostDTO>> paginatedPosts = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(paginatedPosts, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PostDTO> getById(@PathVariable Long id) {
        PostDTO post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/getAllByUserId/{userId}")
    public ResponseEntity<PaginatedResponseDTO<List<PostDTO>>> getAllByUserId(
            @PathVariable Long userId,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)  Integer pageNumber,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PaginatedResponseDTO<List<PostDTO>> paginatedPosts = postService.getPostsByUser(pageNumber, pageSize, userId, sortBy, sortDir);
        return new ResponseEntity<>(paginatedPosts, HttpStatus.OK);
    }

    @GetMapping("/getAllByCategoryId/{categoryId}")
    public ResponseEntity<PaginatedResponseDTO<List<PostDTO>>> getAllByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)  Integer pageNumber,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PaginatedResponseDTO<List<PostDTO>> paginatedPosts = postService.getPostsByCategory(pageNumber, pageSize, categoryId, sortBy, sortDir);
        return new ResponseEntity<>(paginatedPosts, HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable(name = "id") Long postId) {
        PostDTO updatedPostDTO =  this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<>(updatedPostDTO, HttpStatus.OK);
    }

    //* Search
    @GetMapping("/search/{keyword}")
    public ResponseEntity<PaginatedResponseDTO<List<PostDTO>>> searchPostsByTitle(
            @PathVariable(name = "keyword") String keyword,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir

    ) {
        PaginatedResponseDTO<List<PostDTO>> postDTOs = this.postService.searchPosts(keyword, pageSize, pageNumber, sortBy, sortDir);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

}
