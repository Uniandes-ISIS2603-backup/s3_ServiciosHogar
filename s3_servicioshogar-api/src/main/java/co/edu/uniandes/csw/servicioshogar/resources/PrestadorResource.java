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
import javax.ws.rs.*;


/**
 *
 * @author estudiante
 */
@Path("prestadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PrestadorResource {
    
   @Inject
   PrestadorLogic prestadorLogic;
   
   private static final Logger LOGGER = Logger.getLogger(PrestadorResource.class.getName());
   @POST
   @Path("{prestadoresId: \\d+}")
   public PrestadorDTO createPrestador(PrestadorDTO prestador) throws BusinessLogicException
   {
       //Convierte el DTO en un objeto Entity para ser manejado por la lógica.
       LOGGER.info("PrestadorResource createPrestador: input: "+ prestador.toString());
       
       PrestadorEntity prestadorEntity = prestador.toEntity();
       //Invoca la lógica para crear una editorial nueva
       PrestadorEntity nuevoPrestadorEntity= prestadorLogic.createPrestador(prestadorEntity);
       PrestadorDTO nuevoprestadorDTO = new PrestadorDTO(nuevoPrestadorEntity);
       LOGGER.log(Level.INFO, "PrestadorResource createPrestador: output: {0}", nuevoprestadorDTO.toString());
       return nuevoprestadorDTO;
   }
   
   /**
    * Busca y devuelve todos los prestadores que existen en la aplicación
    * @return  JSONArray - Los prestadores encontrados en la alplicación.
    */
   @GET
   public List<PrestadorDetailDTO> getPrestadores()
   {
       LOGGER.info("PrestadorResource getPrestadores: input: void");
       List<PrestadorDetailDTO> listaPrestadores = listEntity2DetailDTO(prestadorLogic.getPrestadores());
       LOGGER.log(Level.INFO, "PrestadorResource getPrestadores: output: {0}", this);
       
       return new ArrayList<PrestadorDetailDTO>();
   }
   
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrestadorlEntity a una lista de
     * objetos PrestadorlDetailDTO (json)
     *
     * @param entityList corresponde a la lista de prestadores de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de prestadores en forma DTO (json)
     */
    private List<PrestadorDetailDTO> listEntity2DetailDTO(List<PrestadorEntity> entityList) {
        List<PrestadorDetailDTO> list = new ArrayList<>();
        for (PrestadorEntity entity : entityList) {
            list.add(new PrestadorDetailDTO(entity));
        }
        return list;
    }
    
}