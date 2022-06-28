package com.tienda.jdbc.dao;

import com.tienda.jdbc.modelo.Categoria;
import com.tienda.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    final private Connection con;

    public ProductoDAO(Connection con) {
        this.con = con;
    }

    public void guardar(Producto producto) {

        try {
            final PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO PRODUCTO (nombre, descripcion, cantidad, categoria_Id)"
                            + " VALUES(?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS);

            try (statement) {
                statement.setString(1, producto.getNombre());
                statement.setString(2, producto.getDescripcion());
                statement.setInt(3, producto.getCantidad());
                statement.setInt(4, producto.getCategoriaId());

                statement.execute();

                final ResultSet resultSet = statement.getGeneratedKeys();

                try (resultSet) {
                    while (resultSet.next()) {
                        producto.setId(resultSet.getInt(1));
                        System.out.println(String.format("Fue insertado el producto %s", producto));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Producto> listar() {
        List<Producto> resultado = new ArrayList<>();

        try {
            final PreparedStatement statement =
                    con.prepareStatement("SELECT  ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

            try (statement) {
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                         resultado.add(new Producto(
                                resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE"),
                                resultSet.getString("DESCRIPCION"),
                                resultSet.getInt("CANTIDAD")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public Integer eliminar(Integer id) {

        try {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
            try (statement) {
                statement.setInt(1, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Integer modificar(String nombre, String descripcion, Integer cantidad, Integer id) {

        try {
            final PreparedStatement statement = con.prepareStatement(
                    "UPDATE  PRODUCTO SET "
                    + " NOMBRE = ?, "
                    + " DESCRIPCION = ?, "
                    + " CANTIDAD = ?, "
                    + " WHERE ID = ?");

            try (statement) {
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, cantidad);
                statement.setInt(4, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public List<Producto> listar(Categoria categoriaId) {
        List<Producto> resultado = new ArrayList<>();

        try {
            var querySelect = "SELECT  ID, NOMBRE, DESCRIPCION, CANTIDAD " +
                    " FROM PRODUCTO " +
                    " WHERE CATEGORIA_ID = ?";

            System.out.println(querySelect);

            final PreparedStatement statement =
                    con.prepareStatement(querySelect);

            try (statement) {
                statement.setInt(1, categoriaId.getId());
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        resultado.add(new Producto(
                                resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE"),
                                resultSet.getString("DESCRIPCION"),
                                resultSet.getInt("CANTIDAD")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

}