package com.tienda.jdbc.controller;

import com.tienda.jdbc.factory.ConnectionFactory;
import com.tienda.jdbc.modelo.Categoria;
import com.tienda.jdbc.modelo.Producto;
import com.tienda.jdbc.dao.ProductoDAO;

import java.util.List;

public class ProductoController {

    private ProductoDAO productoDao;

    public ProductoController() {
        var factory = new ConnectionFactory();
        this.productoDao = new ProductoDAO(factory.recuperaConexion());
    }

    public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
        return productoDao.modificar(nombre,descripcion,cantidad,id);
    }

    public int eliminar(Integer id){
        return productoDao.eliminar(id);
    }

    public List<Producto> listar() {
        return productoDao.listar();
    }

    public void guardar(Producto producto, Integer categoriaId) {
        producto.setCategoriaId(categoriaId);
        productoDao.guardar(producto);
    }

    public List<Producto> listar(Categoria categoria){
        return productoDao.listar(categoria);
    }


}
