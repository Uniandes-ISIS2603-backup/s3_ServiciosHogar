/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.CalificacionDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.CalificacionLogic;
import co.edu.uniandes.csw.servicioshogar.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Carlos Eduardo Robles
 */
@Produces("application/json")
@Consumes("application/json")
public class CalificacionResource 
{
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());

    @Inject
    private CalificacionLogic calificacionLogic;
    
    @POST
    public CalificacionDTO createCalificacion(@PathParam("serviciosId") Long serviciosId, CalificacionDTO calificacion) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: input: {0}", calificacion.toString());
        CalificacionDTO nuevaCalificacionDTO = new CalificacionDTO(calificacionLogic.createCalificacion(serviciosId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: output: {0}", nuevaCalificacionDTO.toString());
        return nuevaCalificacionDTO;
    }
    
    /*@GET
    public List<CalificacionDTO> getReviews(@PathParam("serviciosId") Long serviciosId) {
        LOGGER.log(Level.INFO, "CalificacionResource getReviews: input: {0}", serviciosId);
        List<CalificacionDTO> listaDTOs = listEntity2DTO(calificacionLogic.getReviews(serviciosId));
        LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }*/
    
    @GET
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("serviciosId") Long serviciosId, @PathParam("calificacionId") Long calificacionId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: input: {0}", calificacionId);
        CalificacionEntity entity = calificacionLogic.getCalificacion(serviciosId, calificacionId);
        if (entity == null) 
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + "/calificacion/" + calificacionId + " no existe.", 404);
        
        CalificacionDTO calificacionDTO = new CalificacionDTO(entity);
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", calificacionDTO.toString());
        return calificacionDTO;
    }
    
    @PUT
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("serviciosId") Long serviciosId, @PathParam("calificacionId") Long calificacionId, CalificacionDTO calificacion) throws BusinessLogicException 
    {
                LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: serviciosId: {0} , calificacionId: {1} , calificacion:{2}", new Object[]{serviciosId, calificacionId, calificacion.toString()});
        if (calificacionId.equals(calificacion.getId()))
            throw new BusinessLogicException("Los ids del Review no coinciden.");
        
        CalificacionEntity entity = calificacionLogic.getCalificacion(serviciosId, calificacionId);
        if (entity == null)
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + "/calificacion/" + calificacionId + " no existe.", 404);

        
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.updateCalificacion(serviciosId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: output:{0}", calificacionDTO.toString());
        return calificacionDTO;

    }
    
    @DELETE
    @Path("{calificacionId: \\d+}")
    public void deleteCalificacion(@PathParam("serviciosId") Long serviciosId, @PathParam("calificacionId") Long calificacionId) throws BusinessLogicException {
        CalificacionEntity entity = calificacionLogic.getCalificacion(serviciosId, calificacionId);
        if (entity == null) 
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + "/calificacion/" + calificacionId + " no existe.", 404);
        
        calificacionLogic.deleteCalificacion(serviciosId, calificacionId);
    }
    
    /*private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<CalificacionDTO>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }*/
}
