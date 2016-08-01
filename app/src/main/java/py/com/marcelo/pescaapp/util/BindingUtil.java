package py.com.marcelo.pescaapp.util;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

/**
 * Created by marcelo on 27/07/2016.
 */
public class BindingUtil {
    static String TAG = "BindingUtil";

    @BindingAdapter("android:selectedItemPosition")
    public static void setSelection(Spinner v, Integer sel) {
        Log.v(TAG, "setSelection: " + sel);
        if (v.getSelectedItemPosition() != sel) {
            v.setSelection(sel);
        }
    }


    @InverseBindingAdapter(attribute = "android:selectedItemPosition")
//    @InverseBindingAdapter(attribute = "app:selection")
    public static Integer getSelection(Spinner v) {
        Integer pos = v.getSelectedItemPosition();
        Log.v(TAG, "getSelection: " + pos);
        return pos;
    }


//    @InverseBindingMethods({
//            @InverseBindingMethod(type = Spinner.class, attribute = "android:selectedItemPosition"),
//    })


}
