package ru.rosbank.javaschool.crudapi.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rosbank.javaschool.crudapi.domain.UploadInfo;
import ru.rosbank.javaschool.crudapi.dto.UploadResponseDto;
import ru.rosbank.javaschool.crudapi.service.FileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class RestFileController {
  private final FileService service;

  @PostMapping("/multipart")
  public UploadResponseDto uploadMultipart(@RequestParam MultipartFile file) {
    return service.save(file);
  }

  @GetMapping("/entity/{id}")
  public ResponseEntity<Resource> getEntity(@PathVariable String id) {
    UploadInfo uploadInfo = service.get(id);
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(uploadInfo.getMimeType()))
        .body(uploadInfo.getResource());
  }
}
