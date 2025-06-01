package com.metalbook.cms.service.Impl;

import com.metalbook.cms.exception.ApiException;
import com.metalbook.cms.exception.ContactNotFoundException;
import com.metalbook.cms.exception.EmailAlreadyExistsException;
import com.metalbook.cms.mapper.ContactMapper;
import com.metalbook.cms.model.Contact;
import com.metalbook.cms.dto.request.ContactRequestDTO;
import com.metalbook.cms.dto.response.ContactResponseDTO;
import com.metalbook.cms.repository.ContactRepository;
import com.metalbook.cms.service.ContactService;
import com.metalbook.cms.specification.ContactSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    @Override
    public ContactResponseDTO createContact(ContactRequestDTO contactRequestDTO) {
        if (contactRepository.existsByEmail(contactRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A Contact with the email "+contactRequestDTO.getEmail()+" already exist "
            );
        }
        Contact newContact = contactRepository.save(ContactMapper.mapToModel(contactRequestDTO));
        return ContactMapper.mapToDTO(newContact);
    }

    @Override
    public ContactResponseDTO getContact(UUID contactId) {
        Optional<Contact> contactOptional = contactRepository.findById(contactId);
        Contact contact = contactOptional
                .orElseThrow(() -> new ContactNotFoundException(
                        "Contact not found with ID: " + contactId
                ));
        log.info("Contact fetched successfully for {} ", contactId);
        return ContactMapper.mapToDTO(contact);
    }

    @Override
    public ContactResponseDTO updateContact(UUID contactId, ContactRequestDTO contactRequestDTO) {
        System.out.println("start contactRequestDTO");
        System.out.println(contactRequestDTO);
        System.out.println("end contactRequestDTO");
        if (contactRequestDTO.getFirstName() == null &&
                contactRequestDTO.getLastName() == null &&
                contactRequestDTO.getEmail() == null &&
                contactRequestDTO.getPhoneNumber() == null) {
            throw new ApiException("At least one field must be provided");
        }

        System.out.println("out contactRequestDTO");
        Optional<Contact> existingContactOptional = contactRepository.findById(contactId);
        Contact existingContact = existingContactOptional
                .orElseThrow(() -> new ContactNotFoundException(
                        "A Contact with the email "+contactRequestDTO.getEmail()+" already exist "
                ));
        if(contactRequestDTO.getFirstName()!= null) {
            existingContact.setFirstName(contactRequestDTO.getFirstName());
        }
        if(contactRequestDTO.getLastName()!= null) {
            existingContact.setLastName(contactRequestDTO.getLastName());
        }
        if(contactRequestDTO.getLastName()!= null) {
            existingContact.setEmail(contactRequestDTO.getLastName());
        }
        if(contactRequestDTO.getPhoneNumber()!= null) {
            existingContact.setPhoneNumber(contactRequestDTO.getPhoneNumber());
        }
        Contact updatedContact = contactRepository.save(existingContact);
        return ContactMapper.mapToDTO(updatedContact);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponseDTO> searchContacts(String query) {
        List<Contact> contacts = contactRepository.findAll(
                ContactSpecification.searchContacts(query)
        );
        if(contacts == null){
            log.error("Failed to search contacts for query: {}", query);
            throw new ApiException("Search operation failed");
        }
        return contacts.stream()
                .map(ContactMapper::mapToDTO)
                .toList();
    }

    @Override
    public ContactResponseDTO deleteContact(UUID contactId) {
        Optional<Contact> ContactOptional = contactRepository.findById(contactId);
        Contact contact = ContactOptional
                .orElseThrow(() -> new ContactNotFoundException(
                        "Contact not found with ID: " + contactId
                ));
        ContactResponseDTO deletedContactDTO = ContactMapper.mapToDTO(contact);
        contactRepository.delete(contact);
        return deletedContactDTO;
    }
}
