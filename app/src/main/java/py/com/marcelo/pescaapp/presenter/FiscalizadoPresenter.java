package py.com.marcelo.pescaapp.presenter;

import android.content.Context;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import py.com.marcelo.pescaapp.BR;
import py.com.marcelo.pescaapp.modelo.Fiscalizado;
import py.com.marcelo.pescaapp.modelo.Variedad;

/**
 * Created by marcelo on 04/07/16.
 * Contiene la logica de la vista y su relacion con el modelo
 * Esta a la escucha de los cambios del spinner, y tambien tiene el helper
 * que se encarga de cargar los datos
 * Es importante hacer que la vista sincronice los datos de la variedad seleccionada
 * tambien hacer que cuando se edita un dato, la vista seleccione que datos mostrar
 * ==========================
 * En el spinner utilizar el metodo spinner.setSelection
 * Para Utilizar este metodo crear un metodo en el adapter que permita obtener el indice
 * de un elemento.
 *
 *
 */
public class FiscalizadoPresenter extends AbstractPresenter {

    private static String TAG = "FiscalizadoPresenter";


    @Bindable
    String errorMsg = "se recibio un error";

    @Bindable
    Boolean mostrarError = false;

    Integer pk = 0;
    @Bindable
    Fiscalizado item;

    @Bindable
    String activityTitle = "Agregar / Actualizar";

    Variedad variedad = null;

    @Bindable
    Integer selection = 0;

    @Bindable
    Integer prueba = 3;

//    public MyItemListener getListener() {
//        return listener;
//    }

    //Listener
    @Bindable
//    MyItemListener listener = null;


    private VariedadesAdapter variedadesAdapter = null;
    private List<Variedad> variedadList = null;

    public FiscalizadoPresenter(Context context) {
        super(context);
        item = new Fiscalizado();
//        listener = new MyItemListener();
    }




    public  Fiscalizado getItem () {
        return item;
    }

    /**
     * Setear el item para actualizarlo
     *
     */
    public void setItem (int pk) {

        try {
            Log.i(TAG, "setItem() pk=" + Integer.toString(pk));
            item = getDatabaseHelper().getFiscalizadoDao().queryBuilder().where().idEq(pk).queryForFirst() ;
//            buscar tambien los datos de la variedad seleccionada
            variedad = getDatabaseHelper().getVariedadDao().queryBuilder().where().idEq(item.variedadId).queryForFirst();
//            y cargar en la vista del spinner los datos de la variedad


//            seleccionada, y cuando se guarda el item limpiar el combo
//            notifyPropertyChanged(BR.item);
//            notifyPropertyChanged(BR.selection);
        } catch (SQLException e) {

            e.printStackTrace();
            mostrarError = true;
            errorMsg = e.getMessage();
            notifyPropertyChanged(BR.mostrarError);
            notifyPropertyChanged(BR.errorMsg);
        }
    }

    public Boolean getMostrarError() {
        return mostrarError;
    }

    public String getErrorMsg() {

        return errorMsg;
    }



    public void setCantidad(String cantidad) {
        //Tratar de convertir a Entero
        //Cuando el String es null o es "" hacer cero al item
        if (cantidad != null && !cantidad.equals("")) {
            this.item.cantidad = Integer.parseInt(cantidad);
        }
    }

    public void setMedidaMayor(String medidaMayor) {
        //Tratar de convertir a entero
        if (medidaMayor != null && !medidaMayor.equals("")) {
            this.item.medidaMayor = Integer.parseInt(medidaMayor);
        }
    }


    @Bindable
    public String getCantidad() {
        //tratar de obtener un String
        if (item == null) {return "Item Null";}
        if ((item.cantidad != null) || (item.cantidad != 0)) {
            return item.cantidad.toString();
        }
        return "";
    }

    @Bindable
    public String getMedidaMayor() {
        if (item == null) {return "Item Null";}
        if (item.medidaMayor != null || item.medidaMayor != 0) {
            return item.medidaMayor.toString();
        }
        return "";
    }


    //
    public Integer getSelection() {

        if (variedad != null) {
            int pos = variedadesAdapter.getPosition(variedad);

            Log.v(TAG, "llamando getSelection posicion: " + pos);
            return pos;
        }
        Log.v(TAG, "llamando getSelection posicion: 0");
        return 0;
    }

