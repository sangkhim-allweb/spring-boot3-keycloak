package com.sangkhim.spring_boot3_keycloak.controller;

import com.sangkhim.spring_boot3_keycloak.config.RateLimitConfig;
import com.sangkhim.spring_boot3_keycloak.exception.TooManyRequestsException;
import com.sangkhim.spring_boot3_keycloak.model.entity.Tag;
import com.sangkhim.spring_boot3_keycloak.service.TagService;
import com.sangkhim.spring_boot3_keycloak.utils.HttpUtils;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class TagController {

  private final RateLimitConfig rateLimitConfig;

  private final TagService service;

  @GetMapping("/v1/tags")
  public ResponseEntity<List<Tag>> getAllTags(HttpServletRequest request) {
    String ip = HttpUtils.getRequestIP(request);
    Bucket bucket = rateLimitConfig.resolveBucket("ip-" + ip);
    if (bucket.tryConsume(1)) {
      List<Tag> list = service.getAllTags();
      return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    } else {
      throw new TooManyRequestsException(
          MessageFormat.format("Rate limit exceeded for IP {0}", String.valueOf(ip)));
    }
  }

  @GetMapping("/v1/tags/{id}")
  public ResponseEntity<Tag> getTagById(@PathVariable("id") Long id) {
    Tag entity = service.getById(id);
    return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
  }

  @PostMapping("/v1/tags")
  public ResponseEntity<Tag> createOrUpdate(@RequestBody Tag Tag) {
    Tag updated = service.createOrUpdate(Tag);
    return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
  }

  @DeleteMapping("/v1/tags/{id}")
  public void deleteById(@PathVariable("id") Long id) {
    service.deleteById(id);
  }
}
