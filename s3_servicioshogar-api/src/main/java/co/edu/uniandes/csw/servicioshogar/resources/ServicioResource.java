/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.ServicioDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.ServicioLogic;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
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
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Produces("application/json")
@Consumes("application/json")
public class ServicioResource {
    private static final Logger LOGGER = Logger.getLogger(ServicioResource.class.getName());
    private static final String noExiste = " no existe.";
    private static final String serv="/servicios/";
    private static final String soli="El recurso /solicitudes/";
    @Inject
    private ServicioLogic servicioLogic;

    /**
     * Crea una nueva reseña con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param clientesId
     * @param solicitudesId El ID del solicitud del cual se le agrega la reseña
     * @param servicio {@link ServicioDTO} - La reseña que se desea guardar.
     * @return JSON {@link ServicioDTO} - La reseña guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la reseña.
     */
    @POST
    public ServicioDTO createServicio(@PathParam("clientesId") Long clientesId, @PathParam("solicitudesId") Long solicitudesId, ServicioDTO servicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioResource createServicio: input: {0}", servicio);
        ServicioDTO nuevoServicioDTO = new ServicioDTO(servicioLogic.createServicio(clientesId, solicitudesId, servicio.toEntity()));
        LOGGER.log(Level.INFO, "ServicioResource createServicio: output: {0}", nuevoServicioDTO);
        return nuevoServicioDTO;
    }

    /**
     * Busca y devuelve todas las reseñas que existen en un solicitud.
     *
     * @param clientesId
     * @param solicitudesId El ID del solicitud del cual se buscan las reseñas
     * @return JSONArray {@link ServicioDTO} - Las reseñas encontradas en el
     * solicitud. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ServicioDTO> getServicios(@PathParam("clientesId") Long clientesId, @PathParam("solicitudesId") Long solicitudesId) {
        LOGGER.log(Level.INFO, "ServicioResource getServicios: input: {0}", solicitudesId);
        List<ServicioDTO> listaDTOs = listEntity2DTO(servicioLogic.getServicios(clientesId, solicitudesId));
        LOGGER.log(Level.INFO, "EditorialSolicitudesResource getSolicitudes: output: {0}", listaDTOs);
        return listaDTOs;
    }

    /**
     * Busca y devuelve la reseña con el ID recibido en la URL, relativa a un
     * solicitud.
     *
     * @param solicitudesId El ID del solicitud del cual se buscan las reseñas
     * @param serviciosId El ID de la reseña que se busca
     * @return {@link ServicioDTO} - La reseña encontradas en el solicitud.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el solicitud.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @GET
    @Path("{serviciosId: \\d+}")
    public ServicioDTO getServicio(@PathParam("solicitudesId") Long solicitudesId, @PathParam("serviciosId") Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioResource getServicio: input: {0}", serviciosId);
        ServicioEntity entity = servicioLogic.getServicio(solicitudesId, serviciosId);
        if (entity == null) {
            throw new WebApplicationException(soli + solicitudesId + serv + serviciosId + noExiste, 404);
        }
        ServicioDTO servicioDTO = new ServicioDTO(entity);
        LOGGER.log(Level.INFO, "ServicioResource getServicio: output: {0}", servicioDTO);
        return servicioDTO;
    }

    /**
     * Actualiza una reseña con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param clientesId
     * @param solicitudesId El ID del solicitud del cual se guarda la reseña
     * @param serviciosId El ID de la reseña que se va a actualizar
     * @param servicio {@link ServicioDTO} - La reseña que se desea guardar.
     * @return JSON {@link ServicioDTO} - La reseña actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @PUT
    @Path("{serviciosId: \\d+}")
    public ServicioDTO updateServicio(@PathParam("clientesId") Long clientesId, @PathParam("solicitudesId") Long solicitudesId, @PathParam("serviciosId") Long serviciosId, ServicioDTO servicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioResource updateServicio: input: solicitudesId: {0} , serviciosId: {1} , servicio:{2}", new Object[]{solicitudesId, serviciosId, servicio});
        if (serviciosId.equals(servicio.getId())) {
            throw new BusinessLogicException("Los ids del Servicio no coinciden.");
        }
        ServicioEntity entity = servicioLogic.getServicio(solicitudesId, serviciosId);
        if (entity == null) {
            throw new WebApplicationException(soli + solicitudesId + serv + serviciosId + noExiste, 404);

        }
        ServicioDTO servicioDTO = new ServicioDTO(servicioLogic.updateServicio(clientesId, solicitudesId, servicio.toEntity()));
        LOGGER.log(Level.INFO, "ServicioResource updateServicio: output:{0}", servicioDTO.toString());
        return servicioDTO;

    }

    /**
     * Borra la reseña con el id asociado recibido en la URL.
     *
     * @param solicitudesId El ID del solicitud del cual se va a eliminar la reseña.
     * @param serviciosId El ID de la reseña que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @DELETE
    @Path("{serviciosId: \\d+}")
    public void deleteServicio(@PathParam("solicitudesId") Long solicitudesId, @PathParam("serviciosId") Long serviciosId) throws BusinessLogicException {
        ServicioEntity entity = servicioLogic.getServicio(solicitudesId, serviciosId);
        if (entity == null) {
            throw new WebApplicationException(soli + solicitudesId + serv + serviciosId + noExiste, 404);
        }
        servicioLogic.deleteServicio(solicitudesId, serviciosId);
    }

    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrizeEntity a una lista de
     * objetos ServicioDTO (json)
     *
     * @param entityList corresponde a la lista de reseñas de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de reseñas en forma DTO (json)
     */
    private List<ServicioDTO> listEntity2DTO(List<ServicioEntity> entityList) {
        List<ServicioDTO> list = new ArrayList<ServicioDTO>();
        for (ServicioEntity entity : entityList) {
            list.add(new ServicioDTO(entity));
        }
        return list;
    }
    
    //---------------------------------------
    //-------------Carlos Robles-------------
    //---------------------------------------    
    @Path("{serviciosId: \\d+}/calificacion")
    public Class<CalificacionResource> getCalificacionResource(@PathParam("solicitudesId") Long solicitudesId, @PathParam("serviciosId") Long serviciosId) 
    {        
        if (servicioLogic.getServicio(solicitudesId, serviciosId) == null)
        {
            throw new WebApplicationException("/solicitudes/"+ solicitudesId +serv+ serviciosId +"/calificacion no existe.", 404);
        }
        return CalificacionResource.class;
    }
    
}
