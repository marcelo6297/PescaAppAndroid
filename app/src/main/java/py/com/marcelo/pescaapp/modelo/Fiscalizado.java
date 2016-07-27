package py.com.marcelo.pescaapp.modelo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import py.com.marcelo.pescaapp.util.ExportHelper;

/**
 * Created by marcelo on 01/07/16.
 * Permite manejar un registro de lo que ya esta fiscalizado
 */


@DatabaseTable(tableName = "fiscalizados")
public class Fiscalizado implements ExportHelper.Exportable{


    String[] columnNames = {"id", "fiscalia", "equipo" , "variedadId", "variedadNombre","codigo_variedad", "cantidad", "medida_mayor", "observaciones", "created_on", "updated_on"};

    @DatabaseField (generatedId = true)
    public Integer id = 0;

    @DatabaseField(index = true, canBeNull = false)
    public String  fiscalia = "";

    @DatabaseField
    public String  equipo = "";

    @DatabaseField (columnName = "variedad_id")
    public Integer variedadId = 0;

    @DatabaseField (columnName = "variedad_nombre")
    public String variedadNombre = "";

    @DatabaseField (columnName = "codigo_variedad")
    public String codigoVariedad ="";

    @DatabaseField
    public Integer cantidad= 0;

    @DatabaseField (columnName = "medida_mayor")
    public Integer medidaMayor =0;

    @DatabaseField
    public String  observaciones = "";

    @DatabaseField(columnName = "created_on")
    public Date createdOn ;

    @DatabaseField(columnName = "updated_on")
    public Date updatedOn;




    @Override
    public String[] getColumnNames() {
        return  this.columnNames;
    }

    @Override
    public String[] getData2Export() {
        String[] datos = {id.toString(), fiscalia, equipo, variedadNombre ,codigoVariedad,  cantidad.toString(), medidaMayor.toString(), observaciones.toString(), createdOn != null?createdOn.toString(): " ", updatedOn != null ? updatedOn.toString():" "};
        return datos;
    }



    @Override
    public String toString() {
        return "Fiscalizado{" +
                "id=" + id +
                ", fiscalia='" + fiscalia + '\'' +
                ", equipo='" + equipo + '\'' +
                ", codigoVariedad='" + codigoVariedad + '\'' +
                ", variedad='" + variedadNombre + '\'' +
                ", cantidad=" + cantidad +
                ", medidaMayor=" + medidaMayor +
                ", observaciones='" + observaciones + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }

    public void setVariedad(Variedad v) {
        if (v == null) {
            variedadId = -1;
            variedadNombre = "";
            codigoVariedad = "";
        }
        variedadId = v.getId();
        variedadNombre = v.nombre;
        codigoVariedad = v.codigo;
    }

//    Constructor

    public Fiscalizado() {
    }



    public Fiscalizado(Integer id, String fiscalia, String equipo, Integer variedadId, String variedadNombre, String codigoVariedad, Integer cantidad, Integer medidaMayor, String observaciones) {
        this.id = id;
        this.fiscalia = fiscalia;
        this.equipo = equipo;
        this.variedadId = variedadId;
        this.variedadNombre = variedadNombre;
        this.codigoVariedad = codigoVariedad;
        this.cantidad = cantidad;
        this.medidaMayor = medidaMayor;
        this.observaciones = observaciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFiscalia() {
        return fiscalia;
    }

    public void setFiscalia(String fiscalia) {
        this.fiscalia = fiscalia;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public Integer getVariedadId() {
        return variedadId;
    }

    public void setVariedadId(Integer variedadId) {
        this.variedadId = variedadId;
    }

    public String getVariedadNombre() {
        return variedadNombre;
    }

    public void setVariedadNombre(String variedadNombre) {
        this.variedadNombre = variedadNombre;
    }

    public String getCodigoVariedad() {
        return codigoVariedad;
    }

    public void setCodigoVariedad(String codigoVariedad) {
        this.codigoVariedad = codigoVariedad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getMedidaMayor() {
        return medidaMayor;
    }

    public void setMedidaMayor(Integer medidaMayor) {
        this.medidaMayor = medidaMayor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
