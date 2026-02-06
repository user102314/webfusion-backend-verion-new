package com.example.demo.services;

import com.example.demo.dto.AvisODTO;
import java.util.List;

public interface AvisOService {
    List<AvisODTO> getAllAvis();
    AvisODTO getAvisById(Long id);
    AvisODTO addAvis(AvisODTO avisDTO);
    AvisODTO updateEtatAvis(Long id, String nouvelEtat);
    List<AvisODTO> getAvisByEtat(String etat);
    void deleteAvis(Long id); // Cette méthode doit être implémentée
}