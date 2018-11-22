/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.SolicitudDTO;
import co.edu.uniandes.csw.servicioshogar.dtos.SolicitudDetailDTO;
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
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SolicitudResource {
    private static final Logger LOGGER = Logger.getLogger(SolicitudResource.class.getName());
    private static final String noExiste= " no existe.";
    private static final String solici= "/solicitudes/";
    @Inject
    private SolicitudLogic solicitudLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un nuevo solicitud con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param clientesId
     * @param solicitud {@link SolicitudDTO} - EL solicitud que se desea guardar.
     * @return JSON {@link SolicitudDTO} - El solicitud guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el solicitud o el isbn es
     * inválido o si la editorial ingresada es invalida.
     */
    @POST
    public SolicitudDTO createSolicitud(@PathParam("clientesId") Long clientesId, SolicitudDTO solicitud) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudResource createSolicitud: input: {0}", solicitud);
        SolicitudDTO nuevoSolicitudDTO = new SolicitudDTO(solicitudLogic.createSolicitud(clientesId, solicitud.toEntity()));
        LOGGER.log(Level.INFO, "SolicitudResource createSolicitud: output: {0}", nuevoSolicitudDTO);
        return nuevoSolicitudDTO;
    }

    /**
     * Busca y devuelve todos los solicitudes que existen en la aplicacion.
     *
     * @param clientesId
     * @return JSONArray {@link SolicitudDetailDTO} - Los solicitudes encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<SolicitudDetailDTO> getSolicitudes(@PathParam("clientesId") Long clientesId) {
        LOGGER.info("SolicitudResource getSolicitudes: input: void");
        List<SolicitudDetailDTO> listaSolicitudes = listEntity2DetailDTO(solicitudLogic.getSolicitudes(clientesId));
        LOGGER.log(Level.INFO, "SolicitudResource getSolicitudes: output: {0}", listaSolicitudes);
        return listaSolicitudes;
    }

    /**
     * Busca el solicitud con el id asociado recibido en la URL y lo devuelve.
     *
     * @param clientesId
     * @param solicitudesId Identificador del solicitud que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SolicitudDetailDTO} - El solicitud buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el solicitud.
     */
    @GET
    @Path("{solicitudesId: \\d+}")
    public SolicitudDetailDTO getSolicitud(@PathParam("clientesId") Long clientesId, @PathParam("solicitudesId") Long solicitudesId) {
        LOGGER.log(Level.INFO, "SolicitudResource getSolicitud: input: {0}", solicitudesId);
        SolicitudEntity solicitudEntity = solicitudLogic.getSolicitud(clientesId, solicitudesId);
        if (solicitudEntity == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudesId + noExiste, 404);
        }
        SolicitudDetailDTO solicitudDetailDTO = new SolicitudDetailDTO(solicitudEntity);
        LOGGER.log(Level.INFO, "SolicitudResource getSolicitud: output: {0}", solicitudDetailDTO);
        return solicitudDetailDTO;
    }

    /**
     * Actualiza el solicitud con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param clientesId
     * @param solicitudesId Identificador del solicitud que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param solicitud {@link SolicitudDTO} El solicitud que se desea guardar.
     * @return JSON {@link SolicitudDetailDTO} - El solicitud guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el solicitud a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el solicitud.
     */
    @PUT
    @Path("{solicitudesId: \\d+}")
    public SolicitudDetailDTO updateSolicitud(@PathParam("clientesId") Long clientesId, @PathParam("solicitudesId") Long solicitudesId, SolicitudDTO solicitud) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudResource updateSolicitud: input: clientesId: {0} , solicitudesId: {1} , solicitud:{2}", new Object[]{clientesId, solicitudesId, solicitud});
        if (solicitudesId.equals(solicitud.getId())) {
            throw new BusinessLogicException("Los ids del Solicitud no coinciden.");
        }
        SolicitudEntity entity = solicitudLogic.getSolicitud(clientesId, solicitudesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + solici+ solicitudesId + noExiste, 404);

        }
        SolicitudDetailDTO solicitudDTO = new SolicitudDetailDTO(solicitudLogic.updateSolicitud(clientesId, solicitud.toEntity()));
        LOGGER.log(Level.INFO, "SolicitudResource updateSolicitud: output:{0}", solicitudDTO);
        return solicitudDTO;
    }

    /**
     * Borra el solicitud con el id asociado recibido en la URL.
     *
     * @param clientesId
     * @param solicitudesId Identificador del solicitud que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException
     * cuando el solicitud tiene autores asociados.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el solicitud.
     */
    @DELETE
    @Path("{solicitudesId: \\d+}")
    public void deleteSolicitud(@PathParam("clientesId") Long clientesId, @PathParam("solicitudesId") Long solicitudesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudResource deleteSolicitud: input: {0}", solicitudesId);
        SolicitudEntity entity = solicitudLogic.getSolicitud(clientesId, solicitudesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso clientes/"+clientesId+solici+ solicitudesId + noExiste, 404);
        }
        solicitudLogic.deleteSolicitud(clientesId, solicitudesId);
        LOGGER.info("SolicitudResource deleteSolicitud: output: void");
    }

    /**
     * Conexión con el servicio de reseñas para un solicitud. {@link ServicioResource}
     *
     * Este método conecta la ruta de /solicitudes con las rutas de /servicios que
     * dependen del solicitud, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param solicitudesId El ID del solicitud con respecto al cual se accede al
     * servicio.
     * @return El servicio de Reseñas para ese solicitud en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el solicitud.
     */
    @Path("{solicitudesId: \\d+}/servicios")
    public Class<ServicioResource> getServicioResource(@PathParam("clientesId") Long clientesId, @PathParam("solicitudesId") Long solicitudesId) {
        if (solicitudLogic.getSolicitud(clientesId, solicitudesId) == null) {
            throw new WebApplicationException("El recurso clientes/"+clientesId+solici + solicitudesId + "/servicios no existe.", 404);
        }
        return ServicioResource.class;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos SolicitudEntity a una lista de
     * objetos SolicitudDetailDTO (json)
     *
     * @param entityList corresponde a la lista de solicitudes de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de solicitudes en forma DTO (json)
     */
    private List<SolicitudDetailDTO> listEntity2DetailDTO(List<SolicitudEntity> entityList) {
        List<SolicitudDetailDTO> list = new ArrayList<>();
        for (SolicitudEntity entity : entityList) {
            list.add(new SolicitudDetailDTO(entity));
        }
        return list;
    }
}
