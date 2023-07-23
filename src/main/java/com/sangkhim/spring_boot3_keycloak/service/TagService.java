package com.sangkhim.spring_boot3_keycloak.service;

import com.sangkhim.spring_boot3_keycloak.exception.BadRequestException;
import com.sangkhim.spring_boot3_keycloak.exception.DataNotFoundException;
import com.sangkhim.spring_boot3_keycloak.model.entity.Tag;
import com.sangkhim.spring_boot3_keycloak.repository.TagRepository;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

  private final TagRepository tagRepository;

  public List<Tag> getAllTags() {
    List<Tag> tagList = tagRepository.findAll();
    return tagList;
  }

  public Tag getById(Long id) {
    Optional<Tag> tag = tagRepository.findById(id);
    if (tag.isPresent()) {
      return tag.get();
    } else {
      throw new DataNotFoundException(
          MessageFormat.format("Tag id {0} not found", String.valueOf(id)));
    }
  }

  public Tag createOrUpdate(Tag tagRequest) {
    Optional<Tag> existingTag = tagRepository.findById(tagRequest.getId());

    if (existingTag.isPresent()) {
      Tag tagUpdate = existingTag.get();

      tagUpdate.setName(tagRequest.getName());

      return tagRepository.save(tagUpdate);
    } else {
      return tagRepository.save(tagRequest);
    }
  }

  public void deleteById(Long id) {
    Optional<Tag> tag = tagRepository.findById(id);
    if (tag.isPresent()) {
      tagRepository.deleteById(id);
    } else {
      throw new BadRequestException("Delete error, please check ID and try again");
    }
  }
}
