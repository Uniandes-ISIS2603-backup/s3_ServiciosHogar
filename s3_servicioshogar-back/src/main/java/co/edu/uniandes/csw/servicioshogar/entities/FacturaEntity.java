/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 *
 * @author Adriana Trujillo
 */
@Entity
public class FacturaEntity extends BaseEntity implements Serializable{
   private Integer
           noFactura,
           valor;
   
   private String
           fecha,
           metodoPago;
   
   @PodamExclude
   @OneToOne
   private SolicitudEntity solicitud;
   
   public Integer getNoFactura()
   { return noFactura;}
   
   public void setNoFactura( Integer pNumero)
   { this.noFactura = pNumero;}
   
   public Integer getValor()
   {return valor;}
   
   public void setValor(Integer pValor)
   { this.valor = pValor;}
   
   public String getFecha()
   { return fecha;}
   
   public void setFecha( String pFecha)
   { this.fecha = pFecha;}
   
   public String getMetodoPago()
   { return metodoPago;}
   
   public void setMetodoPago( String pMetodo)
   { this.metodoPago = pMetodo;}
   
   public SolicitudEntity getSolicitud()
   {return solicitud;}
   
   public void setSolicitud(SolicitudEntity solicitud)
   {this.solicitud = solicitud;}
    
}
