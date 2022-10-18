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
    PaginatedResponseDTO<List<PostDTO>> getAllPosts(
            Integer pageNumber,
            Integer PageSize,
            String sortBy,
            String sortDir
    );

    // get by id
    PostDTO getPostById(Long postId);

    // get all by category
    PaginatedResponseDTO<List<PostDTO>> getPostsByCategory(
            Integer pageNumber,
            Integer pageSize,
            Long categoryId,
            String sortBy,
            String sortDir
    );

    // get all by user
    PaginatedResponseDTO<List<PostDTO>> getPostsByUser(
            Integer pageNumber,
            Integer pageSize,
            Long userId,
            String sortBy,
            String sortDir
    );

    // search
    List<PostDTO> searchPosts(String keyword);

}
