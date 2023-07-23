package com.sangkhim.spring_boot3_keycloak.service;

import com.sangkhim.spring_boot3_keycloak.exception.BadRequestException;
import com.sangkhim.spring_boot3_keycloak.exception.DataNotFoundException;
import com.sangkhim.spring_boot3_keycloak.model.entity.Tag;
import com.sangkhim.spring_boot3_keycloak.repository.TagRepository;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

  private final Logger LOG = LoggerFactory.getLogger(getClass());

  private final TagRepository tagRepository;

  @Cacheable(value = "tags")
  public List<Tag> getAllTags() {
    LOG.info("Getting tags.");

    List<Tag> tagList = tagRepository.findAll();
    return tagList;
  }

  @Cacheable(value = "tags", key = "#id")
  public Tag getById(Long id) {
    LOG.info("Getting tag with ID {}.", id);

    Optional<Tag> tag = tagRepository.findById(id);
    if (tag.isPresent()) {
      return tag.get();
    } else {
      throw new DataNotFoundException(
          MessageFormat.format("Tag id {0} not found", String.valueOf(id)));
    }
  }

  @CachePut(value = "tags", key = "#tag.id")
  public Tag createOrUpdate(Tag tagRequest) {
    LOG.info("Create or update tag with id {}", tagRequest.getId());

    Optional<Tag> existingTag = tagRepository.findById(tagRequest.getId());

    if (existingTag.isPresent()) {
      Tag tagUpdate = existingTag.get();

      tagUpdate.setName(tagRequest.getName());

      return tagRepository.save(tagUpdate);
    } else {
      return tagRepository.save(tagRequest);
    }
  }

  @CacheEvict(value = "tags", allEntries = true)
  public void deleteById(Long id) {
    LOG.info("Delete tag with id {}", id);

    Optional<Tag> tag = tagRepository.findById(id);
    if (tag.isPresent()) {
      tagRepository.deleteById(id);
    } else {
      throw new BadRequestException("Delete error, please check ID and try again");
    }
  }
}
