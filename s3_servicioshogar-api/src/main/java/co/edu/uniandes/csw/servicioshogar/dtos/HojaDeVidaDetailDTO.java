/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.HojaDeVidaEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ReferenciaEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniela Rocha Torres
 */
public class HojaDeVidaDetailDTO extends HojaDeVidaDTO implements Serializable{
    private List<ReferenciaDTO> referencias;
    
    private HojaDeVidaDetailDTO(){super();}
    
 
    public HojaDeVidaDetailDTO(HojaDeVidaEntity hojaDeVidaEntity) 
    {
        super(hojaDeVidaEntity);
        if (hojaDeVidaEntity.getReferencias()!= null) 
        {
            referencias = new ArrayList<>();
            for (ReferenciaEntity entityReferencia : hojaDeVidaEntity.getReferencias()) 
                referencias.add(new ReferenciaDTO(entityReferencia));
            
        }
    }


    @Override
    public HojaDeVidaEntity toEntity() {
        HojaDeVidaEntity hojaDeVidaEntity = super.toEntity();
        if (referencias != null) {
            List<ReferenciaEntity> referenciasEntity = new ArrayList<>();
            for (ReferenciaDTO dtoReview : getReferencias()) 
                referenciasEntity.add(dtoReview.toEntity());
            
            hojaDeVidaEntity.setReferencias(referenciasEntity);
        }        
        return hojaDeVidaEntity;
    }


    public List<ReferenciaDTO> getReferencias() {return referencias;}


    public void setReferencias(List<ReferenciaDTO> referencias) {this.referencias = referencias;}

}
