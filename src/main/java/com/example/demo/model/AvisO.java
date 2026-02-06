package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "avis")
public class AvisO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_utilisateur", nullable = false, length = 100)
    private String nomUtilisateur;

    @Column(name = "message", nullable = false, length = 1000)
    private String message;

    @Column(name = "note", nullable = false)
    private Integer note;

    @Column(name = "etat", nullable = false, length = 20)
    private String etat = "EN_ATTENTE";

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @PrePersist
    protected void onCreate() {
        if (this.dateCreation == null) {
            this.dateCreation = LocalDateTime.now();
        }
        if (this.etat == null || this.etat.isEmpty()) {
            this.etat = "EN_ATTENTE";
        }
    }

    // Constructeurs
    public AvisO() {}

    // Getters et Setters (tous les getters/setters comme avant)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomUtilisateur() { return nomUtilisateur; }
    public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getNote() { return note; }
    public void setNote(Integer note) { this.note = note; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}