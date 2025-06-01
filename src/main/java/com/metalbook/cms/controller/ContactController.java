package com.metalbook.cms.controller;

import com.metalbook.cms.dto.request.ContactRequestDTO;
import com.metalbook.cms.dto.response.ContactResponseDTO;
import com.metalbook.cms.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contacts")
@Tag(name = "Contact", description = "API for managing Contacts")
public class ContactController {
    private final ContactService contactService;
    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @GetMapping
    public String hello(){
        return "Hello";
    }

    @PostMapping
    @Operation(summary = "Create a new Contact")
    public ResponseEntity<ContactResponseDTO> createContact(
            @Valid @RequestBody ContactRequestDTO contactRequestDTO
    ) {
        ContactResponseDTO contactResponseDTO = contactService.createContact(contactRequestDTO);
        return ResponseEntity.ok().body(contactResponseDTO);
    }

    @GetMapping("/{contactId}")
    @Operation(summary = "Get a Contact")
    public ResponseEntity<ContactResponseDTO> getContact(
            @PathVariable UUID contactId
    ){
        ContactResponseDTO contactRequestDTO = contactService.getContact(contactId);
        return new ResponseEntity<>(contactRequestDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Search contacts by firstName, lastName or email")
    public ResponseEntity<List<ContactResponseDTO>> searchContacts(
            @Parameter(description = "Search term (first name, last name, or email)")
            @RequestParam String query
    ){
        List<ContactResponseDTO> contactResponseDTOS = contactService.searchContacts(query);
        return new ResponseEntity<>(contactResponseDTOS, HttpStatus.OK);
    }

    @PutMapping("/{contactId}")
    @Operation(summary = "Update a Contact")
    public ResponseEntity<ContactResponseDTO> updateContact(
            @PathVariable UUID contactId,
            @RequestBody ContactRequestDTO contactRequestDTO
            ){

        ContactResponseDTO updatedContactDTO = contactService.updateContact(
                contactId, contactRequestDTO
        );
        return new ResponseEntity<>(updatedContactDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{contactId}")
    @Operation(summary = "Delete a Contact")
    public ResponseEntity<ContactResponseDTO> deleteContact(
            @PathVariable UUID contactId
    ){
        ContactResponseDTO contactResponseDTO = contactService.deleteContact(contactId);
        return new ResponseEntity<>(contactResponseDTO, HttpStatus.OK);
    }

}
