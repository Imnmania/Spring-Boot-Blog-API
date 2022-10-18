package me.niloybiswas.spblog.serviceImpl;

import me.niloybiswas.spblog.dto.PaginatedResponseDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        Post updatedPost = postRepo.save(post);
        return modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
        postRepo.delete(post); // worked after adding orphanRemoval at Entity and @Transactional
//        postRepo.deleteById(post.getId());
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PaginatedResponseDTO<List<PostDTO>> getAllPosts(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir
    ) {
        /*
        /// Without pagination
        List<Post> allPosts = postRepo.findAll();
        List<PostDTO> allPostDTOs = allPosts.stream()
                    .map(post -> modelMapper.map(post, PostDTO.class))
                    .collect(Collectors.toList());
        return allPostDTOs;
        */
        /// With pagination
        PaginatedResponseDTO<List<PostDTO>> postResponse = new PaginatedResponseDTO<>();

        if (pageNumber > 0) {
            int newPageNumber = pageNumber - 1;

            ///* setting up sorting order
            Sort sort = sortDir.equalsIgnoreCase("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();

            ///* Pageable object helps us with generating pagination data
//            Pageable p = PageRequest.of(newPageNumber, pageSize, Sort.by(sortBy));
//            Pageable p = PageRequest.of(newPageNumber, pageSize, Sort.by(sortBy).descending());
            Pageable p = PageRequest.of(newPageNumber, pageSize, sort);
            Page<Post> pagePost = postRepo.findAll(p);
            List<Post> allPosts = pagePost.getContent();

            List<PostDTO> allPostDTOs = allPosts.stream()
                    .map(post -> modelMapper.map(post, PostDTO.class))
                    .collect(Collectors.toList());

            postResponse.setData(allPostDTOs);
            postResponse.setPageNumber(pagePost.getNumber() + 1);
            postResponse.setPageSize(pagePost.getSize());
            postResponse.setTotalElements(pagePost.getTotalElements());
            postResponse.setTotalPages(pagePost.getTotalPages());
            postResponse.setLastPage(pagePost.isLast());
        } else {
            throw new ResourceNotFoundException("Posts", "page number", Long.valueOf(pageNumber));
        }

        return postResponse;
    }

    @Override
    public PaginatedResponseDTO<List<PostDTO>> getPostsByCategory(
            Integer pageNumber,
            Integer pageSize,
            Long categoryId,
            String sortBy,
            String sortDir
    ) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
        /// Without pagination
        /*
        List<Post> posts = postRepo.findByCategory(category);
        List<PostDTO> postDTOs = posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        return postDTOs;
        */
        /// With pagination
        PaginatedResponseDTO<List<PostDTO>> paginatedResponse = new PaginatedResponseDTO<>();

        if (pageNumber > 0) {
            int newPageNumber = pageNumber - 1;
            Sort sort = sortDir.equalsIgnoreCase("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
            Pageable p = PageRequest.of(newPageNumber, pageSize, sort);
            Page<Post> pagePosts = postRepo.findAllByCategory(category, p);
            List<Post> posts = pagePosts.getContent();

            List<PostDTO> postDTOs = posts.stream()
                    .map(post -> modelMapper.map(post, PostDTO.class))
                    .collect(Collectors.toList());

            paginatedResponse.setData(postDTOs);
            paginatedResponse.setPageSize(pagePosts.getSize());
            paginatedResponse.setPageNumber(pagePosts.getNumber() + 1);
            paginatedResponse.setTotalElements(pagePosts.getTotalElements());
            paginatedResponse.setTotalPages(pagePosts.getTotalPages());
            paginatedResponse.setLastPage(pagePosts.isLast());
        } else {
            throw new ResourceNotFoundException("Posts", "page number", Long.valueOf(pageNumber));
        }

        return paginatedResponse;
    }

    @Override
    public PaginatedResponseDTO<List<PostDTO>> getPostsByUser(
            Integer pageNumber,
            Integer pageSize,
            Long userId,
            String sortBy,
            String sortDir
    ) {
        final User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        /// Without pagination
        /*
        final List<Post> posts =  postRepo.findByUser(user);
        final List<PostDTO> postDTOs = posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        return postDTOs;
        */
        /// With pagination
        PaginatedResponseDTO<List<PostDTO>> paginatedResponse = new PaginatedResponseDTO<>();

        if (pageNumber > 0) {
            int newPageNumber = pageNumber - 1;
            Sort sort = sortDir.equalsIgnoreCase("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
            Pageable p = PageRequest.of(newPageNumber, pageSize, sort);
            Page<Post> pagePosts = postRepo.findAllByUser(user, p);
            List<Post> posts = pagePosts.getContent();
            List<PostDTO> postDTOs = posts.stream()
                    .map(post -> modelMapper.map(post, PostDTO.class))
                    .collect(Collectors.toList());

            paginatedResponse.setData(postDTOs);
            paginatedResponse.setPageSize(pagePosts.getSize());
            paginatedResponse.setPageNumber(pagePosts.getNumber() + 1);
            paginatedResponse.setTotalPages(pagePosts.getTotalPages());
            paginatedResponse.setTotalElements(pagePosts.getTotalElements());
            paginatedResponse.setLastPage(pagePosts.isLast());
        } else {
            throw new ResourceNotFoundException("Posts", "page number", Long.valueOf(pageNumber));
        }

        return paginatedResponse;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
        List<PostDTO> postDTOs = posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        return postDTOs;
    }
}
