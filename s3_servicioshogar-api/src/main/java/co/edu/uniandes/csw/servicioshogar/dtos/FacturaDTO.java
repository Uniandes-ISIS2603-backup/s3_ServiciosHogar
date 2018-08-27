/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.FacturaEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class FacturaDTO {

    private Long id;
    private Integer noFactura, valor;
    private String fecha;
    
    public FacturaDTO() {
    }
    
    public FacturaDTO(FacturaEntity facturaEntity) {
        if (facturaEntity != null) {
            this.id = facturaEntity.getId();
            this.noFactura = facturaEntity.getNoFactura();
            this.fecha = facturaEntity.getFecha();
            this.valor = facturaEntity.getValor();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(Integer noFactura) {
        this.noFactura = noFactura;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public FacturaEntity tuenEntity(){
        FacturaEntity facturaEntity = new FacturaEntity();
        facturaEntity.setId(this.id);
        facturaEntity.setNoFactura(this.noFactura);
        facturaEntity.setValor(this.valor);
        facturaEntity.setFecha(this.fecha);
        return facturaEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
