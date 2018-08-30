/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.PrestadorDTO;
import co.edu.uniandes.csw.servicioshogar.dtos.PrestadorDetailDTO;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author estudiante
 */
@Path("prestadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PrestadorResource {
 
    private static final Logger LOGGER = Logger.getLogger(PrestadorResource.class.getName());
    
    /**
     * 
     * @param prestador
     */
    @POST
    public PrestadorDTO createPrestador(PrestadorDTO  prestador)
    {
        return prestador;
    }
    
    /**
     * 
     * @return 
     */
    @GET
    public List<PrestadorDTO> getPrestadores()
    {
        return null;
    }
    
    @GET
    @Path("{prestadoresId: \\d+}")
    public PrestadorDTO getPrestador(@PathParam("prestadoresId") Long prestadorId)
    {
        return null;
    }
    
    /**
     * 
     * @param prestadoresId
     * @param prestador
     * @return 
     * /prestadores/id/habilidades/id
     */
    @PUT
    @Path("{prestadoresId: \\d+}")
    public PrestadorDTO updatePrestador(@PathParam("prestadoresId") Long prestadoresId, PrestadorDTO prestador){
        
        return prestador;
    }
    
    @DELETE
    @Path("{prestadoresId: \\d+}")
    public void deletePrestador(@PathParam("prestadoresId") Long prestadorId)
    {
        
    }
    
    
}
