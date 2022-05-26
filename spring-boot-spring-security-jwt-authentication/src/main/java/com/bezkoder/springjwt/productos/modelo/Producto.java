package com.bezkoder.springjwt.productos.modelo;


import javax.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "published")
    private boolean published;
    public Producto(){
    }
    public Producto(String title, String description, boolean published) {
        this.title = title;
        this.descripcion = description;
        this.published = published;
    }
    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return descripcion;
    }
    public void setDescription(String description) {
        this.descripcion = description;
    }
    public boolean isPublished() {
        return published;
    }
    public void setPublished(boolean isPublished) {
        this.published = isPublished;
    }
    @Override
    public String toString() {
        return "Proyecto [id=" + id + ", title=" + title + ", desc=" + descripcion + ", published=" + published + "]";
    }

}
