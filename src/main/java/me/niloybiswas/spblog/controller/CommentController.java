package me.niloybiswas.spblog.controller;

import me.niloybiswas.spblog.dto.common.ApiResponseDTO;
import me.niloybiswas.spblog.dto.comment.CommentRequestDTO;
import me.niloybiswas.spblog.dto.comment.CommentResponseDTO;
import me.niloybiswas.spblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentResponseDTO> createComment(
            @RequestBody @Valid CommentRequestDTO commentRequestDTO,
            @PathVariable(name = "postId") Long postId
    ) {
        CommentResponseDTO newCommentDTO = commentService.createComment(commentRequestDTO, postId);
        return new ResponseEntity<>(newCommentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponseDTO> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponseDTO("Comment successfully deleted!", true), HttpStatus.OK);
    }


}
