/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.HabilidadDTO;
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
@Path("habilidades")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped  
public class HabilidadResource {
    
    private static final Logger LOGGER = Logger.getLogger(PrestadorResource.class.getName());
    
    /**
     * 
     * @param habilidad
     * @return 
     */
    @POST
    public HabilidadDTO createPrestador(HabilidadDTO  habilidad)
    {
        return habilidad;
    }
    
    /**
     * 
     * @return 
     */
    @GET
    public List<HabilidadDTO> getPrestadores()
    {
        return null;
    }
    
    /**
     * 
     * @param habilidadId
     * @return 
     */
    @GET
    @Path("{habilidadesId: \\d+}")
    public HabilidadDTO getPrestador(@PathParam("habilidadesId") Long habilidadId)
    {
        return null;
    }
    
    /**
     * 
     * @param habilidadId
     * @param habilidad
     * @return 
     */
    @PUT
    @Path("{habilidadesId: \\d+}")
    public HabilidadDTO updatePrestador(@PathParam("habillidadesId") Long habilidadId, HabilidadDTO habilidad){
        
        return habilidad;
    }
    
    /**
     * 
     * @param habilidadId 
     */
    @DELETE
    @Path("{habilidadesId: \\d+}")
    public void deletePrestador(@PathParam("habilidadesId") Long habilidadId)
    {
        
    }
}
