/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.ServicioDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorServiciosLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.SolicitudLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.SolicitudServiciosLogic;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
public class SolicitudServiciosResource {
    
    private static final Logger LOGGER = Logger.getLogger(SolicitudServiciosResource.class.getName());
    private static final String NO_EXISTE= " no existe";
    private static final String PRESTA= " El recurso /solicitudes/";
    @Inject
    private SolicitudServiciosLogic prestadorServiciosLogic;
    
    @Inject
    private SolicitudLogic prestadorLogic;
    
   @GET
   @Path("{servicioId: \\d+}")
   public ServicioDTO getServicio(@PathParam("solicitudId") Long solicitudId, @PathParam("servicioId") Long servicioId) throws BusinessLogicException
   {
       LOGGER.log(Level.INFO, " SolicitudServiciosResource getServicio: input: solicitudId {0}, servicio {1}",new Object[]{solicitudId,servicioId});
       if(prestadorLogic.getSolicitudById(solicitudId)==null)
           throw new WebApplicationException("El recurso /solicitud/"+solicitudId+NO_EXISTE, 404);
       ServicioEntity servicio = prestadorServiciosLogic.findServices(solicitudId, servicioId);
       if(servicio ==null)
           throw new WebApplicationException(PRESTA+solicitudId+"/servicios/"+servicioId+NO_EXISTE, 404);
       ServicioDTO sDTO = new ServicioDTO(servicio);
       LOGGER.log(Level.INFO, "PrestadorServiciosResource getServicio: output: servicio{0}", sDTO);
       return sDTO;
   }
   
   @GET
   public List<ServicioDTO> getServicios(@PathParam("solicitudId") Long solicitudId) throws BusinessLogicException
   {
       LOGGER.log(Level.INFO, " SolicitudServiciosResource getServicio: input: SolicitudId {0}",solicitudId);
       if(prestadorLogic.getSolicitudById(solicitudId)==null)
           throw new WebApplicationException(PRESTA+solicitudId+NO_EXISTE, 404);
       List<ServicioEntity> servicio = prestadorServiciosLogic.findAllServices(solicitudId);
       if(servicio ==null)
           throw new WebApplicationException(PRESTA+solicitudId+"/servicios"+NO_EXISTE, 404);
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
