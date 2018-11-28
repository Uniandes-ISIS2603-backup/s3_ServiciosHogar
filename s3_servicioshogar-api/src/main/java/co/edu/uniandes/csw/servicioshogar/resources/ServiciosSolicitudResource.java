/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.PrestadorDetailDTO;
import co.edu.uniandes.csw.servicioshogar.dtos.ServicioDTO;
import co.edu.uniandes.csw.servicioshogar.dtos.SolicitudDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.ServicioLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.ServiciosSolicitudLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.SolicitudLogic;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
public class ServiciosSolicitudResource {
    
    private static final Logger LOGGER = Logger.getLogger(ServiciosPrestadorResource.class.getName());
    private static final String NO_EXISTE = " no existe.";
    private static final String RECURSO = "El recurso /solicitudes/";
    private static final String SERVICIO = "/servicios/";
    @Inject
    private ServicioLogic servicioLogic;
    
    @Inject
    private ServiciosSolicitudLogic serviciosPrestadorLogic;
    
    @Inject
    private SolicitudLogic prestadorLogic;
    
    @PUT
    public ServicioDTO replaceSolicitud(@PathParam("servicioId") Long servicioId, SolicitudDTO solicitudDTO)
    {
        LOGGER.log(Level.INFO, "ServiciosSolicitudResource replacePRestador: input: servicioId{0}, Solicitud{1}", new Object[]{servicioId,solicitudDTO});
        if(servicioLogic.getServicioById(servicioId) == null)
            throw new WebApplicationException("El recurso "+SERVICIO+servicioId+NO_EXISTE,404);
        if(prestadorLogic.getSolicitudById(solicitudDTO.getId()) == null)
            throw new WebApplicationException(RECURSO+solicitudDTO.getId()+NO_EXISTE, 404);
        ServicioDTO servicioDTO = new ServicioDTO(serviciosPrestadorLogic.replaceSolicitud(solicitudDTO.getId(), servicioId));
        LOGGER.log(Level.INFO, "ServiciosSolicitudResource replaceSolicitud: output: {0}", servicioDTO);
        return servicioDTO;
    }

    /**
     * Busca la prestador con el id asociado recibido en la URL y la devuelve.
     *
     * @param solicitudesId
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link PrestadorDetailDTO} - La prestador buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la prestador.
     */
    @GET
    @Path("{solicitudesId: \\d+}")
    public SolicitudDTO getPrestador(@PathParam("solicitudesId") Long solicitudesId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador: input: {0}", solicitudesId);
        SolicitudEntity prestadorEntity = prestadorLogic.getSolicitudById(solicitudesId);
        if (prestadorEntity == null) {
            throw new WebApplicationException(RECURSO + solicitudesId + NO_EXISTE, 404);
        }
        SolicitudDTO detailDTO = new SolicitudDTO(prestadorEntity);
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador: output: {0}", detailDTO);
        return detailDTO;
    }
}
