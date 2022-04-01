package com.bezkoder.springjwt.productos.modelo;

import javax.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "nombre", length = 60, nullable = false)
    private String nombre;
   @Column(name = "descripcion", length = 60, nullable = false)
    private String descripcion;
   @Column(name = "precio", length = 60, nullable = false)
    private int precio;
   @Column(name = "idTipo", length = 60, nullable = false)
    private int idTipo;

    private String imagen;

    public Producto() {
    }

    public Producto(Long id, String nombre, String descripcion, int precio, int id_tipo, String imagen) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idTipo = id_tipo;
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int id_tipo) {
        this.idTipo = id_tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
