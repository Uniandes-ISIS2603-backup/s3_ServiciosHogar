/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.PrestadorDTO;
import co.edu.uniandes.csw.servicioshogar.dtos.PrestadorDetailDTO;
import co.edu.uniandes.csw.servicioshogar.dtos.ServicioDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.ServicioLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.ServiciosPrestadorLogic;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author María Ocampo
 */
@Path("clientes/{clientId: \\d+}/solicitudes/{solicitudId: \\d+}/servicios/{servicioId: \\d+}/prestador")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiciosPrestadorResource {
    
    private final static Logger LOGGER = Logger.getLogger(ServiciosPrestadorResource.class.getName());
    
    @Inject
    private ServicioLogic servicioLogic;
    
    @Inject
    private ServiciosPrestadorLogic serviciosPrestadorLogic;
    
    @Inject
    private PrestadorLogic prestadorLogic;
    
    @PUT
    public ServicioDTO replacePrestador(@PathParam("servicioId") Long servicioId, @PathParam("solicitudId") Long solicitudId, PrestadorDTO prestador)
    {
        LOGGER.log(Level.INFO, "ServiciosPrestadorResource replacePRestador: input: solicitudId{0}, servicioId{1}, Prestador{2}", new Object[]{solicitudId,servicioId,prestador});
        if(servicioLogic.getServicio(solicitudId, servicioId) == null)
            throw new WebApplicationException("El recurso /servicios/"+servicioId+" no existe",404);
        if(prestadorLogic.getPrestador(prestador.getId()) == null)
            throw new WebApplicationException("El recurso /prestador/"+prestador.getId()+" no existe", 404);
        ServicioDTO servicioDTO = new ServicioDTO(serviciosPrestadorLogic.replacePrestador(prestador.getId(), solicitudId, servicioId));
        LOGGER.log(Level.INFO, "ServiciosPrestadorResource replacePRestador: output: {0}", servicioDTO);
        return servicioDTO;
    }
    
    /**
     * Busca y devuelve todas las prestadores que existen en la aplicacion.
     *
     * @return JSONArray {@link PrestadorDetailDTO} - Las prestadores
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<PrestadorDetailDTO> getPrestadores() {
        LOGGER.info("PrestadorResource getPrestadores: input: void");
        List<PrestadorDetailDTO> listaPrestadores = listEntity2DetailDTO(prestadorLogic.getPrestadores());
        LOGGER.log(Level.INFO, "PrestadorResource getPrestadores: output: {0}", listaPrestadores.toString());
        return listaPrestadores;
    }

    /**
     * Busca la prestador con el id asociado recibido en la URL y la devuelve.
     *
     * @param prestadoresId Identificador de la prestador que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link PrestadorDetailDTO} - La prestador buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la prestador.
     */
    @GET
    @Path("{prestadoresId: \\d+}")
    public PrestadorDetailDTO getPrestador(@PathParam("prestadoresId") Long prestadoresId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador: input: {0}", prestadoresId);
        PrestadorEntity prestadorEntity = prestadorLogic.getPrestador(prestadoresId);
        if (prestadorEntity == null) {
            throw new WebApplicationException("El recurso /prestadores/" + prestadoresId + " no existe.", 404);
        }
        PrestadorDetailDTO detailDTO = new PrestadorDetailDTO(prestadorEntity);
        LOGGER.log(Level.INFO, "PrestadorResource getPrestador: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos BookEntity a una lista de
     * objetos BookDetailDTO (json)
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
