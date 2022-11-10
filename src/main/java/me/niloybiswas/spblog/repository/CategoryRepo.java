package me.niloybiswas.spblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.niloybiswas.spblog.entitiy.Category;


@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>{

}
