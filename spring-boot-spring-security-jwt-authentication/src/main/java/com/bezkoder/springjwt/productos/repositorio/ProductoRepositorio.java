package com.bezkoder.springjwt.productos.repositorio;

import com.bezkoder.springjwt.productos.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProductoRepositorio extends JpaRepository<Producto,Long>{
    List<Producto> findByPublished(boolean published);
    List<Producto> findByTitleContaining(String title);
}
