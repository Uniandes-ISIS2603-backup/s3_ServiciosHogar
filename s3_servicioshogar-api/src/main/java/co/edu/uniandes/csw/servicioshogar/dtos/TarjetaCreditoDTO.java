/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.TarjetaCreditoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Adriana Trujillo
 */
public class TarjetaCreditoDTO implements Serializable{
    //-------------------Atributos------------------------//
    long
            id;
    
    private String
            titular,
            fechaVencimiento;
    private Integer
            codeSeguridad,
            numero;
   
    //------------------Constructor------------------//
    public TarjetaCreditoDTO()
    {}
    
    public TarjetaCreditoDTO(TarjetaCreditoEntity tarjetaEntity)
    {
        if(tarjetaEntity !=null)
        {
            this.id = tarjetaEntity.getId();
            this.titular = tarjetaEntity.getTitular();
            this.fechaVencimiento = tarjetaEntity.getFechaVencimiento();
            this.codeSeguridad = tarjetaEntity.getCodeSeguridad();
            this.numero = tarjetaEntity.getNumero();
        }
    }
    
    //------------------Metodos----------------------//
    public Long getId()
    {return id;}
    
    public void setId( Long pId)
    { this.id = pId;}
    
    public String getTitular()
    {return titular;}
    
    public void setTitular(String pTitular)
    { this.titular = pTitular;}
    
    public String getFechaVencimiento()
    {return fechaVencimiento;}
    
    public void setFechaVencimiento(String pFecha)
    {this.fechaVencimiento = pFecha;}
    
    public Integer getCodeSeguridad()
    {return codeSeguridad;}
    
    public void setCodeSeguridad(Integer pCode)
    {this.codeSeguridad = pCode;}
    
    public Integer getNumero()
    {return numero;}
    
    public void setNumero(Integer pNumero)
    { this.numero = pNumero;}
    
    public TarjetaCreditoEntity toEntity()
    {
        TarjetaCreditoEntity tarjetaEntity = new TarjetaCreditoEntity();
        tarjetaEntity.setId(this.id);
        tarjetaEntity.setTitular(this.titular);
        tarjetaEntity.setFechaVencimiento(this.fechaVencimiento);
        tarjetaEntity.setCodeSeguridad(this.codeSeguridad);
        tarjetaEntity.setNumero(this.numero);
        
        return tarjetaEntity;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

