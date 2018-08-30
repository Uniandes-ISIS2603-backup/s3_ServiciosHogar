/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.HabilidadEntity;
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
    private List<HabilidadDTO> habilities;
    
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
            if(prestadorEntity.getHabilidades()!= null)
                {
                    habilities = new ArrayList<>();
                    for(HabilidadEntity entityHability : prestadorEntity.getHabilidades())
                    {
                      habilities.add(new HabilidadDTO(entityHability));
                    }
                }
        }
    }
    
    public PrestadorEntity  toEntity(){
        PrestadorEntity prestadorEntity = super.toEntity();
        if(habilities != null)
        {
            List<HabilidadEntity> habilitiesEntity = new ArrayList<>();
            for(HabilidadDTO hability : habilities)
            {
                habilitiesEntity.add(hability.toEntity());
            }
            
            prestadorEntity.setHabilidades(habilitiesEntity);
        }
        
        return prestadorEntity;
    }
    
    /**
     * Devuelbe las habilidades del prestador
     * @return Habilidades
     */
    public List<HabilidadDTO> getHabilities() {
        return habilities;
    }
    
    /**
     * Modifica las habilidades del prestador
     * @param habilities Nuevas habilidades
     */
    public void setHabilities(List<HabilidadDTO> habilities) {
        this.habilities = habilities;
    }
    
      @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
