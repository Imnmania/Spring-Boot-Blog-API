package me.niloybiswas.spblog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private BigInteger id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String imageName = "default.png";

    private Date createdDate;

    private CategoryDTO category;

    private UserDTO user;

}
