package ru.rosbank.javaschool.crudapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;
import ru.rosbank.javaschool.crudapi.exception.BadRequestException;
import ru.rosbank.javaschool.crudapi.exception.NotFoundException;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;

    public List<PostResponseDto> getSomePosts(int from, int another) {
        return repository.findAll().stream()
                .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
                .skip(from)
                .limit(another)
                .map(PostResponseDto::from)
                .collect(Collectors.toList());
    }

    public int getCountOfNewPosts(int firstPostId) {
        return (int) repository.findAll().stream()
                .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
                .takeWhile(o -> o.getId() != firstPostId)
                .count();
    }

    public int getLastId() {
        return repository.findAll().stream()
                .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
                .limit(1)
                .findAny()
                .orElseThrow(NotFoundException::new)
                .getId();
    }

    public List<PostResponseDto> getAll() {
        return repository.findAll().stream()
                .map(PostResponseDto::from)
                .collect(Collectors.toList());
    }

    public PostResponseDto save(PostSaveRequestDto dto) {
        return PostResponseDto.from(repository.save(PostEntity.from(dto)));
    }

    public void removeById(int id) {
        repository.deleteById(id);
    }

    public List<PostResponseDto> searchByContent(String q) {
        return repository.findAllByContentLike(q).stream()
                .map(PostResponseDto::from)
                .collect(Collectors.toList());
    }

    public PostResponseDto likeById(int id) {
        final PostEntity entity = repository.findById(id)
                .orElseThrow(BadRequestException::new);
        entity.setLikes(entity.getLikes() + 1);
        return PostResponseDto.from(entity);
    }

    public PostResponseDto dislikeById(int id) {
        final PostEntity entity = repository.findById(id)
                .orElseThrow(BadRequestException::new);
        entity.setLikes(entity.getLikes() - 1);
        return PostResponseDto.from(entity);
    }
}
