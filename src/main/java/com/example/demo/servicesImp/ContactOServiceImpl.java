package com.example.demo.servicesImp;

import com.example.demo.dto.ContactODTO;
import com.example.demo.model.ContactO;
import com.example.demo.repository.ContactORepository;
import com.example.demo.services.ContactOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactOServiceImpl implements ContactOService {

    @Autowired
    private ContactORepository contactRepository;

    // Convertir Entity en DTO
    private ContactODTO convertToDTO(ContactO contact) {
        ContactODTO dto = new ContactODTO();
        dto.setId(contact.getId());
        dto.setNom(contact.getNom());
        dto.setEmail(contact.getEmail());
        dto.setTelephone(contact.getTelephone());
        dto.setMessage(contact.getMessage());
        dto.setDateEnvoi(contact.getDateEnvoi());
        return dto;
    }

    // Convertir DTO en Entity
    private ContactO convertToEntity(ContactODTO dto) {
        ContactO contact = new ContactO();
        contact.setNom(dto.getNom());
        contact.setEmail(dto.getEmail());
        contact.setTelephone(dto.getTelephone());
        contact.setMessage(dto.getMessage());
        return contact;
    }

    @Override
    public List<ContactODTO> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContactODTO getContactById(Long id) {
        return contactRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public ContactODTO addContact(ContactODTO contactDTO) {
        // Validation
        if (contactDTO.getEmail() == null || contactDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("L'email est obligatoire");
        }

        if (contactDTO.getNom() == null || contactDTO.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }

        if (contactDTO.getMessage() == null || contactDTO.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Le message est obligatoire");
        }

        ContactO contact = convertToEntity(contactDTO);
        ContactO savedContact = contactRepository.save(contact);
        return convertToDTO(savedContact);
    }

    @Override
    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new RuntimeException("Contact non trouvé avec l'ID: " + id);
        }
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactODTO> getContactsByEmail(String email) {
        return contactRepository.findByEmail(email)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}