/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.SolicitudDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.SolicitudLogic;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
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
 * Clase que implementa el recurso "solicidudes".
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Path("solicitudes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SolicitudResource {
    
    private static final Logger LOGGER = Logger.getLogger(SolicitudResource.class.getName());
    
    @Inject
    SolicitudLogic solicitudLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva solicitud con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param solicitud {@link SolicitudDTO} - La solicitud que se desea
     * guardar.
     * @return JSON {@link SolicitudDTO} - La solicitud guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la solicitud.
     */
    @POST
    public SolicitudDTO crearSolicitud(SolicitudDTO solicitud) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudResource createSolicitud: input: {0}", solicitud.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        SolicitudEntity solicitudEntity = solicitud.toEntity();
        // Invoca la lógica para crear la solicitud nueva
        SolicitudEntity nuevoSolicitudEntity = solicitudLogic.createSolicitud(solicitudEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        SolicitudDTO nuevoSolicitudDTO = new SolicitudDTO(nuevoSolicitudEntity);
        LOGGER.log(Level.INFO, "SolicitudResource createSolicitud: output: {0}", nuevoSolicitudDTO.toString());
        return nuevoSolicitudDTO;
    }

    /**
     * Busca y devuelve todas las solicitudes que existen en la aplicacion.
     *
     * @return JSONArray {@link SolicitudDTO} - Las solicitudes encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<SolicitudDTO> getSolicitudes() {
        LOGGER.info("ServicioResource getServicios: input: void");
        List<SolicitudDTO> listaServicios = listEntity2DetailDTO(solicitudLogic.getSolicitudes());
        LOGGER.log(Level.INFO, "ServicioResource getServicios: output: {0}", listaServicios.toString());
        return listaServicios;
    }

    /**
     * Busca la solicitud con el id asociado recibido en la URL y la devuelve.
     *
     * @param solicitudesId Identificador de la solicitud que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link SolicitudDTO} - La solicitud buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitud.
     */
    @GET
    @Path("{solicitudesId: \\d+}")
    public SolicitudDTO getSolicitud(@PathParam("solicitudesId") Long solicitudesId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "SolicitudResource getSolicitud: input: {0}", solicitudesId);
        SolicitudEntity solicitudEntity = solicitudLogic.getSolicitud(solicitudesId);
        if (solicitudEntity == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudesId + " no existe.", 404);
        }
        SolicitudDTO detailDTO = new SolicitudDTO(solicitudEntity);
        LOGGER.log(Level.INFO, "olicitudResource getolicitud: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la solicitud con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param solicitudesId Identificador de la solicitud que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param solicitud {@link SolicitudDTO} La solicitud que se desea guardar.
     * @return JSON {@link SolicitudDTO} - La solicitud guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitud a
     * actualizar.
     */
    @PUT
    @Path("{solicitudesId: \\d+}")
    public SolicitudDTO updateSolicitud(@PathParam("solicitudesId") Long solicitudesId, SolicitudDTO solicitud) throws WebApplicationException {
        LOGGER.log(Level.INFO, "SolicitudResource updateSolicitud: input: id:{0} , solicitud: {1}", new Object[]{solicitudesId, solicitud.toString()});
        solicitud.setId(solicitudesId);
        if (solicitudLogic.getSolicitud(solicitudesId) == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudesId + " no existe.", 404);
        }
        SolicitudDTO detailDTO = new SolicitudDTO(solicitudLogic.updateSolicitud(solicitudesId, solicitud.toEntity()));
        LOGGER.log(Level.INFO, "SolicitudResource updateSolicitud: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la solicitud con el id asociado recibido en la URL.
     *
     * @param solicitudesId Identificador de la solicitud que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la solicitud.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitud.
     */
    @DELETE
    @Path("{solicitudesId: \\d+}")
    public void deleteSolicitud(@PathParam("solicitudesId") Long solicitudesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudResource deleteSolicitud: input: {0}", solicitudesId);
        if (solicitudLogic.getSolicitud(solicitudesId) == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudesId + " no existe.", 404);
        }
        solicitudLogic.deleteSolicitud(solicitudesId);
        LOGGER.info("SolicitudResource deleteSolicitud: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos SolicitudEntity a una lista de
     * objetos SolicitudDTO (json)
     *
     * @param entityList corresponde a la lista de solicitudes de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de solicitudes en forma DTO (json)
     */
    private List<SolicitudDTO> listEntity2DetailDTO(List<SolicitudEntity> entityList) {
        List<SolicitudDTO> list = new ArrayList<>();
        for (SolicitudEntity entity : entityList) {
            list.add(new SolicitudDTO(entity));
        }
        return list;
    }
}
