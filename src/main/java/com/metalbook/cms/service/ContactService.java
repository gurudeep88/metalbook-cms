package com.metalbook.cms.service;

import com.metalbook.cms.dto.request.ContactRequestDTO;
import com.metalbook.cms.dto.response.ContactResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ContactService {
    ContactResponseDTO createContact(ContactRequestDTO request);
    ContactResponseDTO getContact(UUID id);
    ContactResponseDTO updateContact(UUID id, ContactRequestDTO request);
    List<ContactResponseDTO> searchContacts(String query);
    ContactResponseDTO deleteContact(UUID id);
}
