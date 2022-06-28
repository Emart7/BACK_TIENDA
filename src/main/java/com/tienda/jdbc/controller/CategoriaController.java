package com.tienda.jdbc.controller;

import com.tienda.jdbc.dao.CategoriaDAO;
import com.tienda.jdbc.factory.ConnectionFactory;
import com.tienda.jdbc.modelo.Categoria;

import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDAO;

    public CategoriaController(){
        var factory = new ConnectionFactory();
        this.categoriaDAO = new CategoriaDAO(factory.recuperaConexion());
    }

	public List<Categoria> listar() {
		return categoriaDAO.listar();
	}

    public List<Categoria> cargaReporte() {
        return this.categoriaDAO.listarConProductos();
    }

}
