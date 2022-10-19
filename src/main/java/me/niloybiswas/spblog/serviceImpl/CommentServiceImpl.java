package me.niloybiswas.spblog.serviceImpl;

import me.niloybiswas.spblog.dto.comment.CommentRequestDTO;
import me.niloybiswas.spblog.dto.comment.CommentResponseDTO;
import me.niloybiswas.spblog.entitiy.Comment;
import me.niloybiswas.spblog.entitiy.Post;
import me.niloybiswas.spblog.entitiy.User;
import me.niloybiswas.spblog.exception.ResourceNotFoundException;
import me.niloybiswas.spblog.repository.CommentRepo;
import me.niloybiswas.spblog.repository.PostRepo;
import me.niloybiswas.spblog.repository.UserRepo;
import me.niloybiswas.spblog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO, Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() ->  new ResourceNotFoundException("Post", "ID", postId));

        // TODO: Add proper user for comments (Get current user from access token)
        User user = userRepo.findById(post.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "user id", post.getUser().getId()));

        Comment comment = modelMapper.map(commentRequestDTO, Comment.class);
        comment.setPost(post);
        comment.setUser(user);

        Comment newComment = commentRepo.saveAndFlush(comment);

        return modelMapper.map(newComment, CommentResponseDTO.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
        commentRepo.delete(comment);
    }
}
