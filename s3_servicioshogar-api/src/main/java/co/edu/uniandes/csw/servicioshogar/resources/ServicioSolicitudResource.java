/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.ServicioDTO;
import co.edu.uniandes.csw.servicioshogar.dtos.SolicitudDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.ServicioLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.ServicioSolicitudLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.SolicitudLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Path("servicios/{serviciosId: \\d+}/solicitud")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicioSolicitudResource {
    private static final Logger LOGGER = Logger.getLogger(ServicioSolicitudResource.class.getName());

    @Inject
    private ServicioLogic servicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ServicioSolicitudLogic servicioSolicitudLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private SolicitudLogic solicitudLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Remplaza la instancia de Solicitud asociada a un Servicio.
     *
     * @param serviciosId Identificador del libro que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param solicitud La solicitud que se será del libro.
     * @return JSON {@link ServicioDTO} - El arreglo de libros guardado en la
     * solicitud.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitud o el
     * libro.
     */
    @PUT
    public ServicioDTO replaceSolicitud(@PathParam("serviciosId") Long serviciosId, SolicitudDTO solicitud) {
        LOGGER.log(Level.INFO, "ServicioSolicitudResource replaceSolicitud: input: serviciosId{0} , Solicitud:{1}", new Object[]{serviciosId, solicitud.toString()});
        if (servicioLogic.getServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
        }
        if (solicitudLogic.getSolicitud(solicitud.getId()) == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitud.getId() + " no existe.", 404);
        }
        ServicioDTO servicioDetailDTO = new ServicioDTO(servicioSolicitudLogic.replaceSolicitud(serviciosId, solicitud.getId()));
        LOGGER.log(Level.INFO, "ServicioSolicitudResource replaceSolicitud: output: {0}", servicioDetailDTO.toString());
        return servicioDetailDTO;
    }
}
