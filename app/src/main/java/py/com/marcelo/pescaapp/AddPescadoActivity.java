package py.com.marcelo.pescaapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import py.com.marcelo.pescaapp.databinding.ActivityAddPescadoBinding;
import py.com.marcelo.pescaapp.modelo.Fiscalizado;
import py.com.marcelo.pescaapp.presenter.FiscalizadoPresenter;

public class AddPescadoActivity extends AppCompatActivity {


    public static final String ARG_ITEM_ID = "item_id";


    FiscalizadoPresenter fiscalizadoPresenter = null;
    Fiscalizado item = null;
    ActivityAddPescadoBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pescado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //crear los items en la base de datos
//
//                //
//                int count = 0;
//                String msg;
//                if (count == 0) {
////                    crear los registros
//
//                    msg = "Se han creado los registros";
//                } else {
//                    msg = "Ya no se pueden crear registros";
//                }
//                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //Del Intent que trae esta vista buscar el ID del elemento... si no esta presente crear un nuevo elemento en blanco

            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
        //crear el fiscalizadoPresenter
        fiscalizadoPresenter = new FiscalizadoPresenter(this);
        fiscalizadoPresenter.init();
        if (getIntent().hasExtra(ARG_ITEM_ID)) {
            int pk = getIntent().getIntExtra(ARG_ITEM_ID, 0);
            fiscalizadoPresenter.setItem(pk);

        }
//      Bindar los objetos
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_pescado);
        binding.setPresenter(fiscalizadoPresenter);
        Spinner spinnerVariedad = (Spinner)findViewById(R.id.spnVariedad);
        spinnerVariedad.setSelection(5);
        fiscalizadoPresenter.getAdapter().notifyDataSetChanged();
//        binding.executePendingBindings();
        spinnerVariedad.setOnItemSelectedListener(fiscalizadoPresenter.getListener());

//        try {
//            fiscalizadoDao = getHelper().getFiscalizadoDao();
//            int pk = getIntent().getIntExtra(PescadoDetailFragment.ARG_ITEM_ID, 0);
//            item = fiscalizadoDao.queryBuilder().where().idEq(pk).queryForFirst();
//        }
//        catch (java.sql.SQLException e) {
//        }

        //Correr en background la carga de datos
//        if (item != null)
//        {
//        }




    }

    /**
     * Guarda los datos en la base de datos
     * @param v
     */
    public void guardar(View v){
//        binding.executePendingBindings();
        String result = fiscalizadoPresenter.save();
        Toast.makeText(AddPescadoActivity.this, result, Toast.LENGTH_SHORT).show();
        ;
    }


    @Override
    protected void onDestroy() {
//        Spinner spinnerVariedad = (Spinner)findViewById(R.id.spnVariedad);

        super.onDestroy();
    }
}
