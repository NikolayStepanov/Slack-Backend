package ru.rosbank.javaschool.crudapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;
import ru.rosbank.javaschool.crudapi.exception.BadRequestException;
import ru.rosbank.javaschool.crudapi.exception.NotFoundException;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostServiceTest {
    private PostRepository repoMock;
    private PostService service;

    @BeforeEach
    void setUp() {
        repoMock = mock(PostRepository.class);
        service = new PostService(repoMock);
    }

    @Test
    void getCountOfNewPosts() {
        when(repoMock.findById(1)).thenReturn(Optional.of(new PostEntity(1, "content1", null, false, 0)));
        when(repoMock.findAll()).thenReturn(List.of(
                new PostEntity(1, "content1", null, false, 0),
                new PostEntity(2, "content2", null, false, 0)
        ));

        assertEquals(1, service.getCountOfNewPosts(1));

    }

    @Test
    void getLastId() {
        when(repoMock.findAll()).thenReturn(List.of(
                new PostEntity(1, "content", null, false, 0),
                new PostEntity(2, "content", null, false, 0),
                new PostEntity(3, "content", null, false, 0)
        ));

        assertEquals(3, service.getLastId());
    }

    @Test
    void getAll() {
        when(repoMock.findAll()).thenReturn(List.of(new PostEntity(1, "", null, false, 0)));
        assertIterableEquals(List.of(new PostResponseDto(1, "", null, 0)), service.getAll());
    }

    @Test
    void getSomePostsWhenOutOfRange() {
        when(repoMock.findAll()).thenReturn(List.of(new PostEntity(1, "", null, false, 0)));

        assertIterableEquals(List.of(new PostResponseDto(1, "", null, 0)), service.getSomePosts(0, 1));
    }

    @Test
    void getSomePostsNotFound() {
        when(repoMock.findAll()).thenReturn(List.of(new PostEntity(1, "", null, false, 0)));

        assertEquals(Collections.emptyList(), service.getSomePosts(2, 5));
    }

    @Test
    void save() {
        when(repoMock.save(any(PostEntity.class))).thenReturn(new PostEntity());

        assertEquals(new PostResponseDto(), service.save(new PostSaveRequestDto()));
    }

    @Test
    void searchByContent() {
        when(repoMock.findAllByContentLike(anyString())).thenReturn(List.of(
                new PostEntity(1, "", null, false, 0)
        ));

        assertIterableEquals(List.of(new PostResponseDto(1, "", null, 0)), service.searchByContent("content"));
    }

    @Test
    void likeById() {
        when(repoMock.findById(anyInt())).thenReturn(Optional.of(new PostEntity(1, "content", null, false, 0)));

        assertEquals(1, service.likeById(1).getLikes());
    }

    @Test
    void likeByIdThrowExcept() {
        when(repoMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> service.likeById(1));
    }

    @Test
    void dislikeById() {
        when(repoMock.findById(anyInt())).thenReturn(Optional.of(new PostEntity(1, "content", null, false, 1)));

        assertEquals(0, service.dislikeById(1).getLikes());
    }

    @Test
    void dislikeByIdThrowExcept() {
        when(repoMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> service.dislikeById(1));
    }

    @Test
    void removeById() {
        service.removeById(1);
    }
}