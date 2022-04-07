package com.mycompany.sistemadegestionmysql.dao;

import com.mycompany.sistemadegestionmysql.models.Cliente;
import com.mysql.jdbc.StringUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    // Consejo es muy util mirar como es el formato del codigo al hacerse una consultar 
    // desde la base de datos para copiarla y pegarla tal cual lo muestra en el codigo de la base de datos
    // es decir es mejor ver la Query (Consulta) y hacerla en la base de datos
    // para asi copiar el codigo
    public Connection conectar() {
        // Conexion a la base de datos
        String baseDeDatos = "java";
        String usuario = "root";
        String password = "";
        String host = "localhost";
        String puerto = "3306";
        String driver = "com.mysql.jdbc.Driver";
        String conexionUrl = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDeDatos + "?useSSL=false";

        Connection conexion = null;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(conexionUrl, usuario, password);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }

    public void agregar(Cliente clientes) {
        Connection conexion = conectar();
        try {
            String sql = "INSERT INTO `clientes` (`id`, `nombre`, `apellido`, `telefono`, `email`) VALUES (NULL, '" + clientes.getNombre() + "', '" + clientes.getApellido() + "', '" + clientes.getTelefono() + "', '" + clientes.getEmail() + "');";
            Statement statement = conexion.createStatement();
            statement.execute(sql);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Para mostar en el programa la lista
    }

    public List<Cliente> listar(Cliente clientes) {
        Connection conexion = conectar();

        List<Cliente> listado = new ArrayList<>();
        try {
            // Para buscar datos
            String sql = "SELECT * FROM `clientes`";
            Statement statement = conexion.createStatement();
            // ExecuteQuery sirve para mostrar los datos
            ResultSet resultado = statement.executeQuery(sql);
            // Para recorrer los datos y guardarlo en la lista 
            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultado.getString("id"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellido(resultado.getString("apellido"));
                cliente.setTelefono(resultado.getString("telefono"));
                cliente.setEmail(resultado.getString("email"));
                listado.add(cliente);
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listado;
    }

    public void eliminar(String id) {
        Connection conexion = conectar();
        try {
            // Para borrar datos 
            // Ojo con no poner el WHERE que sino se borra toda la base de datos
            String sql = "DELETE FROM clientes WHERE `clientes`.`id` = " + id;
            Statement statement = conexion.createStatement();
            statement.execute(sql);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Para mostar en el programa la lista
    }

    public void actualizar(Cliente clientes) {
        Connection conexion = conectar();
        try {
            String sql = "UPDATE `clientes` SET `nombre` = '" + clientes.getNombre()
                    + "', `apellido` = '" + clientes.getApellido()
                    + "', `telefono` = '" + clientes.getTelefono()
                    + "', `email` = '" + clientes.getEmail()
                    + "' WHERE `clientes`.`id` = " + clientes.getId() + "";
            Statement statement = conexion.createStatement();
            statement.execute(sql);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Para mostar en el programa la lista
    }

    // Esta funcion sirve para que el boton de guardar
    // Sepa a donde ir 
    // si el id esta vacio va a agregar
    // y si no al actualizar
    public void guardar(Cliente cliente) {
        if (StringUtils.isEmptyOrWhitespaceOnly(cliente.getId())) {
            agregar(cliente);
        } else {
            actualizar(cliente);
        }
    }

}
