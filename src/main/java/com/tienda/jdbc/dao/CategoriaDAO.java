package com.tienda.jdbc.dao;

import com.tienda.jdbc.modelo.Categoria;
import com.tienda.jdbc.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private Connection con;

    public CategoriaDAO(Connection con) {
        this.con = con;
    }

    public List<Categoria> listar() {
        List<Categoria> resultado = new ArrayList<>();

        try {
            String querySelect = "SELECT ID, NOMBRE FROM CATEGORIA";
            System.out.println(querySelect);

            final PreparedStatement statement = con.prepareStatement(querySelect);

            try (statement) {
                final ResultSet resultSet = statement.executeQuery();
                try (resultSet) {
                    while (resultSet.next()) {
                        resultado.add(new Categoria(
                                resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE")));
                    }
                }
                ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public List<Categoria> listarConProductos() {
        List<Categoria> resultado = new ArrayList<>();

        try {
            String querySelect = "SELECT C.ID, C.NOMBRE, P.ID, P.NOMBRE, P.CANTIDAD "
                    + "FROM CATEGORIA C "
                    + "INNER JOIN PRODUCTO P "
                    + "ON C.ID = P.CATEGORIA_ID";
            System.out.println(querySelect);

            final PreparedStatement statement = con.prepareStatement(querySelect);

            try (statement) {
                final ResultSet resultSet = statement.executeQuery();

                try (resultSet) {
                    while (resultSet.next()) {
                        int categoriaId = resultSet.getInt("C.ID");
                        String categoriaNombre = resultSet.getString("C.NOMBRE");

                        Categoria categoria = resultado
                                .stream()
                                .filter(cat -> cat.getId().equals(categoriaId))
                                .findAny().orElseGet(() -> {
                                    Categoria cat = new Categoria(
                                            categoriaId, categoriaNombre);
                                    resultado.add(cat);

                                    return cat;
                                });

                        Producto producto = new Producto(
                                resultSet.getInt("P.ID"),
                                resultSet.getString("P.NOMBRE"),
                                resultSet.getInt("P.CANTIDAD"));

                        categoria.agregar(producto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
}
