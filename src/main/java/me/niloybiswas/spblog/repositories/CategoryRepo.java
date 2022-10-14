package me.niloybiswas.spblog.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.niloybiswas.spblog.entities.Category;


@Repository
public interface CategoryRepo extends JpaRepository<Category, BigInteger>{

}
