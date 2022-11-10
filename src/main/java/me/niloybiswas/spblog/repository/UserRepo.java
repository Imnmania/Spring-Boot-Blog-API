package me.niloybiswas.spblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.niloybiswas.spblog.entitiy.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);

}
