package me.niloybiswas.spblog.service.impl;

import me.niloybiswas.spblog.dto.PostDTO;
import me.niloybiswas.spblog.entitiy.Category;
import me.niloybiswas.spblog.entitiy.Post;
import me.niloybiswas.spblog.entitiy.User;
import me.niloybiswas.spblog.exception.ResourceNotFoundException;
import me.niloybiswas.spblog.repository.CategoryRepo;
import me.niloybiswas.spblog.repository.PostRepo;
import me.niloybiswas.spblog.repository.UserRepo;
import me.niloybiswas.spblog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public PostDTO createPost(PostDTO postDTO, BigInteger userId, BigInteger categoryId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setCreatedDate(new Date());
        post.setCategory(category);
        post.setUser(user);

        Post newPost = postRepo.save(post);
        return modelMapper.map(newPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, BigInteger postId) {
        return null;
    }

    @Override
    public void deletePost(BigInteger postId) {

    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> allPosts = postRepo.findAll();
        List<PostDTO> allPostDTOs = allPosts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        return allPostDTOs;
    }

    @Override
    public PostDTO getPostById(BigInteger postId) {
        return null;
    }

    @Override
    public List<PostDTO> getPostsByCategory(BigInteger categoryId) {
        return null;
    }

    @Override
    public List<PostDTO> getPostsByUser(BigInteger userId) {
        return null;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        return null;
    }
}
