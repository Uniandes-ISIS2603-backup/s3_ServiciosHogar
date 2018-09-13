/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.ServicioDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.ServicioLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.SolicitudServiciosLogic;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SolicitudServiciosResource {
    private static final Logger LOGGER = Logger.getLogger(SolicitudServiciosResource.class.getName());

    @Inject
    private SolicitudServiciosLogic solicitudServiciosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ServicioLogic servicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un libro dentro de una solicitud con la informacion que recibe el
     * la URL. Se devuelve el libro que se guarda en la solicitud.
     *
     * @param solicitudesId Identificador de la solicitud que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param serviciosId Identificador del libro que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ServicioDTO} - El libro guardado en la solicitud.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{serviciosId: \\d+}")
    public ServicioDTO addServicio(@PathParam("solicitudesId") Long solicitudesId, @PathParam("serviciosId") Long serviciosId) {
        LOGGER.log(Level.INFO, "SolicitudServiciosResource addServicio: input: solicitudesID: {0} , serviciosId: {1}", new Object[]{solicitudesId, serviciosId});
        if (servicioLogic.getServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /servicios/" + serviciosId + " no existe.", 404);
        }
        ServicioDTO servicioDTO = new ServicioDTO(solicitudServiciosLogic.addServicio(serviciosId, solicitudesId));
        LOGGER.log(Level.INFO, "SolicitudServiciosResource addServicio: output: {0}", servicioDTO.toString());
        return servicioDTO;
    }

    /**
     * Busca y devuelve todos los libros que existen en la solicitud.
     *
     * @param solicitudesId Identificador de la solicitud que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link ServicioDTO} - Los libros encontrados en la
     * solicitud. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ServicioDTO> getServicios(@PathParam("solicitudesId") Long solicitudesId) {
        LOGGER.log(Level.INFO, "SolicitudServiciosResource getServicios: input: {0}", solicitudesId);
        List<ServicioDTO> listaDetailDTOs = serviciosListEntity2DTO(solicitudServiciosLogic.getServicios(solicitudesId));
        LOGGER.log(Level.INFO, "SolicitudServiciosResource getServicios: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la solicitud con id asociado.
     *
     * @param solicitudesId Identificador de la solicitud que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param serviciosId Identificador del libro que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ServicioDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro en la
     * solicitud.
     */
    @GET
    @Path("{serviciosId: \\d+}")
    public ServicioDTO getServicio(@PathParam("solicitudesId") Long solicitudesId, @PathParam("serviciosId") Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudServiciosResource getServicio: input: solicitudesID: {0} , serviciosId: {1}", new Object[]{solicitudesId, serviciosId});
        if (servicioLogic.getServicio(serviciosId) == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudesId + "/servicios/" + serviciosId + " no existe.", 404);
        }
        ServicioDTO servicioDetailDTO = new ServicioDTO(solicitudServiciosLogic.getServicio(solicitudesId, serviciosId));
        LOGGER.log(Level.INFO, "SolicitudServiciosResource getServicio: output: {0}", servicioDetailDTO.toString());
        return servicioDetailDTO;
    }

    /**
     * Remplaza las instancias de Servicio asociadas a una instancia de Solicitud
     *
     * @param solicitudesId Identificador de la solicitud que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param servicios JSONArray {@link ServicioDTO} El arreglo de libros nuevo para la
     * solicitud.
     * @return JSON {@link ServicioDTO} - El arreglo de libros guardado en la
     * solicitud.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @PUT
    public List<ServicioDTO> replaceServicios(@PathParam("solicitudesId") Long solicitudesId, List<ServicioDTO> servicios) {
        LOGGER.log(Level.INFO, "SolicitudServiciosResource replaceServicios: input: solicitudesId: {0} , servicios: {1}", new Object[]{solicitudesId, servicios.toString()});
        for (ServicioDTO servicio : servicios) {
            if (servicioLogic.getServicio(servicio.getId()) == null) {
                throw new WebApplicationException("El recurso /servicios/" + servicio.getId() + " no existe.", 404);
            }
        }
        List<ServicioDTO> listaDetailDTOs = serviciosListEntity2DTO(solicitudServiciosLogic.replaceServicios(solicitudesId, serviciosListDTO2Entity(servicios)));
        LOGGER.log(Level.INFO, "SolicitudServiciosResource replaceServicios: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de ServicioEntity a una lista de ServicioDTO.
     *
     * @param entityList Lista de ServicioEntity a convertir.
     * @return Lista de ServicioDTO convertida.
     */
    private List<ServicioDTO> serviciosListEntity2DTO(List<ServicioEntity> entityList) {
        List<ServicioDTO> list = new ArrayList();
        for (ServicioEntity entity : entityList) {
            list.add(new ServicioDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ServicioDTO a una lista de ServicioEntity.
     *
     * @param dtos Lista de ServicioDTO a convertir.
     * @return Lista de ServicioEntity convertida.
     */
    private List<ServicioEntity> serviciosListDTO2Entity(List<ServicioDTO> dtos) {
        List<ServicioEntity> list = new ArrayList<>();
        for (ServicioDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
