package py.com.marcelo.pescaapp.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import py.com.marcelo.pescaapp.R;
import py.com.marcelo.pescaapp.modelo.Fiscalizado;
import py.com.marcelo.pescaapp.modelo.Variedad;

/**
 * Created by marcelo on 01/07/16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{



    private static final String DATABASE_NAME = "pescaApp.db";
    private static final int DATABASE_VERSION = 1;
    //consultas las tablas
    private Dao<Fiscalizado, Integer> fiscalizadosDao = null;

    private Dao<Variedad, Integer> variedadesDao = null;
    //Capturar los errores
    private RuntimeExceptionDao<Fiscalizado, Integer> fiscalizadoDaoRuntimeException = null;

    //
    private RuntimeExceptionDao<Variedad, Integer> variedadDaoRuntimeException = null;



    //Constructor de la clase


    public DatabaseHelper(Context context)
    {
        //TODO: crear el archivo de configuracion para la base de datos
        super(context, DATABASE_NAME, null , DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource source)
    {
       //Todo: Implementar el metodo
        try
        {
            TableUtils.createTable(source, Fiscalizado.class);
            TableUtils.createTable(source, Variedad.class);
        } catch (SQLException e)
        {
            Log.e("DatabaseHelper", e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlite, ConnectionSource source, int oldVersion, int newVersion)
    {
        //TODO: Implementar el metodo
        try {
            TableUtils.dropTable(source, Fiscalizado.class, true);
            TableUtils.dropTable(source, Variedad.class, true);
            onCreate(sqlite, source);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearFiscalizados () throws SQLException {
        TableUtils.clearTable(this.getConnectionSource(), Fiscalizado.class);
    }


    public void clearVariedades () throws SQLException {
        TableUtils.clearTable(this.getConnectionSource(), Variedad.class);
    }


    public Dao<Fiscalizado, Integer> getFiscalizadoDao() throws SQLException
    {
        if (fiscalizadosDao == null )
        {
            fiscalizadosDao = getDao(Fiscalizado.class);

        }
        return fiscalizadosDao;

    }

    public Dao<Variedad, Integer> getVariedadDao() throws SQLException
    {
        if (variedadesDao == null)
        {
            variedadesDao = getDao(Variedad.class);
        }
        return variedadesDao;

    }

    public RuntimeExceptionDao<Fiscalizado, Integer> getFiscalizadosError()
    {
        if (fiscalizadoDaoRuntimeException == null)
        {
            fiscalizadoDaoRuntimeException = getRuntimeExceptionDao(Fiscalizado.class);
        }
        return fiscalizadoDaoRuntimeException;
    }

    public RuntimeExceptionDao<Variedad, Integer> getVariedadError()
    {
        if (variedadDaoRuntimeException == null)
        {
            variedadDaoRuntimeException = getRuntimeExceptionDao(Variedad.class);
        }
        return variedadDaoRuntimeException;

    }

    @Override
    public void close() {
        super.close();
        fiscalizadoDaoRuntimeException = null;
        variedadDaoRuntimeException = null;
        fiscalizadosDao = null;
        variedadesDao = null;
    }
}
