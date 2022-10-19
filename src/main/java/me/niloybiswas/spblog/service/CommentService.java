package me.niloybiswas.spblog.service;

import me.niloybiswas.spblog.dto.comment.CommentRequestDTO;
import me.niloybiswas.spblog.dto.comment.CommentResponseDTO;

public interface CommentService {

    CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO, Long postId);

    void deleteComment(Long commentId);

}
