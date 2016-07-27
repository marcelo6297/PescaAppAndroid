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

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variedad variedad = (Variedad) o;

        if (id != variedad.id) return false;
        return nombre != null ? nombre.equals(variedad.nombre) : variedad.nombre == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }
}
