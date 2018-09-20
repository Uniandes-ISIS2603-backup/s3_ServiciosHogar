/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.HabilidadDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.HabilidadLogic;
import co.edu.uniandes.csw.servicioshogar.entities.HabilidadEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Maria Ocampo
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped  
public class HabilidadResource {
    
    private static final Logger LOGGER = Logger.getLogger(PrestadorResource.class.getName());
    
    @Inject
    private HabilidadLogic habilidadLogic;
    /**
     * 
     * @param habilidad
     * @return 
     */
    @POST
    public HabilidadDTO createHabilidad(@PathParam("prestadorId") Long prestadorId, HabilidadDTO  habilidad) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "HabilidadResource createHabilidad: input: {0}", habilidad.toString());
        HabilidadDTO nuevoHabilidadDTO = new HabilidadDTO(habilidadLogic.createHabilidad(prestadorId, habilidad.toEntity()));
        LOGGER.log(Level.INFO, "HabilidadResource createHabilidad: output: {0}", nuevoHabilidadDTO.toString());
        return nuevoHabilidadDTO;
    }
    
    /**
     * 
     * @return 
     */
    @GET
    public List<HabilidadDTO> getHabilidades(@PathParam("prestadorId") Long prestadorId)
    {
        LOGGER.log(Level.INFO, "HabilidadResource getHabilidades: input: {0}", prestadorId);
        List<HabilidadDTO> listaDTOs = listEntity2DTO(habilidadLogic.getHabilidades(prestadorId));
        LOGGER.log(Level.INFO, "HabilidadResource getHabilidades: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }
    
    /**
     * 
     * @param habilidadId
     * @return 
     */
    @GET
    @Path("{habilidadesId: \\d+}")
    public HabilidadDTO getHabilidad(@PathParam("prestadorId") Long prestadorId, @PathParam("habilidadesId") Long habilidadId)
    {
        LOGGER.log(Level.INFO, "HabilidadResource getHabilidad: input: {0}", habilidadId);
        HabilidadEntity hEntity = habilidadLogic.getHabilidad(prestadorId, habilidadId);
        if(hEntity == null)
            throw new WebApplicationException("El recurso /prestadores/"+prestadorId+"/habilidades/"+habilidadId+"no existe", 404);
        
        HabilidadDTO HabilidadDTO = new HabilidadDTO(hEntity);
        LOGGER.log(Level.INFO, "HabilidadResource getHabilidad: output: {0}", HabilidadDTO.toString());
        return HabilidadDTO;
    }
    
    /**
     * 
     * @param habilidadId
     * @param habilidad
     * @return 
     */
    @PUT
    @Path("{habilidadesId: \\d+}")
    public HabilidadDTO updatHabilidad(@PathParam("prestadorId") Long prestadorId, @PathParam("habilidadesId") Long habilidadId, HabilidadDTO habilidad) throws BusinessLogicException{
        
        LOGGER.log(Level.INFO, "HabilidadResource updateHabilidad: input: prestadorId: {0} , habilidadId: {1} , habilidad:{2}", new Object[]{prestadorId, habilidadId, habilidad.toString()});
        if (habilidad.getId().equals(habilidadId)) {
            throw new BusinessLogicException("Los ids de la Habilidad no coinciden.");
        }
        HabilidadEntity entity = habilidadLogic.getHabilidad(prestadorId, habilidadId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /prestadores/" + prestadorId + "/habilidades/" + habilidadId + " no existe.", 404);

        }
        HabilidadDTO habilidadDTO = new HabilidadDTO(habilidadLogic.updateHabilidad(prestadorId, habilidad.toEntity()));
        LOGGER.log(Level.INFO, "HabilidadResource updateHabilidad: output:{0}", habilidadDTO.toString());
        return habilidadDTO;

    }
    
    /**
     * 
     * @param habilidadId 
     */
    @DELETE
    @Path("{habilidadesId: \\d+}")
    public void deleteHabilidad(@PathParam("prestadorId") Long prestadorId,@PathParam("habilidadesId") Long habilidadId) throws BusinessLogicException
    {
        HabilidadEntity entity = habilidadLogic.getHabilidad(prestadorId, habilidadId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /prestadores/" + prestadorId + "/habilidades/" + habilidadId + " no existe.", 404);
        }
        habilidadLogic.deleteHabilidad(prestadorId, habilidadId);
    }
    
     /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrizeEntity a una lista de
     * objetos ReviewDTO (json)
     *
     * @param entityList corresponde a la lista de reseñas de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de reseñas en forma DTO (json)
     */
    private List<HabilidadDTO> listEntity2DTO(List<HabilidadEntity> entityList) {
        List<HabilidadDTO> list = new ArrayList<HabilidadDTO>();
        for (HabilidadEntity entity : entityList) {
            list.add(new HabilidadDTO(entity));
        }
        return list;
    }
}
