package com.metalbook.cms.mapper;


import com.metalbook.cms.dto.request.ContactRequestDTO;
import com.metalbook.cms.dto.response.ContactResponseDTO;
import com.metalbook.cms.model.Contact;

public class ContactMapper {
    public static ContactResponseDTO mapToDTO(Contact contact){
        ContactResponseDTO contactResponseDTO = new ContactResponseDTO();
        contactResponseDTO.setId(contact.getId());
        contactResponseDTO.setFirstName(contact.getFirstName());
        contactResponseDTO.setLastName(contact.getLastName());
        contactResponseDTO.setEmail(contact.getEmail());
        contactResponseDTO.setPhoneNumber(contact.getPhoneNumber());
        return contactResponseDTO;
    }

    public static Contact mapToModel(ContactRequestDTO contactRequestDTO){
        Contact contact = new Contact();
        contact.setFirstName(contactRequestDTO.getFirstName());
        contact.setLastName(contactRequestDTO.getLastName());
        contact.setEmail(contactRequestDTO.getEmail());
        contact.setPhoneNumber(contactRequestDTO.getPhoneNumber());
        return contact;
    }
}
