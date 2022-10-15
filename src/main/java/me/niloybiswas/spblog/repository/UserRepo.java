package me.niloybiswas.spblog.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.niloybiswas.spblog.entitiy.User;

@Repository
public interface UserRepo extends JpaRepository<User, BigInteger>{

}
