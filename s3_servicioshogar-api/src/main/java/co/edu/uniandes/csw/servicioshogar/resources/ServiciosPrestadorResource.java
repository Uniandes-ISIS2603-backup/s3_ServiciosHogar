/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.PrestadorDTO;
import co.edu.uniandes.csw.servicioshogar.dtos.ServicioDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.ServicioLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.ServiciosPrestadorLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
}
