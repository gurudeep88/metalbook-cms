package com.metalbook.cms.repository;

import com.metalbook.cms.model.Contact;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends
        JpaRepository<Contact, UUID> {

    boolean existsByEmail(String email);

    List<Contact> findAll(Specification<Contact> spec);

}
