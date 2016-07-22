package py.com.marcelo.pescaapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import py.com.marcelo.pescaapp.R;
import py.com.marcelo.pescaapp.modelo.Fiscalizado;
import py.com.marcelo.pescaapp.modelo.Variedad;
import py.com.marcelo.pescaapp.util.DatabaseHelper;
import py.com.marcelo.pescaapp.util.ExportHelper;

import com.j256.ormlite.dao.Dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import static py.com.marcelo.pescaapp.util.ExportHelper.*;

public class DatabaseActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper = null;
    Dao<Fiscalizado, Integer> fiscalizadoDao = null;
    Dao<Variedad, Integer> variedadDao = null;

    List<Exportable> exportables = null;


    TextView operationLog = null;
    StringBuilder logger = new StringBuilder();
    private TextView getLogView() {
        if (operationLog == null) {
            operationLog = (TextView) findViewById(R.id.operationLog);
        }
        return operationLog;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        logger.append("Iniciando aplicacion \n");


        //Iniciar la base de datos
        try {
            initApp();

        } catch (SQLException e) {
            e.printStackTrace();

        }


        //crear la base de datos

        getLogView().setText(logger.toString());
    }



    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    /**
     *
     */
    private void initApp() throws SQLException {

        fiscalizadoDao = getHelper().getFiscalizadoDao();
        logger.append("Iniciando fiscalizado DAO \n");
        variedadDao = getHelper().getVariedadDao();
        logger.append("Iniciando Variedad DAO \n");
        exportables = new ArrayList<>();
        for (Fiscalizado fiscalizado : fiscalizadoDao.queryForAll()) {
            exportables.add(fiscalizado);
        }
    }

    /**
     * Leer la accion de entrada y ejecutar el comando indicado
     * @param v la vista que deseo manipular
     */
    public void acciones(View v) {
        Context context = v.getContext();
        Intent intent;
        switch (v.getId()) {
            case R.id.irAddFiscalizados :
                intent = new Intent(context, AddPescadoActivity.class);
                context.startActivity(intent);
            case R.id.irListaFiscalizados:
                intent = new Intent(context, PescadoListActivity.class);
                context.startActivity(intent);
        }

    }

    public void accionesdb(View v)  {
        int id = v.getId();
        if (id == R.id.btnExportFis) {
            Log.i("DatabaseActivity:", "accionesdb");
            ConfigCSVImpl config = new ConfigCSVImpl();
            ExportHelper eh = new ExportHelper(getApplicationContext(), config );
            try {
                eh.export(exportables);
                Toast.makeText(DatabaseActivity.this, "Exportados los datos", Toast.LENGTH_SHORT).show();
                Log.i("DatabaseActivity:", "accionesdb.export");
            } catch (IOException e) {
                Toast.makeText(DatabaseActivity.this, "Error: "+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Crear datos de ejemplo
     * @param v
     */
    public void accionesEjemplo(View v) {
//        Fiscalizado item = new Fiscalizado();
//        Variedad variedad = new Variedad();
        if (v.getId() == R.id.crearFiscalizados)
            {
                Log.i("accionesEjemplo", "Crear ejemplo Fiscalizado 1");
                InputStream inputStream = getApplicationContext().getResources().openRawResource(R.raw.fiscalizados);
                Reader reader = new InputStreamReader(inputStream);

                Iterable<CSVRecord> records = null;
                try
                {
                    final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
                    ;
                    for (CSVRecord record : parser)
                    {
                        //Cargar desde el archivo de ejemplo
//                        Fiscalizado fiscalizado = new fiscalizado(record.get("codigo"), record.get("nombre"));
                        fiscalizadoDao.create(new Fiscalizado());
                        logger.append("Agregando fiscalizado: "+ "\n");
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    logger.append("No se leer el archivo: " + "\n" );
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                    logger.append("No se pudo agregar: " + "\n" );
                }



            }


        if (v.getId() == R.id.crearVariedades)
        {
            InputStream inputStream = getApplicationContext().getResources().openRawResource(R.raw.variedades);
            Reader reader = new InputStreamReader(inputStream);

            Iterable<CSVRecord> records = null;
            try
            {
                final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
                ;
                for (CSVRecord record : parser)
                {
                       Variedad variedad = new Variedad(record.get("codigo"), record.get("nombre"));
                       variedadDao.create(variedad);
                       logger.append("Agregando variedad: "+ variedad.nombre + "\n");
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                logger.append("No se leer el archivo: " + "\n" );
            }
           catch (SQLException e)
           {
               e.printStackTrace();
               logger.append("No se pudo agregar: " + "\n" );
          }


        }

        getLogView().setText(logger.toString());

    }


    public void limpiarTbls(View v){
        int id = v.getId();
        try {
                if (id == R.id.btnFisCls) {
                       databaseHelper.clearFiscalizados();
                       logger.append("Limpiando la tabla fiscalizados"+ "\n");
                }
                if (id == R.id.btnVarCls) {
                       databaseHelper.clearVariedades();
                       logger.append("Limpiando la tabla Variedades"+ "\n");
                }
        } catch (SQLException e) {
                e.printStackTrace();
//                mostrar mensajes de error;
        }
        getLogView().setText(logger.toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
        fiscalizadoDao = null;
        variedadDao = null;

    }





}
