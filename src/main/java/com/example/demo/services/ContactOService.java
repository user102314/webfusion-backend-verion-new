package com.example.demo.services;

import com.example.demo.dto.ContactODTO;
import java.util.List;

public interface ContactOService {
    List<ContactODTO> getAllContacts();
    ContactODTO getContactById(Long id);
    ContactODTO addContact(ContactODTO contactDTO);
    void deleteContact(Long id);
    List<ContactODTO> getContactsByEmail(String email);
}