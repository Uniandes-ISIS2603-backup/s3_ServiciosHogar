/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class PrestadorDetailDTO extends PrestadorDTO implements Serializable{
    
    /**
     * Lista de habilidades asociadas a un prestador.
     */
    //TODO Cambiar por HabilidadDTO
    private List<String> habilities;
    
    /**
     * Constructor por defecto.
     */
    public PrestadorDetailDTO(){
        
    }
    
    /**
     * 
     * @param prestadorEntity 
     */
    public PrestadorDetailDTO(PrestadorEntity prestadorEntity){
        super(prestadorEntity);
        if(prestadorEntity != null)
        {
            if(prestadorEntity.getHabilities()!= null)
                {
                    habilities = new ArrayList<>();
                    for(String entityHability : prestadorEntity.getHabilities())
                    {
                        //TODO 
                        habilities.add(entityHability);
                    }
                }
        }
    }
    
    public PrestadorEntity  toEntity(){
        PrestadorEntity prestadorEntity = super.toEntity();
        if(habilities != null)
        {
            List<String> habilitiesEntity = new ArrayList<>();
            for(String hability : habilities)
            {
                habilitiesEntity.add(hability);
            }
            
            prestadorEntity.setHabilities(habilitiesEntity);
        }
        
        return prestadorEntity;
    }
    
    /**
     * Devuelbe las habilidades del prestador
     * @return Habilidades
     */
    public List<String> getHabilities() {
        return habilities;
    }
    
    /**
     * Modifica las habilidades del prestador
     * @param habilities Nuevas habilidades
     */
    public void setHabilities(List<String> habilities) {
        this.habilities = habilities;
    }
    
      @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
