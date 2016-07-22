package py.com.marcelo.pescaapp.presenter;

import py.com.marcelo.pescaapp.util.DatabaseHelper;

/**
 * Created by marcelo on 09/07/16.
 * Define los metodos que debe tener todos los presenters de la aplicacion
 *
 */
public interface IPresenter {
    /**
     * Este metodo debe inicializar los recursos necesarios
     */
    void create();

    /**
     * Este metodo tiene que liberar los recursos creados
     */
    void destroy();

    /**
     * metodo para obtener el databasehelper
     */
    DatabaseHelper getDatabaseHelper();
}
