/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 *
 * @author Daniela Rocha Torres
 */
@Entity
public class ReferenciaEntity extends BaseEntity implements Serializable{
          
    private String empresa;
    private String nombreRemitente;
    private Long idRemitente;
    private Long telRemitente;
    private String cargo;
    private String email;
    private String parentesco;
    
    @PodamExclude
    @ManyToOne()
    private HojaDeVidaEntity hojaDeVida;

    
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
}
