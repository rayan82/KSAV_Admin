package fr.btssio.ksav_admin.views;

import fr.btssio.ksav_admin.controllers.Controller;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class View extends JPanel implements ActionListener {
    
    private Controller controller;
    private Map<String, Object> parameters;
    
    /**
     * Permet de faire le rendu en appelant les methodes d'initialisations de base
     * 
     * @param params 
     */
    public void render(Map<String, Object> params) {
        this.parameters = params;
        this.init();
        for (Component component : getComponents()) {
            if (component instanceof JButton jButton) {
                jButton.addActionListener(this);
            }
        }
    }
    
    /**
     * Permet de définir le controleur de la vue
     * 
     * @param controller 
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    /**
     * Méthode abstraite d'initialisation
     */
    public abstract void init();
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
        this.controller.event(name);
    }
    /**
     * Récuperer un paramètre à partie de sa clée
     * 
     * @param key
     * @return Objet correspondant à la clée
     */
    public Object getParam(String key) {
        return this.parameters.get(key);
    }
    
}
