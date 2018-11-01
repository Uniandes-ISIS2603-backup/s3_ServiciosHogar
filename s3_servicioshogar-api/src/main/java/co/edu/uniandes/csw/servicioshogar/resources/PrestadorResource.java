/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.PrestadorDTO;
import co.edu.uniandes.csw.servicioshogar.dtos.PrestadorDetailDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorLogic;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
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
@Path("prestadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PrestadorResource {
 
    private static final Logger LOGGER = Logger.getLogger(PrestadorResource.class.getName());
    
    @Inject
    private PrestadorLogic prestadorLogic;
    /**
     * 
     * @param prestador
     */
    @POST
    public PrestadorDTO createPrestador(PrestadorDTO  prestador) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "PrestadorResource createPrestador : input {0}", prestador.toString());
        PrestadorDetailDTO nuevoPrestadorDTO = new PrestadorDetailDTO(prestadorLogic.createPrestador(prestador.toEntity()));
        LOGGER.log(Level.INFO, "PrestadorResource createPrestador : outpu {0}", nuevoPrestadorDTO.toString());
        return nuevoPrestadorDTO;
    }
    
    /**
     * 
     * @return 
     */
    @GET
    public List<PrestadorDetailDTO> getPrestadores()
    {
        LOGGER.log(Level.INFO, "PrestadorResource getPrestadores : input: void");
        List<PrestadorDetailDTO> listaPrestadores = listEntity2DetailDTO(prestadorLogic.getPrestadores());
        LOGGER.log(Level.INFO, "PrestadorResource getPrestadores : outpu {0}", listaPrestadores.toString());
        return listaPrestadores;
    }
    
    @GET
    @Path("{prestadoresId: \\d+}")
    public PrestadorDetailDTO getPrestador(@PathParam("prestadoresId") Long prestadorId)
    {
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador : input {0}", prestadorId);
        PrestadorEntity pEntity = prestadorLogic.getPrestador(prestadorId);
        if(pEntity == null)
            throw new WebApplicationException("El recurso /prestadores/"+prestadorId+" no existe", 404);
        PrestadorDetailDTO prestadorDetailDTO = new PrestadorDetailDTO(pEntity);
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador : outpu {0}", prestadorDetailDTO.toString());
        return prestadorDetailDTO;
    }
    
    /**
     * 
     * @param prestadoresId
     * @param prestador
     * @return 
     * /prestadores/id/habilidades/id
     */
    @PUT
    @Path("{prestadoresId: \\d+}")
    public PrestadorDetailDTO updatePrestador(@PathParam("prestadoresId") Long prestadorId, PrestadorDTO prestador) throws BusinessLogicException{
        
        LOGGER.log(Level.INFO, "PrestadorResource updatePrestador : input id: {0} , book: {1}", new Object[]{prestadorId, prestador.toString()});
        prestador.setId(prestadorId);
        if(prestadorLogic.getPrestador(prestadorId) == null) 
            throw new WebApplicationException("El recurso /prestadores/"+prestadorId+" no existe", 404);
        
        PrestadorDetailDTO detailDTO = new PrestadorDetailDTO(prestadorLogic.updatePrestador(prestadorId, prestador.toEntity()));
        LOGGER.log(Level.INFO, "PrestadorResource updatePrestador : outpu {0}", detailDTO.toString());
        return detailDTO;
    }
    
    @DELETE
    @Path("{prestadoresId: \\d+}")
    public void deletePrestador(@PathParam("prestadoresId") Long prestadorId)
    {
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador : input {0}", prestadorId);
        PrestadorEntity pEntity = prestadorLogic.getPrestador(prestadorId);
        if(pEntity == null)
            throw new WebApplicationException("El recurso /prestadores/"+prestadorId+" no existe", 404);
        prestadorLogic.deletePrestador(prestadorId);
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador : outpu : void");        
    }
    
    @Path("{prestadorId: \\d+}/habilidades")
    public Class<HabilidadResource> getHabilidadResource(@PathParam("prestadorId") Long prestadorId) {
        if (prestadorLogic.getPrestador(prestadorId) == null) {
            throw new WebApplicationException("El recurso /prestadores/" + prestadorId + "/habilidades no existe.", 404);
        }
        return HabilidadResource.class;
    }
    
    @Path("{prestadorId: \\d+}/hojaDeVida")
    public Class<HojaDeVidaResource> getHojaDeVidaResource(@PathParam("prestadorId") Long prestadorId) {
        if (prestadorLogic.getPrestador(prestadorId) == null) {
            throw new WebApplicationException("El recurso /prestador/" + prestadorId + " no existe.", 404);
        }
        return HojaDeVidaResource.class;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos BookEntity a una lista de
     * objetos BookDetailDTO (json)
     *
     * @param entityList corresponde a la lista de libros de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de libros en forma DTO (json)
     */
    private List<PrestadorDetailDTO> listEntity2DetailDTO(List<PrestadorEntity> entityList) {
        List<PrestadorDetailDTO> list = new ArrayList<>();
        for (PrestadorEntity entity : entityList) {
            list.add(new PrestadorDetailDTO(entity));
        }
        return list;
    }
    
}
