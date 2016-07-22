package py.com.marcelo.pescaapp.modelo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by marcelo on 01/07/16.
 * La clase Variedad guarda informacion referente al tipo de variedad de pescado
 */

@DatabaseTable(tableName = "variedades")
public class Variedad {


    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    public String codigo;

    @DatabaseField
    public String nombre;

    public Variedad() {
    }

    public Variedad(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
