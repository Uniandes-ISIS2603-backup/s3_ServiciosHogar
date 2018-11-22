/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.PrestadorDTO;
import co.edu.uniandes.csw.servicioshogar.dtos.PrestadorDetailDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorLogic;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
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
@Path("prestadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PrestadorResource {
 
    private static final Logger LOGGER = Logger.getLogger(PrestadorResource.class.getName());
    
    /**
     * Constante que representa el path de prestadores
     */
    private static final String PATH_PRESTADOR = "El recurso /prestadores/";
    
    /**
     * Constante que represnta un mensaje de error
     */
    private static final String ERROR = " no existe";    
    
    /**
     * Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    private PrestadorLogic prestadorLogic;
    
    /**
     * Crea un nuevo prestador con la información que se recibe en el cuerpo de la petición
     * y se agrega un objeto idéntico con id autogenerado por la BD.
     * @param prestador {@link PrestadorDTO} - El prestador que desea guardar
     * @return JSON {@link PrestadorDTO} - El prestador guardado con el id autogenerado.
     * @throws BusinessLogicException  {@link BussinesLogicException} - Erro de lógica generado cuando el prestador ya existe
     */
    @POST
    public PrestadorDTO createPrestador(PrestadorDTO  prestador) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "PrestadorResource createPrestador : input {0}", prestador.toString());
        PrestadorDetailDTO nuevoPrestadorDTO = new PrestadorDetailDTO(prestadorLogic.createPrestador(prestador.toEntity()));
        LOGGER.log(Level.INFO, "PrestadorResource createPrestador : outpu {0}", nuevoPrestadorDTO.toString());
        return nuevoPrestadorDTO;
    }
    
    /**
     * Busca y retorna todos los prestadores existentes en la app
     * @return JSONArray {@link PrestadorDTO} - Los prestadores encontrados en el app.
     * Sino retorna vacío.
     */
    @GET
    public List<PrestadorDetailDTO> getPrestadores()
    {
        LOGGER.log(Level.INFO, "PrestadorResource getPrestadores : input: void");
        List<PrestadorDetailDTO> listaPrestadores = listEntity2DetailDTO(prestadorLogic.getPrestadores());
        LOGGER.log(Level.INFO, "PrestadorResource getPrestadores : outpu {0}", listaPrestadores.toString());
        return listaPrestadores;
    }
    
    /**
     * Busca el prestador con el id dado por parámetro y lo retorna 
     * @param prestadorId. El id del prestador que se está buscando
     * @return JSON {@link PrestadorDTO} - Prestador buscado
     * @throws WebApplicationException {@link WebApplicationException} Cuando no se encuentra al prestador
     */
    @GET
    @Path("{prestadoresId: \\d+}")
    public PrestadorDetailDTO getPrestador(@PathParam("prestadoresId") Long prestadorId)
    {
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador : input {0}", prestadorId);
        PrestadorEntity pEntity = prestadorLogic.getPrestador(prestadorId);
        if(pEntity == null)
            throw new WebApplicationException(PATH_PRESTADOR + prestadorId + ERROR, 404);
        PrestadorDetailDTO prestadorDetailDTO = new PrestadorDetailDTO(pEntity);
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador : outpu {0}", prestadorDetailDTO.toString());
        return prestadorDetailDTO;
    }
    
    /**
     * Actualiza/Modifca el prestador con el id dado por parámetro
     * @param prestadorId. El id del prestador que se desea actualizar
     * @param prestador {@link PrestadorDTO} - El prestador que se desea guardad
     * @return JSON {@link PrestadorDetailDTO} - El cliente guardado con la modificaciones
     * @throws BusinessLogicException {@link WebApplicationException} Cuando no se encentra el cliente a modificar.
     */
    @PUT
    @Path("{prestadoresId: \\d+}")
    public PrestadorDetailDTO updatePrestador(@PathParam("prestadoresId") Long prestadorId, PrestadorDTO prestador) throws BusinessLogicException{
        
        LOGGER.log(Level.INFO, "PrestadorResource updatePrestador : input id: {0} , book: {1}", new Object[]{prestadorId, prestador.toString()});
        prestador.setId(prestadorId);
        if(prestadorLogic.getPrestador(prestadorId) == null) 
            throw new WebApplicationException(PATH_PRESTADOR + prestadorId + ERROR, 404);
        
        PrestadorDetailDTO detailDTO = new PrestadorDetailDTO(prestadorLogic.updatePrestador(prestadorId, prestador.toEntity()));
        LOGGER.log(Level.INFO, "PrestadorResource updatePrestador : outpu {0}", detailDTO.toString());
        return detailDTO;
    }
    
    /**
     * Borra el cliente asociado con el id dado por parámetro
     * @param prestadorId. El identificador del prestador a borrar
     * @throws WebApplicationException {@link WebApplicationException} Cuando el prestador a eliminar no existe
     */
    @DELETE
    @Path("{prestadoresId: \\d+}")
    public void deletePrestador(@PathParam("prestadoresId") Long prestadorId)
    {
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador : input {0}", prestadorId);
        PrestadorEntity pEntity = prestadorLogic.getPrestador(prestadorId);
        if(pEntity == null)
            throw new WebApplicationException(PATH_PRESTADOR + prestadorId + ERROR, 404);
        prestadorLogic.deletePrestador(prestadorId);
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador : outpu : void");        
    }

    /**
     * Conexión con el servicio de habilidades de un prestador. {@link HabillidadResource}
     * Este método conecta la ruta /prestadores con la ruta /habilidades que dependen del prestador,
     * es una redirección al servicio que maneja el segmento de la URL que se encarga de las habilidades.
     * @param prestadorId. El id del prestador con respecto al cual se va a acceder al servicio
     * @return El servicio de Habilidades para ese prestador en particular.
     * @throws WebApplicationException {@link WebApplicationException}
     * Error de lógico que se genera cuando no se encuentra el prestador 
     */
    @Path("{prestadorId: \\d+}/habilidades")
    public Class<HabilidadResource> getHabilidadResource(@PathParam("prestadorId") Long prestadorId) {
        if (prestadorLogic.getPrestador(prestadorId) == null) {
            throw new WebApplicationException(PATH_PRESTADOR + prestadorId + "/habilidades no existe.", 404);
        }
        return HabilidadResource.class;
    }
    
    /**
     * Conexión con el servicio de hoja de vida de un prestador. {@link HojaDeVidaResource}
     * Este método conecta la ruta /prestadores con la ruta /hojaDeVida que dependen del prestador,
     * es una redirección al servicio que maneja el segmento de la URL que se encarga de la hoja de vida.
     * @param prestadorId. El id del prestador con respecto al cual se va a acceder al servicio
     * @return El servicio de Hoja de Vida para ese prestador en particular.
     * @throws WebApplicationException {@link WebApplicationException}
     * Error de lógico que se genera cuando no se encuentra el prestador 
     */
    @Path("{prestadorId: \\d+}/hojaDeVida")
    public Class<HojaDeVidaResource> getHojaDeVidaResource(@PathParam("prestadorId") Long prestadorId) {
        if (prestadorLogic.getPrestador(prestadorId) == null) {
            throw new WebApplicationException(PATH_PRESTADOR + prestadorId + ERROR, 404);
        }
        return HojaDeVidaResource.class;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrestadorEntity a una lista de
     * objetos PrestadorDetailDTO (json)
     *
     * @param entityList corresponde a la lista de libros de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de libros en forma DTO (json)
     */
    private List<PrestadorDetailDTO> listEntity2DetailDTO(List<PrestadorEntity> entityList) {
        List<PrestadorDetailDTO> list = new ArrayList<>();
        for (PrestadorEntity entity : entityList) {
            list.add(new PrestadorDetailDTO(entity));
        }
        return list;
    }
    
}
