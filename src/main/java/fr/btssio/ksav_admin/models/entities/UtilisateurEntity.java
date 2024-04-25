package fr.btssio.ksav_admin.models.entities;

import java.sql.Date;
import java.time.LocalDateTime;
import fr.btssio.ksav_admin.models.entities.annotations.PrimaryKey;

public class UtilisateurEntity implements Entity {
    
    @PrimaryKey
    private int IDUTILISATEUR;
    private int IDROLE;
    private String PSEUDO;
    private String NOM;
    private String PRENOM;
    private String MDP;
    private LocalDateTime DATECREATION;
    private Date DATENAISSANCE;
    private int SEXE;

    public UtilisateurEntity(int idUtilisateur, int idRole, String pseudo, String nom, String prenom, String mdp, LocalDateTime dateCreation, Date dateNaissance, int sexe) {
        this.IDUTILISATEUR = idUtilisateur;
        this.IDROLE = idRole;
        this.PSEUDO = pseudo;
        this.NOM = nom;
        this.PRENOM = prenom;
        this.MDP = mdp;
        this.DATECREATION = dateCreation;
        this.DATENAISSANCE = dateNaissance;
        this.SEXE = sexe;
    }

    public UtilisateurEntity(int idRole, String pseudo, String nom, String prenom, String mdp, LocalDateTime dateCreation, Date dateNaissance, int sexe) {
        this(0, idRole, pseudo, nom, prenom, mdp, dateCreation, dateNaissance, sexe);
    }
    
    public UtilisateurEntity() {}

    public int getIdUtilisateur() {
        return this.IDUTILISATEUR;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.IDUTILISATEUR = idUtilisateur;
    }

    public int getIdRole() {
        return this.IDROLE;
    }

    public void setIdRole(int idRole) {
        this.IDROLE = idRole;
    }
    
    public String getPseudo() {
        return this.PSEUDO;
    }
    
    public void setPseudo(String pseudo) {
        this.PSEUDO = pseudo;
    }

    public String getNom() {
        return this.NOM;
    }

    public void setNom(String nom) {
        this.NOM = nom;
    }

    public String getPrenom() {
        return this.PRENOM;
    }

    public void setPrenom(String prenom) {
        this.PRENOM = prenom;
    }

    public String getMdp() {
        return this.MDP;
    }

    public void setMdp(String mdp) {
        this.MDP = mdp;
    }

    public LocalDateTime getDateCreation() {
        return this.DATECREATION;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.DATECREATION = dateCreation;
    }

    public Date getDateNaissance() {
        return this.DATENAISSANCE;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.DATENAISSANCE = dateNaissance;
    }

    public int getSexe() {
        return this.SEXE;
    }

    public void setSexe(int sexe) {
        this.SEXE = sexe;
    }
    
}
