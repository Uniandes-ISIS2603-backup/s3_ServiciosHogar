/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.TarjetaCreditoEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 *
 * @author Adriana Trujillo
 * @author Carlos Robles / 3er Ciclo
 */
public class TarjetaCreditoDTO implements Serializable{
   
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------ 
    private Long id;    
    private String titular;
    private String fechaVencimiento;
    private Integer codeSeguridad;
    private String numero;    
    private ClienteDTO cliente;   
   
    //------------------------------------------
    //---------------Constructor----------------
    //------------------------------------------
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
    
    //------------------------------------------
    //------------------Metodos-----------------
    //------------------------------------------   

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getTitular() {return titular;}

    public void setTitular(String titular) {this.titular = titular;}

    public String getFechaVencimiento() {return fechaVencimiento;}

    public void setFechaVencimiento(String fechaVencimiento) {this.fechaVencimiento = fechaVencimiento;}

    public Integer getCodeSeguridad() {return codeSeguridad;}

    public void setCodeSeguridad(Integer codeSeguridad) {this.codeSeguridad = codeSeguridad;}

    public String getNumero() {return numero;}

    public void setNumero(String numero) {this.numero = numero;}

    public ClienteDTO getCliente() {return cliente;}

    public void setCliente(ClienteDTO cliente) {this.cliente = cliente;}  
    
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

