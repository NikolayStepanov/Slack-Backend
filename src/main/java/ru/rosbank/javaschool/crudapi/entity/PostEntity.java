package ru.rosbank.javaschool.crudapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String content;
  private String media;
  private boolean removed;
  private int likes;

  public static PostEntity from(PostSaveRequestDto dto) {
    return new PostEntity(dto.getId(), dto.getContent(), dto.getMedia(), false, 0);
  }
}
