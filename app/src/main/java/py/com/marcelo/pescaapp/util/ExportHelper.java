package py.com.marcelo.pescaapp.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by marcelo on 04/07/16.
 * Debe recibir el tipo de caracter separador
 * Recibir la ruta y nombre del archivo a guardar
 * El caracter set
 * y una interfaz que permita leer los datos como un Array de String
 */
public class ExportHelper {

    Context context = null;
    ConfigCSV configCSV = null;


    public ExportHelper(Context context, ConfigCSV csv){
        this.context = context;
        this.configCSV = csv;

    }

    /**
     *
     * @param exportableList
     * @throws IOException
     */

    public void export(List<Exportable> exportableList) throws IOException{

        if (isExternalStorageWritable()) {
            Log.i("Exportable:", "exportableList.isExternalStorageWritable: true");
            File directorio;
            Boolean crear = false;
            //Crear la carpeta
            directorio = new File(Environment.getExternalStorageDirectory(), configCSV.getDirName());
            if (!directorio.exists()) {
                if (directorio.mkdir()) {
                    crear = true;
                }
                else {
                    Log.e("Exportable:", "file.mkdir().Archivo no creado");
                    throw new IOException("No se pudo crear el archivo!!!");
                }
            }
            else {
                crear = true;
            }
            if (crear) {

                //crear el archivo
                File csv = new File(directorio, configCSV.getFileName());
                FileWriter writer = new FileWriter(csv);
                BufferedWriter bw = new BufferedWriter(writer);
                StringBuilder sb = new StringBuilder();

                for (Exportable exportable : exportableList) {
                    Log.i("Exportable:", "exportableList.Loop exportableList");
                    String[] datos = exportable.getData2Export();
                    //Utilizar Apache Commons IO y String
                    int cap = datos.length - 1, cont=0;

                    for(String dato : datos) {
                        sb.append(dato);
                        if (cap > cont){
                            sb.append(configCSV.getSeparatorChar());
                        }
                        cont++;
                    }

                    //Escribir en el archivo
                    bw.write(sb.toString());
                    bw.newLine();
                    sb.delete(0,sb.length() - 1);
                }
                bw.close();
            }


        }
        else {
            Log.i("Exportable:", "isExternalStorageWritable= false");
            throw new IOException("La tarjera no esta disponible para escribir");
        }
    }

    /**
     * Chequear si esta presente la tarjeta y permite escribir
     * @return
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public interface ConfigCSV {
        public String getDirName();
        public String getFileName();
        public String getCharacterSet();
        public Boolean saveColumnNames ();
        public String getSeparatorChar ();
    }

    /**
     * Created by marcelo on 04/07/16.
     */
    public interface Exportable {

        /**
         * Nombre de las tablas a exportar
         * @return
         */
        public String[] getColumnNames();

        /**
         * Los datos a exportar
         */

        public String[] getData2Export();
    }


    public static class ConfigCSVImpl implements ConfigCSV {

        String dirName = "prueba", fileName = "mi-archivo.csv", separador = "\t", charSet = "UTF-8";
        Boolean columnName = false;

        public ConfigCSVImpl() {
        }

        @Override
        public String getDirName() {
            return dirName;
        }

        @Override
        public String getFileName() {
            return fileName;
        }

        @Override
        public String getCharacterSet() {
            return charSet;
        }

        @Override
        public Boolean saveColumnNames() {
            return columnName;
        }

        @Override
        public String getSeparatorChar() {
            return separador;
        }
    }



}

