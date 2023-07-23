package com.sangkhim.spring_boot3_keycloak.repository;

import com.sangkhim.spring_boot3_keycloak.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {}
