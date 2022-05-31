package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto,Long>{
    
}
