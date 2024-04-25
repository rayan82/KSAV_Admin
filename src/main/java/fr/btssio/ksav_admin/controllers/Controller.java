package fr.btssio.ksav_admin.controllers;

import fr.btssio.ksav_admin.views.View;
import fr.btssio.ksav_admin.views.Window;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Controller {

    protected Window window;
    protected View view;
    private Map<String, Object> parameters;

    public Controller(Class<? extends View> clazz) {
        try {
            this.view = clazz.getDeclaredConstructor().newInstance();
            this.view.setController(this.getInstance());
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage());
        }
    }

    protected abstract void init();

    /**
     * Permet de faire le rendu de la vue
     *
     * @param params
     */
    protected void render(Map<String, Object> params) {
        this.window.render(this.view);
        this.view.render(params);
    }

    /**
     * Permet de faire le rendu de la vue
     */
    protected void render() {
        this.render(new HashMap<>());
    }

    /**
     * Permet de changer de vue avec des paramètres
     *
     * @param clazz
     * @param parameters
     */
    protected void moveTo(Class<? extends Controller> clazz, Map<String, Object> parameters) {
        Controller.show(this.window, clazz, parameters);
    }

    /**
     * Permet de changer de vue sans les paramètres
     *
     * @param clazz
     */
    protected void moveTo(Class<? extends Controller> clazz) {
        Controller.show(this.window, clazz);
    }

    /**
     * Peremt de récuperer un paramètre
     *
     * @param key
     * @return
     */
    protected Object getParam(String key) {
        return this.parameters.get(key);
    }

    /**
     * Permet de retourner la vue
     *
     * @return Objet vue
     */
    public View getView() {
        return this.view;
    }

    /**
     * Permet de définir la fenetre
     *
     * @param window
     */
    public void setWindow(Window window) {
        this.window = window;
    }

    /**
     * Permet de définir les paramètres
     *
     * @param parameters
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public abstract void event(String name);

    protected abstract Controller getInstance();

    /**
     * Permet d'appeler un controller avec les paramètres
     *
     * @param window
     * @param clazz
     * @param parameters
     */
    public static void show(Window window, Class<? extends Controller> clazz, Map<String, Object> parameters) {
        try {
            Controller controller = clazz.newInstance();
            controller.setWindow(window);
            controller.setParameters(parameters);
            controller.init();
        } catch (SecurityException | InstantiationException | IllegalAccessException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * Permet d'appeler un controller sans les paramètres
     *
     * @param window
     * @param clazz
     */
    public static void show(Window window, Class<? extends Controller> clazz) {
        Controller.show(window, clazz, new HashMap<>());
    }

}
