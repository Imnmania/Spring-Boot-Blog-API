package me.niloybiswas.spblog.repository;

import me.niloybiswas.spblog.entitiy.Category;
import me.niloybiswas.spblog.entitiy.Post;
import me.niloybiswas.spblog.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, BigInteger> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

}
