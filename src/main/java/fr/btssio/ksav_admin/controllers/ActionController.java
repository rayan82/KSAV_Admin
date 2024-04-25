package fr.btssio.ksav_admin.controllers;

import fr.btssio.ksav_admin.models.DAO.RoleDAO;
import fr.btssio.ksav_admin.models.DAO.UtilisateurDAO;
import fr.btssio.ksav_admin.models.entities.UtilisateurEntity;
import fr.btssio.ksav_admin.utils.DateTool;
import fr.btssio.ksav_admin.utils.HashTool;
import fr.btssio.ksav_admin.views.ActionView;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class ActionController extends Controller {
    
    public ActionController() {
        super(ActionView.class);
    }

    @Override
    protected void init() {
        RoleDAO dao = new RoleDAO();
        
        Map<String, Object> params = new HashMap<>();
        params.put("action", this.getParam("action"));
        params.put("roles", dao.getAll());
        if (this.getParam("action") == "update") {
            params.put("user", this.getParam("user"));
        }
        
        this.render(params);
    }
    
    /**
     * Permet d'ajouter un utilisateur avec les données entrées entrer dans le formulaire
     */
    private void add() {
        ActionView view = (ActionView) this.view;
        String nom = view.getNom().toLowerCase();
        String prenom = view.getPrenom().toLowerCase();
        String mdp = view.getMdp();
        String dateNaissance = view.getDateNaissance();
        int sexe = view.getSexe();
        int role = view.getRole();
        
        if (nom.isEmpty() || prenom.isEmpty() || mdp.isEmpty() || dateNaissance.isEmpty() || sexe == 0 || role == 0) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs avant de valider !");
        } else if (!DateTool.isValidDate(dateNaissance, "dd/MM/yyyy")) {
            JOptionPane.showMessageDialog(null, "Le format de la date de naissance n'est pas bon (JJ/MM/AAAA) !");
        } else {
            UtilisateurEntity entity = new UtilisateurEntity(role, (nom + "." + prenom), nom, prenom, HashTool.hash(mdp), LocalDateTime.now(), DateTool.convertStringToDate(dateNaissance), sexe);
            UtilisateurDAO dao = new UtilisateurDAO();
            dao.insert(entity);
            this.returnToHome();
        }
    }
    
    /**
     * Permet de mettre à jour un utilisateur avec les données entrées dans le formulaire
     */
    private void update() {
        ActionView view = (ActionView) this.view;
        String nom = view.getNom();
        String prenom = view.getPrenom();
        String mdp = view.getMdp();
        String dateNaissance = view.getDateNaissance();
        int sexe = view.getSexe();
        int role = view.getRole();
                
        if (nom.isEmpty() || prenom.isEmpty() || mdp.isEmpty() || dateNaissance.isEmpty() || sexe == 0 || role == 0) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs avant de valider !");
        } else if (!DateTool.isValidDate(dateNaissance, "dd/MM/yyyy")) {
            JOptionPane.showMessageDialog(null, "Le format de la date de naissance n'est pas bon (JJ/MM/AAAA) !");
        } else {
            UtilisateurEntity entity = new UtilisateurEntity(((UtilisateurEntity) this.getParam("user")).getIdUtilisateur(), role, (nom.toLowerCase() + "." + prenom.toLowerCase()), nom, prenom, (view.getMdpInput().isVisible() ? HashTool.hash(mdp) : mdp), null, DateTool.convertStringToDate(dateNaissance), sexe);
            UtilisateurDAO dao = new UtilisateurDAO();
            dao.update(entity);
            this.returnToHome();
        }
    }
    
    /**
     * Permet d'annuler l'ajout ou la modification et d'afficher un message de confirmation
     */
    private void cancel() {
        String msg = "Voulez vous vraiement annuler ?";
        String[] options = {"Oui", "Non"};
        int choice = JOptionPane.showOptionDialog(null, msg, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        if (choice == JOptionPane.YES_NO_OPTION) {
            this.returnToHome();
        }
    }
    
    /**
     * Permet que quand on modifie un utilisateur, quand on clique sur le bouton reinitialiser du mot de passe réafficher le champ du mot de passe
     */
    private void resetPassword() {
        if (this.getParam("action") == "update") {
            ActionView view = (ActionView) this.view;
            view.getResetButton().setVisible(false);
            view.getMdpInput().setVisible(true);
        }
    }
    
    /**
     * Permet de retourner à l'écran de liste des utilisateurs
     */
    private void returnToHome() {
        this.moveTo(HomeController.class);
    }

    @Override
    public void event(String name) {
        switch (name) {
            case "Annuler" -> this.cancel();
            case "Ajouter" -> this.add();
            case "Modifier" -> this.update();
            case "Réinitialiser" -> this.resetPassword();
        }
    }

    @Override
    protected Controller getInstance() {
        return this;
    }
    
}
