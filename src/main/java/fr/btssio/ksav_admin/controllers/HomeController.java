package fr.btssio.ksav_admin.controllers;

import fr.btssio.ksav_admin.models.DAO.UtilisateurDAO;
import fr.btssio.ksav_admin.models.DAO.UtilisateurRoleDAO;
import fr.btssio.ksav_admin.models.entities.UtilisateurEntity;
import fr.btssio.ksav_admin.views.HomeView;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HomeController extends Controller {
    
    public HomeController() {
        super(HomeView.class);
    }

    @Override
    protected void init() {
        UtilisateurRoleDAO dao = new UtilisateurRoleDAO();
        
        Map<String, Object> params = new HashMap<>();
        params.put("utilisateurs", dao.getAll());
        this.render(params);
    }
    
    /**
     * Permet de supprimer 1 ou plusieurs utilisateur avec un popup qui s'affiche pour avoir la confirmation
     */
    private void deleteUser() {
        JTable table = ((HomeView) this.view).getUsersTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int[] rows = table.getSelectedRows();
        
        String msg = "Voulez vous vraiment supprimer " + (rows.length > 1 ? "ses utilisateurs" : "cet utilisateur") + "?";
        String[] options = {"Oui", "Non"};
        int choice = JOptionPane.showOptionDialog(null, msg, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        if (choice == JOptionPane.YES_NO_OPTION) {
            for (int i = 0; i < rows.length; i++) {
                int id = (int) model.getValueAt(rows[i], 0);
                UtilisateurDAO dao = new UtilisateurDAO();
                dao.delete(id);
                model.removeRow(rows[i]);
            }
        }
    }
    
    /**
     * Permet d'afficher la fênetre pour l'ajout d'un utilisateur
     */
    private void addUser() {
        Map<String, Object> params = new HashMap<>();
        params.put("action", "add");
        this.moveTo(ActionController.class, params);
    }
    
    /**
     * Permet d'afficher la fênetre pour la modification d'un utilisateur
     */
    private void updateUser() {
        UtilisateurDAO dao = new UtilisateurDAO();
        
        JTable table = ((HomeView) this.view).getUsersTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int row = table.getSelectedRow();
        int id = (int) model.getValueAt(row, 0);
        
        UtilisateurEntity entity = dao.get(id);
        
        Map<String, Object> params = new HashMap<>();
        params.put("action", "update");
        params.put("user", entity);
        this.moveTo(ActionController.class, params);
    }

    @Override
    public void event(String name) {
        switch (name) {
            case "Ajouter" -> this.addUser();
            case "Modifier" -> this.updateUser();
            case "Supprimer" -> this.deleteUser();
        }
    }

    @Override
    protected Controller getInstance() {
        return this;
    }
        
}
