package ru.rosbank.javaschool.crudapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
  List<PostEntity> findAllByContentLike(String q);
}
