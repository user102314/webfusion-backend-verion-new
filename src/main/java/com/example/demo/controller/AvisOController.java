package com.example.demo.controller;

import com.example.demo.dto.AvisODTO;
import com.example.demo.services.AvisOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avis")
// NE PLUS METTRE @CrossOrigin ICI - géré par CorsConfig.java
public class AvisOController {

    @Autowired
    private AvisOService avisService;

    @GetMapping
    public ResponseEntity<?> getAllAvis() {
        try {
            System.out.println("GET /api/avis - Récupération de tous les avis");
            List<AvisODTO> avisList = avisService.getAllAvis();
            System.out.println("Nombre d'avis trouvés: " + (avisList != null ? avisList.size() : 0));
            return ResponseEntity.ok(avisList != null ? avisList : List.of());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Erreur serveur: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping
    public ResponseEntity<?> addAvis(@RequestBody AvisODTO avisDTO) {
        try {
            System.out.println("POST /api/avis - Données reçues: " + avisDTO);

            if (avisDTO.getNomUtilisateur() == null || avisDTO.getNomUtilisateur().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("{\"error\": \"Le nom d'utilisateur est obligatoire\"}");
            }
            if (avisDTO.getMessage() == null || avisDTO.getMessage().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("{\"error\": \"Le message est obligatoire\"}");
            }
            if (avisDTO.getNote() == null || avisDTO.getNote() < 1 || avisDTO.getNote() > 5) {
                return ResponseEntity.badRequest().body("{\"error\": \"La note doit être entre 1 et 5\"}");
            }

            AvisODTO savedAvis = avisService.addAvis(avisDTO);
            System.out.println("Avis sauvegardé avec ID: " + savedAvis.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAvis);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Erreur serveur lors de l'ajout: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAvisById(@PathVariable Long id) {
        try {
            AvisODTO avis = avisService.getAvisById(id);
            if (avis != null) {
                return ResponseEntity.ok(avis);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"Avis non trouvé avec l'ID: " + id + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Erreur serveur: " + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/{id}/etat")
    public ResponseEntity<?> updateEtatAvis(@PathVariable Long id, @RequestParam String etat) {
        try {
            AvisODTO updatedAvis = avisService.updateEtatAvis(id, etat);
            return ResponseEntity.ok(updatedAvis);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("non trouvé")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\": \"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Erreur serveur: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/etat/{etat}")
    public ResponseEntity<?> getAvisByEtat(@PathVariable String etat) {
        try {
            List<AvisODTO> avisList = avisService.getAvisByEtat(etat);
            return ResponseEntity.ok(avisList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Erreur serveur: " + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAvis(@PathVariable Long id) {
        try {
            avisService.deleteAvis(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Erreur serveur: " + e.getMessage() + "\"}");
        }
    }
}