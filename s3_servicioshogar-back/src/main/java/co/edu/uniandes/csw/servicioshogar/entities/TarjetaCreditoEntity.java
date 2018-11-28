/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Adriana Trujillo
 */
@Entity
public class TarjetaCreditoEntity extends BaseEntity implements Serializable 
{
    
    //--------------------Atributos-------------------//
    private String titular;
    private String fechaVencimiento;
    private String numero;
    private Integer codeSeguridad;    
    
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ClienteEntity cliente;
    //-------------------Metodos---------------------//    
    public String getTitular() {return titular;}

    public void setTitular(String titular) {this.titular = titular;}

    public String getFechaVencimiento() {return fechaVencimiento;}

    public void setFechaVencimiento(String fechaVencimiento) {this.fechaVencimiento = fechaVencimiento;}

    public Integer getCodeSeguridad() {return codeSeguridad;}

    public void setCodeSeguridad(Integer codeSeguridad) {this.codeSeguridad = codeSeguridad;}

    public String getNumero() {return numero;}

    public void setNumero(String numero) {this.numero = numero;}

    public ClienteEntity getCliente() {return cliente;}

    public void setCliente(ClienteEntity cliente) {this.cliente = cliente;}     
}
