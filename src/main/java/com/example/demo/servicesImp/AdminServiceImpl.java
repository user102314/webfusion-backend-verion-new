package com.example.demo.servicesImp;

import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Convertir Entity en DTO
    private AdminDTO convertToDTO(Admin admin) {
        if (admin == null) return null;

        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        dto.setEmail(admin.getEmail());
        dto.setRole(admin.getRole());
        dto.setDateCreation(admin.getDateCreation());
        dto.setDerniereConnexion(admin.getDerniereConnexion());
        dto.setActif(admin.getActif());
        return dto;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            System.out.println("🔐 Tentative de connexion pour: " + loginRequest.getUsername());

            // 1. Validation des entrées
            if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty()) {
                return new LoginResponse(false, "L'identifiant est obligatoire");
            }
            if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
                return new LoginResponse(false, "Le mot de passe est obligatoire");
            }

            // 2. Recherche par EMAIL (Correction basée sur votre base de données)
            // Assurez-vous que findByEmail est déclaré dans AdminRepository
            Admin admin = adminRepository.findByEmail(loginRequest.getUsername())
                    .orElse(null);

            if (admin == null) {
                System.out.println("❌ Email non trouvé en base: " + loginRequest.getUsername());
                return new LoginResponse(false, "Identifiants incorrects");
            }

            // 3. Vérifier si le compte est actif
            // Note: Vérifiez que la colonne 'actif' est bien à 'true' (1) dans votre base
            if (admin.getActif() == null || !admin.getActif()) {
                System.out.println("❌ Compte désactivé ou état inconnu: " + loginRequest.getUsername());
                return new LoginResponse(false, "Compte désactivé");
            }

            // 4. Vérifier le mot de passe (en clair selon vos données: "salahsalah")
            if (!admin.getPassword().equals(loginRequest.getPassword())) {
                System.out.println("❌ Mot de passe incorrect pour: " + loginRequest.getUsername());
                return new LoginResponse(false, "Identifiants incorrects");
            }

            // 5. Mise à jour de la session
            admin.setDerniereConnexion(LocalDateTime.now());
            adminRepository.save(admin);

            System.out.println("✅ Connexion réussie pour: " + loginRequest.getUsername());
            return new LoginResponse(true, "Connexion réussie", convertToDTO(admin));

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la connexion: " + e.getMessage());
            e.printStackTrace();
            return new LoginResponse(false, "Erreur serveur lors de la connexion");
        }
    }
}