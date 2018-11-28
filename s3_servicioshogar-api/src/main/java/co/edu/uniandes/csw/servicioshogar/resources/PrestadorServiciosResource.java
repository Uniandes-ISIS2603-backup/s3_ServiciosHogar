/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.ServicioDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorServiciosLogic;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author estudiante
 */
@Path("prestadores/{prestadorId: \\d+}/servicios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PrestadorServiciosResource {
    
    private static final Logger LOGGER = Logger.getLogger(PrestadorServiciosResource.class.getName());
    private static final String NO_EXISTE= " no existe";
    private static final String PRESTA= " El recurso /prestadores/";
    @Inject
    private PrestadorServiciosLogic prestadorServiciosLogic;
    
    @Inject
    private PrestadorLogic prestadorLogic;
    
   @GET
   @Path("{servicioId: \\d+}")
   public ServicioDTO getServicio(@PathParam("prestadorId") Long prestadorId, @PathParam("servicioId") Long servicioId) throws BusinessLogicException
   {
       LOGGER.log(Level.INFO, " PrestadorServiciosResource getServicio: input: presatdorId {0}, servicio {1}",new Object[]{prestadorId,servicioId});
       if(prestadorLogic.getPrestador(prestadorId)==null)
           throw new WebApplicationException("El recurso /prestadores/"+prestadorId+NO_EXISTE, 404);
       ServicioEntity servicio = prestadorServiciosLogic.finServices(prestadorId, servicioId);
       if(servicio ==null)
           throw new WebApplicationException(PRESTA+prestadorId+"/servicios/"+servicioId+NO_EXISTE, 404);
       ServicioDTO sDTO = new ServicioDTO(servicio);
       LOGGER.log(Level.INFO, "PrestadorServiciosResource getServicio: output: servicio{0}", sDTO);
       return sDTO;
   }
   
   @GET
   public List<ServicioDTO> getServicios(@PathParam("prestadorId") Long prestadorId) throws BusinessLogicException
   {
       LOGGER.log(Level.INFO, " PrestadorServiciosResource getServicio: input: presatdorId {0}",prestadorId);
       if(prestadorLogic.getPrestador(prestadorId)==null)
           throw new WebApplicationException(PRESTA+prestadorId+NO_EXISTE, 404);
       List<ServicioEntity> servicio = prestadorServiciosLogic.findAllServices(prestadorId);
       if(servicio ==null)
           throw new WebApplicationException(PRESTA+prestadorId+"/servicios"+NO_EXISTE, 404);
       List<ServicioDTO> sDTO = listEntity2DetailDTO(servicio);
       LOGGER.log(Level.INFO, "PrestadorServiciosResource getServicio: output: servicio{0}", sDTO);
       return sDTO;
   }
   
    private List<ServicioDTO> listEntity2DetailDTO(List<ServicioEntity> entityList) {
        List<ServicioDTO> list = new ArrayList<>();
        for (ServicioEntity entity : entityList) {
            list.add(new ServicioDTO(entity));
        }
        return list;
    }
}
