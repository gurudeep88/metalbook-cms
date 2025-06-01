package com.metalbook.cms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequestDTO {
    @NotBlank(message = "First name can not be blank")
    @Size(min = 3, message = "Name should contain at least 3 characters")
    private String firstName;
    @NotBlank(message = "Second name can not be blank")
    private String lastName;
    @NotBlank(message = "email can not be blank")
    @Email(message = "Should be a valid email")
    private String email;
    @NotBlank(message = "Phone number can not be blank")
    @Pattern(regexp = "^\\+?[0-9\\-\\s]+$", message = "Incorrect phone number format")
    private String phoneNumber;
}
