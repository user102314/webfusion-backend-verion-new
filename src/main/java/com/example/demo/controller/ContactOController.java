package com.example.demo.controller;

import com.example.demo.dto.ContactODTO;
import com.example.demo.services.ContactOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(originPatterns = "*") // ✅ Changé de origins à originPatterns
public class ContactOController {

    @Autowired
    private ContactOService contactService;

    // GET all contacts
    @GetMapping
    public ResponseEntity<List<ContactODTO>> getAllContacts() {
        List<ContactODTO> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }

    // GET contact by id
    @GetMapping("/{id}")
    public ResponseEntity<ContactODTO> getContactById(@PathVariable Long id) {
        ContactODTO contact = contactService.getContactById(id);
        if (contact != null) {
            return ResponseEntity.ok(contact);
        }
        return ResponseEntity.notFound().build();
    }

    // POST add new contact
    @PostMapping
    public ResponseEntity<ContactODTO> addContact(@RequestBody ContactODTO contactDTO) {
        try {
            ContactODTO savedContact = contactService.addContact(contactDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // DELETE contact
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        try {
            contactService.deleteContact(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET contacts by email
    @GetMapping("/email/{email}")
    public ResponseEntity<List<ContactODTO>> getContactsByEmail(@PathVariable String email) {
        List<ContactODTO> contacts = contactService.getContactsByEmail(email);
        return ResponseEntity.ok(contacts);
    }
}