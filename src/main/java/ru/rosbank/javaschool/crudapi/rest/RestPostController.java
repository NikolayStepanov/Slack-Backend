package ru.rosbank.javaschool.crudapi.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class RestPostController {
    private final PostService service;

    @GetMapping(params = {"from", "another"})
    public List<PostResponseDto> getSomePosts(@RequestParam int from, @RequestParam int another) {
        return service.getSomePosts(from, another);
    }

    @GetMapping(params = {"last"})
    public int getCountOfNewPosts(@RequestParam("last") int lastPostId) {
        return service.getCountOfNewPosts(lastPostId);
    }

    @GetMapping
    public int getLastPostId() {
        return service.getLastId();
    }

    @GetMapping(params = "q")
    public List<PostResponseDto> searchByContent(@RequestParam String q) {
        return service.searchByContent(q);
    }

    @PostMapping
    public PostResponseDto save(@RequestBody PostSaveRequestDto dto) {
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        service.removeById(id);
    }

    @PostMapping("/{id}/likes")
    public PostResponseDto likeById(@PathVariable int id) {
        return service.likeById(id);
    }

    @DeleteMapping("/{id}/likes")
    public PostResponseDto dislikeById(@PathVariable int id) {
        return service.dislikeById(id);
    }
}
