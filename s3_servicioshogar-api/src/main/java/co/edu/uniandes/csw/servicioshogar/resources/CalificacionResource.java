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
    
    private static final String CALIFICACION = "/calificacion/";

    @Inject
    private CalificacionLogic calificacionLogic;
    
    @POST
    public CalificacionDTO createCalificacion(@PathParam("solicitudesId") Long solicitudesId, @PathParam("serviciosId") Long serviciosId, CalificacionDTO calificacion) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: input: {0}", calificacion);
        CalificacionDTO nuevaCalificacionDTO = new CalificacionDTO(calificacionLogic.createCalificacion(solicitudesId, serviciosId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: output: {0}", nuevaCalificacionDTO);
        return nuevaCalificacionDTO;
    }
    
    @GET
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("serviciosId") Long serviciosId, @PathParam("calificacionId") Long calificacionId)
    {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: input: {0}", calificacionId);
        CalificacionEntity entity = calificacionLogic.getCalificacion(serviciosId, calificacionId);
        if (entity == null) 
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + CALIFICACION + calificacionId + " no existe.", 404);
        
        CalificacionDTO calificacionDTO = new CalificacionDTO(entity);
        System.out.println("Creo DTO");
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", calificacionDTO.toString());
        return calificacionDTO;
    }
    
    @PUT
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("solicitudesId") Long solicitudesId,@PathParam("serviciosId") Long serviciosId, @PathParam("calificacionId") Long calificacionId, CalificacionDTO calificacion) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: serviciosId: {0} , calificacionId: {1} , calificacion:{2}", new Object[]{serviciosId, calificacionId, calificacion.toString()});
        
        CalificacionEntity entity = calificacionLogic.getCalificacion(serviciosId, calificacionId);
        if (entity == null)
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + CALIFICACION + calificacionId + " no existe.", 404);

        
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.updateCalificacion(solicitudesId, serviciosId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: output:{0}", calificacionDTO.toString());
        return calificacionDTO;
        
    }
    
    @DELETE
    @Path("{calificacionId: \\d+}")
    public void deleteCalificacion(@PathParam("serviciosId") Long serviciosId, @PathParam("calificacionId") Long calificacionId) throws BusinessLogicException {
        CalificacionEntity entity = calificacionLogic.getCalificacion(serviciosId, calificacionId);
        if (entity == null) 
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + CALIFICACION + calificacionId + " no existe.", 404);
        
        calificacionLogic.deleteCalificacion(serviciosId, calificacionId);
    }
}