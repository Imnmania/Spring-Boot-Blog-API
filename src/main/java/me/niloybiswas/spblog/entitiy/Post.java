package me.niloybiswas.spblog.entitiy;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String content;

    private String imageName;

    @Column(nullable = false)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

