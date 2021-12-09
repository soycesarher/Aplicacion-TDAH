/**
 *Clase: Contiene los datos asociados a una CURP.
 *
 * @author: TDAH Móvil
 */
package com.example.tdah.validaciones;
import java.util.HashMap;

public class DatosDeCurp {

    private final String[] STRING_PARAMETROS =
            {
                    "curp", "fatherName", "motherName", "name", "gender", "birthday", "birthState"
            };

    private String string_curp;
    private String string_apellido_paterno;
    private String string_apellido_materno;
    private String string_nombre;
    private String string_genero;
    private String string_fecha_nacimiento;
    private String string_estado_nacimiento;

    /**
     * Organiza los obtenidos de cadena de texto de la API de RENAPO.
     *
     * @param string_cadena_renapo Cadena de la forma
     * {"name":"César","apellido":"Farfán"}.
     */
    public DatosDeCurp(String string_cadena_renapo)
    {

        HashMap<String, String> hashmap_datos = separarDatos(string_cadena_renapo);

        this.string_curp = hashmap_datos.get(STRING_PARAMETROS[0]);
        this.string_apellido_paterno = hashmap_datos.get(STRING_PARAMETROS[1]);
        this.string_apellido_materno = hashmap_datos.get(STRING_PARAMETROS[2]);
        this.string_nombre = hashmap_datos.get(STRING_PARAMETROS[3]);
        this.string_genero = hashmap_datos.get(STRING_PARAMETROS[4]);
        this.string_fecha_nacimiento = hashmap_datos.get(STRING_PARAMETROS[5]);
        this.string_estado_nacimiento = hashmap_datos.get(STRING_PARAMETROS[6]);

        hashmap_datos.clear();
    }

    /**
     * Separa los datos de una cadena de la API de RENAPO y los agrupa en un
     * HashMap.
     *
     * @param string_cadena_renapo Cadena de la forma
     * {"name":"César","apellido":"Farfán"}.
     * @return HashMap con los datos de la cadena separados.
     */
    private static HashMap separarDatos(String string_cadena_renapo)
    {
        //Se quitan llaves al inicio y final de la cadena.
        String string_cad = string_cadena_renapo.toString().substring(1, string_cadena_renapo.length() - 1);
        //Se separan datos por coma (,).
        String[] string_arreglo = string_cad.split(",");
        HashMap<String, String> hashmap_datos = new HashMap<String, String>();

        for (int i = 0; i < string_arreglo.length; i++)
        {
            /*
             *Se separa clave y dato.
             *El tamaño del arreglo siempre será 2.
             */
            String[] string_aux = string_arreglo[i].split(":");
            /*
             *Las siguientes 3 líneas son índices que nos ayudan a quitar las
             *comillas iniciales y finales de las cadenas de clave y dato.
             */
            final int INT_INICIO = 1;
            final int INT_FINAL_CLAVE = string_aux[0].length() - 1;
            final int INT_FINAL_ENTRADA = string_aux[1].length() - 1;
            //Se añaden clave y dato a HashMap.
            hashmap_datos.put(string_aux[0].substring(INT_INICIO, INT_FINAL_CLAVE),
                    string_aux[1].substring(INT_INICIO, INT_FINAL_ENTRADA));
        }
        return hashmap_datos;
    }

    /**
     * Obtiene CURP.
     *
     * @return CURP
     */
    public String getString_curp()
    {
        return string_curp;
    }

    /**
     * Asigna CURP.
     *
     * @param string_curp CURP
     */
    public void setString_curp(String string_curp)
    {
        this.string_curp = string_curp;
    }

    /**
     * Obtiene apellido paterno.
     *
     * @return Apellido paterno
     */
    public String getString_apellido_paterno()
    {
        return string_apellido_paterno;
    }

    /**
     * Asigna apellido paterno.
     *
     * @param string_apellido_paterno Apellido paterno
     */
    public void setString_apellido_paterno(String string_apellido_paterno)
    {
        this.string_apellido_paterno = string_apellido_paterno;
    }

    /**
     * Obtiene apellido materno.
     *
     * @return Apellido materno
     */
    public String getString_apellido_materno()
    {
        return string_apellido_materno;
    }

    /**
     * Asigna apellido materno.
     *
     * @param string_apellido_materno Apellido materno
     */
    public void setString_apellido_materno(String string_apellido_materno)
    {
        this.string_apellido_materno = string_apellido_materno;
    }

    /**
     * Obtiene nombre.
     *
     * @return Nombre
     */
    public String getString_nombre()
    {
        return string_nombre;
    }

    /**
     * Asigna nombre.
     *
     * @param string_nombre Nombre
     */
    public void setString_nombre(String string_nombre)
    {
        this.string_nombre = string_nombre;
    }

    /**
     * Obtiene género.
     *
     * @return Género
     */
    public String getString_genero()
    {
        return string_genero;
    }

    /**
     * Asigna género.
     *
     * @param string_genero Género
     */
    public void setString_genero(String string_genero)
    {
        this.string_genero = string_genero;
    }

    /**
     * Obtiene fecha de nacimiento.
     *
     * @return Fecha de nacimiento
     */
    public String getString_fecha_nacimiento()
    {
        return string_fecha_nacimiento;
    }

    /**
     * Asigna fecha de nacimiento.
     *
     * @param string_fecha_nacimiento Fecha de nacimiento
     */
    public void setString_fecha_nacimiento(String string_fecha_nacimiento)
    {
        this.string_fecha_nacimiento = string_fecha_nacimiento;
    }

    /**
     * Obtiene estado de nacimiento.
     *
     * @return Estado de nacimiento
     */
    public String getString_estado_nacimiento()
    {
        return string_estado_nacimiento;
    }

    /**
     * Asigna estado de nacimiento.
     *
     * @param string_estado_nacimiento Estado de nacimiento
     */
    public void setString_estado_nacimiento(String string_estado_nacimiento)
    {
        this.string_estado_nacimiento = string_estado_nacimiento;
    }
}


