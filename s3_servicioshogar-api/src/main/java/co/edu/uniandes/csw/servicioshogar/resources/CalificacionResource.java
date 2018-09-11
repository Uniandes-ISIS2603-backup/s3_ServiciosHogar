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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Carlos Eduardo Robles
 */
public class CalificacionResource 
{
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());

    @Inject
    private CalificacionLogic calificacionLogic;
    
    /**
     * Crea una nueva calificacion con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param clientesId El ID del cliente del cual se le agrega la calificacion
     * @param calificacion {@link CalificacionDTO} - La calificacion que se desea guardar.
     * @return JSON {@link CalificacionDTO} - La calificacion guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la calificacion.
     */
    @POST
    public CalificacionDTO createCalificacion(@PathParam("clientesId") Long clientesId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: input: {0}", calificacion.toString());
        CalificacionDTO nuevoCalificacionDTO = new CalificacionDTO(calificacionLogic.createCalificacion(clientesId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: output: {0}", nuevoCalificacionDTO.toString());
        return nuevoCalificacionDTO;
    }

    /**
     * Busca y devuelve todas las calificaciones que existen en un cliente.
     *
     * @param clientesId El ID del cliente del cual se buscan las calificaciones
     * @return JSONArray {@link CalificacionDTO} - Las calificaciones encontradas en el
     * cliente. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificaciones: input: {0}", clientesId);
        List<CalificacionDTO> listaDTOs = listEntity2DTO(calificacionLogic.getCalificaciones(clientesId));
        LOGGER.log(Level.INFO, "ClientesResource getClientes: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }

    /**
     * Busca y devuelve la calificacion con el ID recibido en la URL, relativa a un
     * cliente.
     *
     * @param clientesId El ID del cliente del cual se buscan las calificaciones
     * @param calificacionesId El ID de la calificacion que se busca
     * @return {@link CalificacionDTO} - La calificacion encontradas en el cliente.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("clientesId") Long clientesId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: input: {0}", calificacionesId);
        CalificacionEntity entity = calificacionLogic.getCalificacion(clientesId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/calificaciones/" + calificacionesId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(entity);
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", calificacionDTO.toString());
        return calificacionDTO;
    }

    /**
     * Actualiza una calificacion con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param clientesId El ID del cliente del cual se guarda la calificacion
     * @param calificacionesId El ID de la calificacion que se va a actualizar
     * @param calificacion {@link CalificacionDTO} - La calificacion que se desea guardar.
     * @return JSON {@link CalificacionDTO} - La calificacion actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la calificacion.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("clientesId") Long clientesId, @PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: clientesId: {0} , calificacionesId: {1} , calificacion:{2}", new Object[]{clientesId, calificacionesId, calificacion.toString()});
        if (calificacionesId.equals(calificacion.getId())) {
            throw new BusinessLogicException("Los ids del Calificacion no coinciden.");
        }
        CalificacionEntity entity = calificacionLogic.getCalificacion(clientesId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/calificaciones/" + calificacionesId + " no existe.", 404);

        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.updateCalificacion(clientesId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: output:{0}", calificacionDTO.toString());
        return calificacionDTO;

    }

    /**
     * Borra la calificacion con el id asociado recibido en la URL.
     *
     * @param clientesId El ID del cliente del cual se va a eliminar la calificacion.
     * @param calificacionesId El ID de la calificacion que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la calificacion.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void deleteCalificacion(@PathParam("clientesId") Long clientesId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        CalificacionEntity entity = calificacionLogic.getCalificacion(clientesId, calificacionesId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/calificaciones/" + calificacionesId + " no existe.", 404);
        }
        calificacionLogic.deleteCalificacion(clientesId, calificacionesId);
    }

    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrizeEntity a una lista de
     * objetos CalificacionDTO (json)
     *
     * @param entityList corresponde a la lista de calificaciones de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de calificaciones en forma DTO (json)
     */
    private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<CalificacionDTO>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
}
