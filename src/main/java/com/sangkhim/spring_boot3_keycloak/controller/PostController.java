package com.sangkhim.spring_boot3_keycloak.controller;

import com.sangkhim.spring_boot3_keycloak.model.entity.Post;
import com.sangkhim.spring_boot3_keycloak.model.entity.Tag;
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
  public ResponseEntity<Post> createOrUpdate(@Valid @RequestBody Post post) {
    Post updated = service.createOrUpdate(post);
    return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
  }

  @GetMapping("/v1/posts/{id}/tags")
  public ResponseEntity<List<Tag>> getAllTagsByPostId(@PathVariable(value = "id") Long id) {
    List<Tag> tagList = service.getAllTagsByPostId(id);
    return new ResponseEntity<>(tagList, new HttpHeaders(), HttpStatus.OK);
  }

  @PostMapping("/v1/posts/{id}/tags")
  public ResponseEntity<Tag> addTag(@PathVariable("id") Long id, @RequestBody Tag tagRequest) {
    Tag updated = service.addTag(id, tagRequest);
    return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
  }

  @DeleteMapping("/v1/posts/{id}/tags/{tagId}")
  public void deleteTagFromPost(@PathVariable("id") Long id, @PathVariable("tagId") Long tagId) {
    service.deleteTagFromPost(id, tagId);
  }

  @DeleteMapping("/v1/posts/{id}")
  public void deleteById(@PathVariable("id") Long id) {
    service.deleteById(id);
  }
}
