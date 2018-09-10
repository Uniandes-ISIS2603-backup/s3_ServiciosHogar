/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.ReferenciaEntity;
import java.io.Serializable;

/**
 *
 * @author Daniela Rocha Torres
 */
public class ReferenciaDTO implements Serializable {

   
    private String empresa;
    private String nombreRemitente;
    private int idRemitente;
    private int telRemitente;
    private String cargo;
    private String email;
    private String parentesco;
    
    public ReferenciaDTO(){
        
    }
    
    public ReferenciaDTO(ReferenciaEntity referenciaEntity) {
        if(referenciaEntity!=null){
            this.empresa = referenciaEntity.getEmpresa();
            this.nombreRemitente = referenciaEntity.getNombreRemitente();
            this.idRemitente = referenciaEntity.getIdRemitente();
            this.telRemitente = referenciaEntity.getTelRemitente();
            this.cargo = referenciaEntity.getCargo();
            this.email = referenciaEntity.getEmail();
            this.parentesco = referenciaEntity.getParentesco();
                   
        }
    }
    
    public ReferenciaEntity toEntity(ReferenciaEntity referenciaEntity ){
        ReferenciaEntity referencia = new ReferenciaEntity();
        
        referencia.setEmpresa(this.empresa);
        referencia.setNombreRemitente(this.nombreRemitente);
        referencia.setIdRemitente(this.idRemitente);
        referencia.setTelRemitente(this.telRemitente);
        referencia.setCargo(this.cargo);
        referencia.setEmail(this.email);
        referencia.setParentesco(this.parentesco);
      
        return referencia;
    } 
    
    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombreRemitente() {
        return nombreRemitente;
    }

    public void setNombreRemitente(String nombreRemitente) {
        this.nombreRemitente = nombreRemitente;
    }
    
    public int getIdRemitente(){
        return idRemitente;
    }

    public void setIdRemitente(int idRemitente){
        this.idRemitente = idRemitente;
    }
    
    public int getTelRemitente() {
        return telRemitente;
    }

    public void setTelRemitente(int telRemitente) {
        this.telRemitente = telRemitente;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
    
 
}
