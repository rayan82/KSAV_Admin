package fr.btssio.ksav_admin.views;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends JFrame {

    private View view;
    
    public Window(String title) {
        this.setTitle(title);
    }

    /**
     * Permet de faire le rendu de la fenêtre
     * 
     * @param view 
     */
    public void render(View view) {
        this.view = view;
        this.getContentPane().removeAll();
        Dimension preferredSize = this.view.getPreferredSize();
        this.add(this.view);
        this.setSize(new Dimension(preferredSize.width + 20, preferredSize.height + 20));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

}
