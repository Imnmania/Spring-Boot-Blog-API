package me.niloybiswas.spblog.service;

import me.niloybiswas.spblog.dto.PaginatedResponseDTO;
import me.niloybiswas.spblog.dto.PostDTO;

import java.util.List;

public interface PostService {

    // create
    PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId);

    // update
    PostDTO updatePost(PostDTO postDTO, Long postId);

    // delete
    void deletePost(Long postId);

    // get all
    PaginatedResponseDTO<List<PostDTO>> getAllPosts(Integer pageNumber, Integer PageSize);

    // get by id
    PostDTO getPostById(Long postId);

    // get all by category
    List<PostDTO> getPostsByCategory(Long categoryId);

    // get all by user
    List<PostDTO> getPostsByUser(Long userId);

    // search
    List<PostDTO> searchPosts(String keyword);

}
