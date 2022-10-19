package me.niloybiswas.spblog.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.niloybiswas.spblog.dto.category.CategoryDTO;
import me.niloybiswas.spblog.dto.comment.CommentResponseDTO;
import me.niloybiswas.spblog.dto.user.UserDTO;

import javax.validation.constraints.NotEmpty;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String imageName;

    private String base64Image;

    private OffsetDateTime createdDate;

    private CategoryDTO category;

    private UserDTO user;

    private Set<CommentResponseDTO> comments = new HashSet<>();

}
