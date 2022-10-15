package me.niloybiswas.spblog.service;

import me.niloybiswas.spblog.dto.PostDTO;
import me.niloybiswas.spblog.entitiy.Category;
import me.niloybiswas.spblog.entitiy.Post;
import me.niloybiswas.spblog.entitiy.User;

import java.math.BigInteger;
import java.util.List;

public interface PostService {

    // create
    PostDTO createPost(PostDTO postDTO, BigInteger userId, BigInteger categoryId);

    // update
    PostDTO updatePost(PostDTO postDTO, BigInteger postId);

    // delete
    void deletePost(BigInteger postId);

    // get all
    List<PostDTO> getAllPosts();

    // get by id
    PostDTO getPostById(BigInteger postId);

    // get all by category
    List<PostDTO> getPostsByCategory(BigInteger categoryId);

    // get all by user
    List<PostDTO> getPostsByUser(BigInteger userId);

    // search
    List<PostDTO> searchPosts(String keyword);

}
