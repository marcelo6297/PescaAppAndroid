package py.com.marcelo.pescaapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

import py.com.marcelo.pescaapp.modelo.Fiscalizado;
import py.com.marcelo.pescaapp.util.DatabaseHelper;

import com.j256.ormlite.dao.Dao;

/**
 * A fragment representing a single Fiscalizado detail screen.
 * This fragment is either contained in a {@link PescadoListActivity}
 * in two-pane mode (on tablets) or a {@link PescadoDetailActivity}
 * on handsets.
 */
public class PescadoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    DatabaseHelper databaseHelper = null;
    Dao<Fiscalizado, Integer> fiscalizadoDao = null;

    private Fiscalizado mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PescadoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //primero obtener el helper de la base de datos

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            try {
                fiscalizadoDao = getHelper().getFiscalizadoDao();
                int pk = getArguments().getInt(ARG_ITEM_ID);
                mItem =  fiscalizadoDao.queryBuilder().where().idEq(pk).queryForFirst() ;
            } catch (SQLException e) {
                e.printStackTrace();
                Log.e("PescadoDetailFragment", "error al intentar leer DDBB");
            }

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                if (mItem != null) {
                    appBarLayout.setTitle(mItem.equipo);
                }
            }
//            Log.i("PescadoDetailFragment", "mItem es: "+ mItem.toString());
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pescado_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(mItem.equipo + "\n");
            stringBuilder.append(mItem.fiscalia + "\n");
            stringBuilder.append(mItem.variedadNombre + "\n");
            stringBuilder.append(mItem.cantidad + "\n");
            stringBuilder.append(mItem.medidaMayor + "\n");
            stringBuilder.append(mItem.observaciones + "\n");
            ((TextView) rootView.findViewById(R.id.pescado_detail)).setText(stringBuilder.toString());
        }

        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
        fiscalizadoDao = null;

    }

    /**
     * You'll need this in your class to get the helper from the manager once per class.
     */
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this.getContext(), DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
