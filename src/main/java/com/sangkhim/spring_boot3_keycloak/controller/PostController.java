package com.sangkhim.spring_boot3_keycloak.controller;

import com.sangkhim.spring_boot3_keycloak.model.dto.PostDTO;
import com.sangkhim.spring_boot3_keycloak.model.entity.Post;
import com.sangkhim.spring_boot3_keycloak.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class PostController {

  private final PostService service;

  @GetMapping("/v1/posts")
  public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) String title) {
    List<Post> list = service.getAllPosts(title);
    return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
  }

  @GetMapping("/v1/posts/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable("id") Long id) {
    Post entity = service.getById(id);
    return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
  }

  @PostMapping("/v1/posts")
  public ResponseEntity<Post> createOrUpdate(@Valid @RequestBody PostDTO postDTO) {
    Post updated = service.createOrUpdate(postDTO);
    return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
  }

  @DeleteMapping("/v1/posts/{id}")
  public void deleteById(@PathVariable("id") Long id) {
    service.deleteById(id);
  }
}
