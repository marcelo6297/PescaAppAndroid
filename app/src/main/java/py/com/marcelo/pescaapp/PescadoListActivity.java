package py.com.marcelo.pescaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import py.com.marcelo.pescaapp.modelo.Fiscalizado;
import py.com.marcelo.pescaapp.util.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Pescados. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PescadoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PescadoListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    DatabaseHelper databaseHelper = null;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null){
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pescado_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, AddPescadoActivity.class);
                context.startActivity(intent);

            }
        });

        View recyclerView = findViewById(R.id.pescado_list);
        assert recyclerView != null;
        //consultar en la base de datos y cargar los datos
//        DatabaseHelper model = DatabaseHelper;
        List<Fiscalizado> fiscalizados = null;
        try {
            Dao<Fiscalizado, Integer> daoFiscalizado = getHelper().getFiscalizadoDao();
            fiscalizados = daoFiscalizado.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (fiscalizados == null) { fiscalizados = new ArrayList<>();}
        setupRecyclerView((RecyclerView) recyclerView, fiscalizados);

        if (findViewById(R.id.pescado_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<Fiscalizado> items) {
        //Cuando creo la instancia debo pasarle la lista de items a crear
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(items));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Fiscalizado> mListaFiscalizados;

        public SimpleItemRecyclerViewAdapter(List<Fiscalizado> items) {
            mListaFiscalizados = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pescado_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mListaFiscalizados.get(position);
            holder.mIdView.setText(holder.mItem.id.toString());
            holder.mFiscalizadoView.setText(holder.mItem.fiscalia);
            holder.equipoView.setText(holder.mItem.equipo);
            holder.cantidadView.setText(holder.mItem.cantidad.toString());
//            holder.cantidadView.setText(holder.mItem.cantidad);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putInt(PescadoDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        PescadoDetailFragment fragment = new PescadoDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.pescado_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, PescadoDetailActivity.class);
                        intent.putExtra(PescadoDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mListaFiscalizados.size();
        }


        /**
         *
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            public Fiscalizado mItem;
            public final View mView;
            public final TextView mIdView;
            public final TextView mFiscalizadoView;
            public final TextView equipoView;
            public final TextView cantidadView;

            public ViewHolder(View view) {

                super(view);
                //Aca voy a cargar todos los datos
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.idFiscalizado);
                mFiscalizadoView = (TextView) view.findViewById(R.id.fiscalia);
                equipoView = (TextView) view.findViewById(R.id.equipo);
                cantidadView = (TextView) view.findViewById(R.id.cantidadList);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mFiscalizadoView.getText() + "'";
            }
        }
    }


    /**
     * You'll need this in your class to get the helper from the manager once per class.
     */
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("PescadoListActivity:" , "onRestart: recargar datos!!!");
    }
}
