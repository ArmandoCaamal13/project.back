package com.bezkoder.springjwt.productos.controlador;

import com.bezkoder.springjwt.productos.modelo.Producto;
import com.bezkoder.springjwt.productos.repositorio.ProductoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081/")
public class ProductoControlador {
    @Autowired
    ProductoRepositorio productoRepositorio;
    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getAllProductos(@RequestParam(required = false)String title){
        try{
            List<Producto> productos = new ArrayList<Producto>();
            if(title == null)
                productoRepositorio.findAll().forEach(productos::add);
            else
                productoRepositorio.findByTitleContaining(title).forEach(productos::add);
            if (productos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productos, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable("id") long id) {
        Optional<Producto> productoData = productoRepositorio.findById(id);
        if (productoData.isPresent()) {
            return new ResponseEntity<>(productoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/productos")
    public ResponseEntity<Producto> createProducto (@RequestBody Producto producto) {
        try {
            Producto _producto  = productoRepositorio
                    .save(new Producto(producto.getTitle(), producto.getDescription(), false));
            return new ResponseEntity<>(_producto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable("id") long id, @RequestBody Producto producto) {
        Optional<Producto> productoData = productoRepositorio.findById(id);
        if (productoData.isPresent()) {
            Producto _producto = productoData.get();
            _producto.setTitle(producto.getTitle());
            _producto.setDescription(producto.getDescription());
            _producto.setPublished(producto.isPublished());
            return new ResponseEntity<>(productoRepositorio.save(_producto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<HttpStatus> deleteProducto(@PathVariable("id") long id) {
        try {
            productoRepositorio.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/productos")
    public ResponseEntity<HttpStatus> deleteAllProductos() {
        try {
            productoRepositorio.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/productos/published")
    public ResponseEntity<List<Producto>> findByPublished() {
        try {
            List<Producto> tutorials = productoRepositorio.findByPublished(true);
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



