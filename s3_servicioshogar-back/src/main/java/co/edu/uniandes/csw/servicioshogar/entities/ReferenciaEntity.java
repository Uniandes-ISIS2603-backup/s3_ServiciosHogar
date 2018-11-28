/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

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

    public void setHojaDeVida(HojaDeVidaEntity hojaDeVida) {
        this.hojaDeVida = hojaDeVida;
    }

    public HojaDeVidaEntity getHojaDeVida() {
        return hojaDeVida;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.empresa);
        hash = 29 * hash + Objects.hashCode(this.nombreRemitente);
        hash = 29 * hash + Objects.hashCode(this.idRemitente);
        hash = 29 * hash + Objects.hashCode(this.telRemitente);
        hash = 29 * hash + Objects.hashCode(this.cargo);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.parentesco);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReferenciaEntity other = (ReferenciaEntity) obj;
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        if (!Objects.equals(this.nombreRemitente, other.nombreRemitente)) {
            return false;
        }
        if (!Objects.equals(this.cargo, other.cargo)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.parentesco, other.parentesco)) {
            return false;
        }
        if (!Objects.equals(this.idRemitente, other.idRemitente)) {
            return false;
        }
        return Objects.equals(this.telRemitente, other.telRemitente);
    }
    
    
}
