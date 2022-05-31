package com.bezkoder.springjwt.models;

import javax.persistence.*;

@Entity
@Table(name = "inventario")
public class Producto {

    private long id;
    private String equipo;
    private String marca;
    private String modelo;
    private String num_serie;

    public Producto(){
    }

    public Producto(String equipo, String marca, String modelo, String num_serie){
        this.equipo = equipo;
        this.modelo = modelo;
        this.marca = marca;
        this.num_serie = num_serie;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    @Column(name = "Equipo", nullable = false)
    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    @Column(name = "Marca", nullable = false)
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Column(name = "Modelo", nullable = false)
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Column(name = "Num_Serie", nullable = false)
    public String getNum_serie() {
        return num_serie;
    }

    public void setNum_serie(String num_serie) {
        this.num_serie = num_serie;
    }
    @Override
    public String toString(){
        return "Inventario [id=" + id + ", Equipo=" + equipo + ", Modelo=" + modelo +
                ", Num_serie=" + num_serie + " ]";
    }
}