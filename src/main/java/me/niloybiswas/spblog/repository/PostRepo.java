package me.niloybiswas.spblog.repository;

import me.niloybiswas.spblog.entitiy.Category;
import me.niloybiswas.spblog.entitiy.Post;
import me.niloybiswas.spblog.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    void delete(Post post);

    /// This is how you do raw query operation
//    @Modifying
//    @Query("DELETE FROM posts WHERE id = :id")
//    void deleteById(@Param("id") Long id);
}
