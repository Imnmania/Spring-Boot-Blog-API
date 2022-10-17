package me.niloybiswas.spblog.serviceImpl;

import me.niloybiswas.spblog.dto.PostDTO;
import me.niloybiswas.spblog.entitiy.Category;
import me.niloybiswas.spblog.entitiy.Post;
import me.niloybiswas.spblog.entitiy.User;
import me.niloybiswas.spblog.exception.ResourceNotFoundException;
import me.niloybiswas.spblog.repository.CategoryRepo;
import me.niloybiswas.spblog.repository.PostRepo;
import me.niloybiswas.spblog.repository.UserRepo;
import me.niloybiswas.spblog.service.PostService;
import me.niloybiswas.spblog.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
//        post.setCreatedDate(new Date());
        post.setCreatedDate(DateUtil.getCurrentDate());
        post.setCategory(category);
        post.setUser(user);

        Post newPost = postRepo.save(post);
        return modelMapper.map(newPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());

        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
        postRepo.delete(post); // worked after adding orphanRemoval at Entity and @Transactional
//        postRepo.deleteById(post.getId());
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
    public PostDTO getPostById(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getPostsByCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
        List<Post> posts = postRepo.findByCategory(category);

        List<PostDTO> postDTOs = posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        return postDTOs;
    }

    @Override
    public List<PostDTO> getPostsByUser(Long userId) {
        final User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        final List<Post> posts =  postRepo.findByUser(user);
        final List<PostDTO> postDTOs = posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        return postDTOs;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        return null;
    }
}
