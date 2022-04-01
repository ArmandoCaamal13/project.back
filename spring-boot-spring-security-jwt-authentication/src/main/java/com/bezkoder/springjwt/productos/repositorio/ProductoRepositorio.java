package com.bezkoder.springjwt.productos.repositorio;

import com.bezkoder.springjwt.productos.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long>{

    List<Producto> findAllByIdTipo(int Tipo);
}
