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
 * Clase que implementa el recurso "servicios".
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Path("servicios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ServicioResource {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioResource.class.getName());

    @Inject
    ServicioLogic servicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva servicio con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param servicio {@link ServicioDTO} - La servicio que se desea
     * guardar.
     * @return JSON {@link ServicioDTO} - La servicio guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la servicio.
     */
    @POST
    public ServicioDTO createServicio(ServicioDTO servicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioResource createServicio: input: {0}", servicio.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ServicioEntity servicioEntity = servicio.toEntity();
        // Invoca la lógica para crear la servicio nueva
        ServicioEntity nuevoServicioEntity = servicioLogic.createServicio(servicioEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ServicioDTO nuevoServicioDTO = new ServicioDTO(nuevoServicioEntity);
        LOGGER.log(Level.INFO, "ServicioResource createServicio: output: {0}", nuevoServicioDTO.toString());
        return nuevoServicioDTO;
    }

    /**
     * Busca y devuelve todas las servicios que existen en la aplicacion.
     *
     * @return JSONArray {@link ServicioDTO} - Las servicios encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ServicioDTO> getServicios() {
        LOGGER.info("ServicioResource getServicios: input: void");
        List<ServicioDTO> listaServicios = listEntity2DetailDTO(servicioLogic.getServicios());
        LOGGER.log(Level.INFO, "ServicioResource getServicios: output: {0}", listaServicios.toString());
        return listaServicios;
    }

    /**
     * Busca la servicio con el id asociado recibido en la URL y la devuelve.
     *
     * @param serviciosId Identificador de la servicio que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ServicioDTO} - La servicio buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la servicio.
     */
    @GET
    @Path("{serviciosId: \\d+}")
    public ServicioDTO getServicio(@PathParam("serviciosId") Long serviciosId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ServicioResource getServicio: input: {0}", serviciosId);
        ServicioEntity servicioEntity = servicioLogic.getServicio(serviciosId);
        if (servicioEntity == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
        }
        ServicioDTO detailDTO = new ServicioDTO(servicioEntity);
        LOGGER.log(Level.INFO, "ServicioResource getServicio: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la servicio con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param serviciosId Identificador de la servicio que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param servicio {@link ServicioDTO} La servicio que se desea guardar.
     * @return JSON {@link ServicioDTO} - La servicio guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la servicio a
     * actualizar.
     */
    @PUT
    @Path("{serviciosId: \\d+}")
    public ServicioDTO updateServicio(@PathParam("serviciosId") Long serviciosId, ServicioDTO servicio) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ServicioResource updateServicio: input: id:{0} , servicio: {1}", new Object[]{serviciosId, servicio.toString()});
        servicio.setId(serviciosId);
        if (servicioLogic.getServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
        }
        ServicioDTO detailDTO = new ServicioDTO(servicioLogic.updateServicio(serviciosId, servicio.toEntity()));
        LOGGER.log(Level.INFO, "ServicioResource updateServicio: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra la servicio con el id asociado recibido en la URL.
     *
     * @param serviciosId Identificador de la servicio que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la servicio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la servicio.
     */
    @DELETE
    @Path("{serviciosId: \\d+}")
    public void deleteServicio(@PathParam("serviciosId") Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ServicioResource deleteServicio: input: {0}", serviciosId);
        if (servicioLogic.getServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
        }
        servicioLogic.deleteServicio(serviciosId);
        LOGGER.info("ServicioResource deleteServicio: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ServicioEntity a una lista de
     * objetos ServicioDTO (json)
     *
     * @param entityList corresponde a la lista de servicios de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de servicios en forma DTO (json)
     */
    private List<ServicioDTO> listEntity2DetailDTO(List<ServicioEntity> entityList) {
        List<ServicioDTO> list = new ArrayList<>();
        for (ServicioEntity entity : entityList) {
            list.add(new ServicioDTO(entity));
        }
        return list;
    }
}