    public void setSelection(Integer pk) {
        Log.v(TAG, "llamado setSelection: " + pk);
        if (variedadList == null) {
            Log.v(TAG, "crear adapter:");
            createAdapter();
        }
        variedad = variedadList.get(pk);
        Log.v(TAG, "variedad:" + variedad);
        item.setVariedad(variedad);
    }


    //A borrar si funciona databinding
    public void setVariedad(Object object) {
        if (object instanceof Variedad) {
            variedad = (Variedad) object;
            item.setVariedad(variedad);
        }
    }

    //a borrar si funciona databinding
    public Object getVariedad() {
        return variedad;
    }


    /**
     * Guardar datos
     * @return String el resultado de la operacion de guardado
     */
    public String save() {
        //Convertir los datos al tipo adecuado
        //guardar los datos
//        Fiscalizado fiscalizado =new Fiscalizado(fiscalia, equipo, variedad.codigo, variedad.nombre,  Integer.parseInt(cantidad),  Integer.parseInt(medidaMayor), observacion, new Date());
        Log.i(TAG, "Metodo.save()" + item.toString());
//        setVariedad(variedad);
        String result;
        try {
            if (item.id == 0) {
                item.createdOn = new Date();
                getDatabaseHelper().getFiscalizadoDao().create(item);
                result = "Creado!!!";
            }
            else {
                item.updatedOn = new Date();
                getDatabaseHelper().getFiscalizadoDao().update(item);
                result = "Actualizado!!!";
            }
            //Mostrar mensaje de guardado
            // limpiar item
            item = new Fiscalizado();
            //Notificar a la vista que el item cambio;
            notifyPropertyChanged(BR.item);
            notifyPropertyChanged(BR.selection);
            notifyPropertyChanged(BR.cantidad);
            notifyPropertyChanged(BR.medidaMayor);

        } catch (SQLException e) {
            result = "Error al guardar!!!";
            e.printStackTrace();
            //Mostrar mensaje de error
            //Mostrar Texto de Error
        }
        Log.i(TAG, "Presionado Guardar");
        return result;


    }

    private void createAdapter() {

        Log.i(TAG, "createAdapter.llamado");
        try {
            variedadList = getDatabaseHelper().getVariedadDao().queryForAll();
            Log.i(TAG, variedadList.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            variedadList = new ArrayList<>();
            Variedad variedadError = new Variedad();
            variedadError.nombre = "Se produjo un error BBDD";
            variedadList.add(variedadError);
        }

        variedadesAdapter = new VariedadesAdapter(super.getContext(),variedadList);
    }


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//        Log.i("FiscalizadoPresenter", "onItemSelected:");
//        variedad = (Variedad) parent.getItemAtPosition(position);
//        item.setVariedad(variedad);
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//            Log.i("FiscalizadoPresenter", "onNothingSelected:");
//            variedad = null;
//            item.setVariedad(null);
//
//    }

//    class MyItemListener implements AdapterView.OnItemSelectedListener {
//
//        @Override
//        public void onItemSelected(AdapterView parent, View view, int position, long id) {
//            Log.i("FiscalizadoPresenter", "onItemSelected:" + position);
//            variedad = (Variedad) parent.getItemAtPosition(position);
//            item.setVariedad(variedad);
//
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//            Log.i("FiscalizadoPresenter", "onNothingSelected:");
//            variedad = null;
//            item.setVariedad(null);
//        }
//    }


    @Override
    public void init() {
        createAdapter();
    }

    public ArrayAdapter<Variedad> getAdapter() {
        if (variedadesAdapter == null) {
            //crear variedades adapter;
            Log.v(TAG, "No se llamo al metodo init");
            createAdapter();
        }
        return variedadesAdapter;
    }

    /**
     * Por default siempre dibujara el mismo layout
     */
    private final class VariedadesAdapter extends ArrayAdapter<Variedad> {
        List<Variedad> variedades = null;
        public VariedadesAdapter(Context context,@NonNull List<Variedad> variedades) {
            super(context, android.R.layout.simple_spinner_item, variedades);
            this.variedades = variedades;
        }

    }



}
