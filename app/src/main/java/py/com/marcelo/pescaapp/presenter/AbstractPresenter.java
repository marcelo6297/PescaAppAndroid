package py.com.marcelo.pescaapp.presenter;

import android.content.Context;
import android.databinding.BaseObservable;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import py.com.marcelo.pescaapp.modelo.Fiscalizado;
import py.com.marcelo.pescaapp.util.DatabaseHelper;

/**
 * Created by marcelo on 09/07/16.
 * La clase AbstractPresenter Tiene todos los campos que debe tener un
 * presenter que accede a la base de datos
 */
public abstract class AbstractPresenter extends BaseObservable implements IPresenter {
    private DatabaseHelper databaseHelper = null;
    private Context context = null;


    public AbstractPresenter(Context context) {
        this.context = context;
    }



    public Context getContext() {
        return context;
    }

    @Override
    public void destroy() {
        OpenHelperManager.releaseHelper();
        context = null;
        databaseHelper = null;
    }

    @Override
    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
