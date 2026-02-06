package com.example.demo.servicesImp;

import com.example.demo.dto.AvisODTO;
import com.example.demo.model.AvisO;
import com.example.demo.repository.AvisORepository;
import com.example.demo.services.AvisOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AvisOServiceImpl implements AvisOService {

    @Autowired
    private AvisORepository avisRepository;

    // Convertir Entity en DTO
    private AvisODTO convertToDTO(AvisO avis) {
        AvisODTO dto = new AvisODTO();
        dto.setId(avis.getId());
        dto.setNomUtilisateur(avis.getNomUtilisateur());
        dto.setMessage(avis.getMessage());
        dto.setNote(avis.getNote());
        dto.setEtat(avis.getEtat());
        dto.setDateCreation(avis.getDateCreation());
        return dto;
    }

    // Convertir DTO en Entity
    private AvisO convertToEntity(AvisODTO dto) {
        AvisO avis = new AvisO();
        avis.setNomUtilisateur(dto.getNomUtilisateur());
        avis.setMessage(dto.getMessage());
        avis.setNote(dto.getNote());

        if (dto.getEtat() != null && !dto.getEtat().isEmpty()) {
            avis.setEtat(dto.getEtat());
        } else {
            avis.setEtat("EN_ATTENTE");
        }

        if (dto.getDateCreation() != null) {
            avis.setDateCreation(dto.getDateCreation());
        }

        return avis;
    }

    @Override
    public List<AvisODTO> getAllAvis() {
        return avisRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AvisODTO getAvisById(Long id) {
        return avisRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public AvisODTO addAvis(AvisODTO avisDTO) {
        // Validation
        if (avisDTO.getNote() == null || avisDTO.getNote() < 1 || avisDTO.getNote() > 5) {
            throw new IllegalArgumentException("La note doit être comprise entre 1 et 5");
        }

        if (avisDTO.getNomUtilisateur() == null || avisDTO.getNomUtilisateur().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom d'utilisateur est obligatoire");
        }

        if (avisDTO.getMessage() == null || avisDTO.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Le message est obligatoire");
        }

        AvisO avis = convertToEntity(avisDTO);
        AvisO savedAvis = avisRepository.save(avis);
        return convertToDTO(savedAvis);
    }

    @Override
    public AvisODTO updateEtatAvis(Long id, String nouvelEtat) {
        return avisRepository.findById(id)
                .map(avis -> {
                    // Validation des états autorisés
                    List<String> etatsValides = List.of("EN_ATTENTE", "APPROUVE", "REJETE");
                    if (!etatsValides.contains(nouvelEtat)) {
                        throw new IllegalArgumentException("État non valide. Les états valides sont: " + etatsValides);
                    }

                    avis.setEtat(nouvelEtat);
                    AvisO updatedAvis = avisRepository.save(avis);
                    return convertToDTO(updatedAvis);
                })
                .orElseThrow(() -> new RuntimeException("Avis non trouvé avec l'ID: " + id));
    }

    @Override
    public List<AvisODTO> getAvisByEtat(String etat) {
        return avisRepository.findByEtat(etat)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAvis(Long id) {
        if (!avisRepository.existsById(id)) {
            throw new RuntimeException("Avis non trouvé avec l'ID: " + id);
        }
        avisRepository.deleteById(id);
    }
}