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
    private Long idRemitente;
    private Long telRemitente;
    private String cargo;
    private String email;
    private String parentesco;
    
    public ReferenciaDTO(){}
    
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
    
    public Long getIdRemitente(){
        return idRemitente;
    }

    public void setIdRemitente(Long idRemitente){
        this.idRemitente = idRemitente;
    }
    
    public Long getTelRemitente() {
        return telRemitente;
    }

    public void setTelRemitente(Long telRemitente) {
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
    
    /**
     * Convierte DTO a Entity
     * @return entidad de la referencia.
     */
    public ReferenciaEntity toEntity() 
    {
        ReferenciaEntity referenciaEntity = new ReferenciaEntity();
        referenciaEntity.setEmpresa(this.empresa);
        referenciaEntity.setNombreRemitente(this.nombreRemitente);
        referenciaEntity.setIdRemitente(this.idRemitente);
        referenciaEntity.setTelRemitente(this.telRemitente);
        referenciaEntity.setCargo(this.cargo);
        referenciaEntity.setEmail(this.email);
        referenciaEntity.setParentesco(this.parentesco);
        
        return referenciaEntity;
    }    
    
    
}
    