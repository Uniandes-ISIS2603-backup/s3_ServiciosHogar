/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.FacturaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 *
 * @author Adriana Trujillo
 */
public class FacturaDTO implements Serializable{
    
    Long 
            id;
    private Integer
            noFactura,
            valor;
    private String
            fecha,
            metodoPago;
    
    public FacturaDTO(){}
    
    public FacturaDTO(FacturaEntity facturaEntity)
    {
        if( facturaEntity != null)
        {
        this.id = facturaEntity.getId();
        this.noFactura = facturaEntity.getNoFactura();
        this.valor = facturaEntity.getValor();
        this.fecha = facturaEntity.getFecha();
        this.metodoPago = facturaEntity.getMetodoPago();
        }
    }
    
    public Long getId()
    { return id;}
    
    public void setId(Long pId)
    { this.id = pId;}
    
    public Integer getNoFactura()
    {return noFactura;}
    
    public void setNoFactura( Integer pNumero)
    {this.noFactura = pNumero;}
    
    public Integer getValor()
    {return valor;}
    
    public void setValor( Integer pValor)
    {this.valor = pValor;}
    
    public String getFecha()
    {return fecha;}
    
    public void setFecha( String pFecha)
    {this.fecha = pFecha;}
    
    public String getMetogoPago()
    {return metodoPago;}
    
    public void setMetodoPago( String pMetodo)
    {this.metodoPago = pMetodo;}
    
    public FacturaEntity toEntity()
    {
        FacturaEntity facturaEntity = new FacturaEntity();
        facturaEntity.setNoFactura(this.noFactura);
        facturaEntity.setValor(this.valor);
        facturaEntity.setFecha(this.fecha);
        facturaEntity.setMetodoPago(this.metodoPago);
        
        return facturaEntity;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
