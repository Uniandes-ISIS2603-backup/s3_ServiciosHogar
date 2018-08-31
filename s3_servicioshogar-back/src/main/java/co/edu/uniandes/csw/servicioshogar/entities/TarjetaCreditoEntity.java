/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import java.io.Serializable;

/**
 *
 * @author Adriana Trujillo
 */
public class TarjetaCreditoEntity extends BaseEntity implements Serializable {
    
    //--------------------Atributos-------------------//
    private String 
            titular,
            fechaVencimiento;
    private Integer
            codeSeguridad,
            numero;
    
    //-------------------Metodos---------------------//
    
    public String getTitular()
    { return titular;}
    
    public void setTitular(String pTitular)
    { this.titular = pTitular;}
    
    public String getFechaVencimiento()
    { return fechaVencimiento;}
    
    public  void setFechaVencimiento( String pFecha)
    { this.fechaVencimiento = pFecha;}
    
    public Integer getCodeSeguridad()
    { return codeSeguridad;}
    
    public void setCodeSeguridad(Integer pCode)
    { this.codeSeguridad = pCode;}
    
    public Integer getNumero()
    { return numero;}
    
    public void setNumero( Integer pNumero)
    { this.numero = pNumero;}
    
}
