package py.com.marcelo.pescaapp.util;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by marcelo on 01/07/16.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {


    public static void main(String[] arg) throws IOException, SQLException {
        writeConfigFile("ormlite_config.txt");
    }
}
