/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.HabilidadDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.HabilidadLogic;
import co.edu.uniandes.csw.servicioshogar.entities.HabilidadEntity;
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
 * @author Maria Ocampo
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped  
public class HabilidadResource {
    
    private static final Logger LOGGER = Logger.getLogger(PrestadorResource.class.getName());
    
    /**
     * Constante que representa el path a prestador
     */
    private static final String PATH_PRESTADOR = "El recurso /prestadores/";
    
    /**
     * Constante que representa el path a habilidad
     */
    private static final String PATH_HABILIDAD = "/habilidades/";
    
    /**
    * Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    */
    @Inject
    private HabilidadLogic habilidadLogic;
    
    /**
     * Crea una nueva habiidad con la información que se recibe en el cuerpo de la petición y
     * agrega un objeto idéntico con un id autogenerado por la BD.
     * @param prestadorId. El id del prestador al que se le va a agregar la habilidad
     * @param habilidad {@link HabilidadDTO} - La habilidad que se desea guardar
     * @return JSON {@link HabilidadDTO} - La habilidad guardad con el id autogenerado
     * @throws BusinessLogicException {@link BusinessLogicException} - Error que ocurre cuando ya existe la habilidad 
     */
    @POST
    public HabilidadDTO createHabilidad(@PathParam("prestadorId") Long prestadorId, HabilidadDTO  habilidad) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "HabilidadResource createHabilidad: input: {0}", habilidad);
        HabilidadDTO nuevoHabilidadDTO = new HabilidadDTO(habilidadLogic.createHabilidad(prestadorId, habilidad.toEntity()));
        LOGGER.log(Level.INFO, "HabilidadResource createHabilidad: output: {0}", nuevoHabilidadDTO);
        return nuevoHabilidadDTO;
    }
    
    /**
     * Busca y retorna todas las habilidades existentes del prestador dado por parámetro
     * @param prestadorId. El id del prestador del cual se quiere consultar sus habilidades
     * @return JSONArray {@link HabilidadDTO} - Las habilidades del prestador, en caso de que no hayan retorna vacío
     */
    @GET
    public List<HabilidadDTO> getHabilidades(@PathParam("prestadorId") Long prestadorId)
    {
        LOGGER.log(Level.INFO, "HabilidadResource getHabilidades: input: {0}", prestadorId);
        List<HabilidadDTO> listaDTOs = listEntity2DTO(habilidadLogic.getHabilidades(prestadorId));
        LOGGER.log(Level.INFO, "HabilidadResource getHabilidades: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    /**
     * Bucsa la habilidad con el id dado por parámetro y la retorna
     * @param prestadorId. El id del prestador aosciado a la habilidad
     * @param habilidadId. El id de la habilidad buscada
     * @return JSON {@link HabilidadDTO} - La habilidad buscada.
     * @throws WebApplicationException  {@link WebApplicationExceptionMapper} - Error de logica generado
     * cuando no se encuentra la habilidad
     */
    @GET
    @Path("{habilidadesId: \\d+}")
    public HabilidadDTO getHabilidad(@PathParam("prestadorId") Long prestadorId, @PathParam("habilidadesId") Long habilidadId)
    {
        LOGGER.log(Level.INFO, "HabilidadResource getHabilidad: input: {0}", habilidadId);
        HabilidadEntity hEntity = habilidadLogic.getHabilidad(prestadorId, habilidadId);
        if(hEntity == null)
            throw new WebApplicationException(PATH_PRESTADOR+prestadorId+PATH_HABILIDAD+habilidadId+"no existe", 404);
        
        HabilidadDTO habilidadDTO = new HabilidadDTO(hEntity);
        LOGGER.log(Level.INFO, "HabilidadResource getHabilidad: output: {0}", habilidadDTO);
        return habilidadDTO;
    }
    
    /**
     * Actualiza/Modifica la habilidad con el id dado por parámetro
     * @param prestadorId. El id del prestador asociado a la habildiad
     * @param habilidadId. El id de la habilidad que se desea actualizar
     * @param habilidad. {@link HabilidadDTO} - La habilidad con las modificaciones que se desea realizar
     * @return JSON {@link HabilidadDTO} - La habilidad guardada con las modificaciones
     * @throws BusinessLogicException {@link BusinessLogicException} Error que ocurre cuando el id´s dados por parámetro no coinciden 
     */
    @PUT
    @Path("{habilidadesId: \\d+}")
    public HabilidadDTO updatHabilidad(@PathParam("prestadorId") Long prestadorId, @PathParam("habilidadesId") Long habilidadId, HabilidadDTO habilidad) throws BusinessLogicException{
        
        LOGGER.log(Level.INFO, "HabilidadResource updateHabilidad: input: prestadorId: {0} , habilidadId: {1} , habilidad:{2}", new Object[]{prestadorId, habilidadId, habilidad});
        if (habilidad.getId().equals(habilidadId)) {
            throw new BusinessLogicException("Los ids de la Habilidad no coinciden.");
        }
        HabilidadEntity entity = habilidadLogic.getHabilidad(prestadorId, habilidadId);
        if (entity == null) {
            throw new WebApplicationException(PATH_PRESTADOR + prestadorId + PATH_HABILIDAD + habilidadId + " no existe.", 404);

        }
        HabilidadDTO habilidadDTO = new HabilidadDTO(habilidadLogic.updateHabilidad(prestadorId, habilidad.toEntity()));
        LOGGER.log(Level.INFO, "HabilidadResource updateHabilidad: output:{0}", habilidadDTO);
        return habilidadDTO;

    }
    
    /**
     * Borra la habilidad asociada con el id dado por parámetro
     * @param prestadorId. El id del prestador asociado a la habilidad
     * @param habilidadId. El id de la habilidad que se desea eliminar
     * @throws BusinessLogicException {@link BusinessLogicException} Error que ocurre cuando la habilidad
     * con el id dado no existe.
     */
    @DELETE
    @Path("{habilidadesId: \\d+}")
    public void deleteHabilidad(@PathParam("prestadorId") Long prestadorId,@PathParam("habilidadesId") Long habilidadId) throws BusinessLogicException
    {
        HabilidadEntity entity = habilidadLogic.getHabilidad(prestadorId, habilidadId);
        if (entity == null) {
            throw new WebApplicationException(PATH_PRESTADOR + prestadorId + PATH_HABILIDAD + habilidadId + " no existe.", 404);
        }
        habilidadLogic.deleteHabilidad(prestadorId, habilidadId);
    }
    
     /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos HabilidadEntity a una lista de
     * objetos HabilidadDTO (json)
     *
     * @param entityList corresponde a la lista de habilidades de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de habilidades en forma DTO (json)
     */
    private List<HabilidadDTO> listEntity2DTO(List<HabilidadEntity> entityList) {
        List<HabilidadDTO> list = new ArrayList<>();
        for (HabilidadEntity entity : entityList) {
            list.add(new HabilidadDTO(entity));
        }
        return list;
    }
}
