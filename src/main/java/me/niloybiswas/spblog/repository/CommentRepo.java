package me.niloybiswas.spblog.repository;

import me.niloybiswas.spblog.entitiy.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
