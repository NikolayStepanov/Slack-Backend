package ru.rosbank.javaschool.crudapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CrudApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrudApiApplication.class, args);
  }

  @Bean
  public CommandLineRunner runner(PostRepository repository) {
    List<PostEntity> initialPosts = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      initialPosts.add(new PostEntity(0, String.valueOf(i), null, false, 0));
    }
    return args -> repository.saveAll(initialPosts);
  }

}
