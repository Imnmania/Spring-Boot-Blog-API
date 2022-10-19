package me.niloybiswas.spblog.dto.comment;

import lombok.*;
import me.niloybiswas.spblog.dto.user.UserDTO;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {

    private Long id;

    @NotEmpty
    private String content;

    private UserDTO user;
}
