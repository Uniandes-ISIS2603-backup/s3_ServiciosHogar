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
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
public class FacturaDTO {
    
    private long id;
    private Integer noFactura, valor;
    private String fecha;

    public FacturaDTO() {
    }
    
    public FacturaDTO(FacturaEntity facturaEntity){
        if(facturaEntity!=null){
            this.id=facturaEntity.getId();
            this.noFactura=facturaEntity.getNoFactura();
            this.valor=facturaEntity.getValor();
            this.fecha=facturaEntity.getFecha();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    
    public FacturaEntity toEntity(){
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
