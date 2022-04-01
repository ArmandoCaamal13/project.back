package com.bezkoder.springjwt.productos.controlador;

import com.bezkoder.springjwt.productos.excepciones.ResourceNotFoundException;
import com.bezkoder.springjwt.productos.modelo.Producto;
import com.bezkoder.springjwt.productos.repositorio.ProductoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductoControlador {

    @Autowired
    private ProductoRepositorio repositorio;

    //metodo que sirve para en listar todos los productos
    @GetMapping("/productos")
    public List<Producto> listarTodosLosProductos(){
        return repositorio.findAll();
    }


    //este metodo sirve para guardar el empleado
    @PostMapping("/productos")
    public Producto guardarProducto(@RequestBody Producto producto){
        return repositorio.save(producto);
    }


    //ESTE METODO SIRVE PARA BUSCAR UN PRODUCTO POR MEDIO DEL ID
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id){
        Producto producto = repositorio.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No existe el producto con el ID: " + id));
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto detallesProducto){
        Producto producto = repositorio.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No existe el producto con el ID: " + id));
        producto.setNombre(detallesProducto.getNombre());
        producto.setDescripcion(detallesProducto.getDescripcion());
        producto.setIdTipo(detallesProducto.getIdTipo());
        producto.setPrecio(detallesProducto.getPrecio());

        Producto productoActualizado= repositorio.save(producto);
        return ResponseEntity.ok(productoActualizado);
    }
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map <String,Boolean>> eliminarProducto(@PathVariable long id){
        Producto producto = repositorio.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No existe el producto con el ID: " + id));
        repositorio.delete(producto);
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminar",Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
    @GetMapping("/productos/list/{tipo}")
    public ResponseEntity< List<Producto>> obtenerProductoPorIdTipo(@PathVariable int tipo){
        List<Producto> producto = repositorio.findAllByIdTipo(tipo);
        return ResponseEntity.ok(producto);
    }

}


