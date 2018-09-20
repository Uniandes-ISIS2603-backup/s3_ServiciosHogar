/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.HojaDeVidaDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.HojaDeVidaLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorHojaDeVidaLogic;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PrestadorHojaDeVidaResource implements Serializable{
    
    private static final Logger LOGGER = Logger.getLogger(PrestadorHojaDeVidaResource.class.getName());

    @Inject 
    private PrestadorHojaDeVidaLogic prestadorHojaDeVidaLogic;

    @Inject 
    private HojaDeVidaLogic hojaDeVidaLogic;

    @POST  
    @Path("{hojaDeVidaId: \\d+}")
    public HojaDeVidaDTO addHojaDeVida(@PathParam("prestadorId") Long prestadorId, @PathParam("hojaDeVidaId") Long hojaDeVidaId) {
        LOGGER.log(Level.INFO, "PrestadorHojaDeVidaResource addHojaDeVida: input: prestadorId {0} , hojaDeVidaId {1}", new Object[]{prestadorId, hojaDeVidaId});
        if (hojaDeVidaLogic.getHojaDeVida(hojaDeVidaId) == null) {
            throw new WebApplicationException("El recurso /hojaDeVida/" + hojaDeVidaId + " no existe.", 404);
        }
        HojaDeVidaDTO detailDTO = new HojaDeVidaDTO(prestadorHojaDeVidaLogic.addHojaDeVida(prestadorId, hojaDeVidaId));
        LOGGER.log(Level.INFO, "PrestadorHojaDeVidaResource addHojaDeVida: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    @GET 
    public HojaDeVidaDTO getHojaDeVida(@PathParam("prestadorId") Long prestadorId) {
        LOGGER.log(Level.INFO, "PrestadorHojaDeVidaResource getHojaDeVida: input: prestadorId {0}", prestadorId);
        if (prestadorHojaDeVidaLogic.getHojaDeVida(prestadorId) == null) {
            throw new WebApplicationException("El recurso /hojaDeVida no existe.", 404);
        }
        HojaDeVidaDTO detailDTO = new HojaDeVidaDTO(prestadorHojaDeVidaLogic.getHojaDeVida(prestadorId));
        LOGGER.log(Level.INFO, "PrestadorHojaDeVidaResource getHojaDeVida: output: {0}", detailDTO.toString());
        return detailDTO;
    }

  
    @PUT
    @Path("{hojaDeVidaId: \\d+}")
    public HojaDeVidaDTO replaceHojaDeVida(@PathParam("prestadorId") Long prestadorId, @PathParam("hojaDeVidaId") Long hojaDeVidaId) {
        LOGGER.log(Level.INFO, "PrestadorHojaDeVidaResource replaceHojaDeVida: input: prestadorId {0} , hojaDeVidaId{1}", new Object[]{prestadorId, hojaDeVidaId});
        if (hojaDeVidaLogic.getHojaDeVida(hojaDeVidaId) == null) {
            throw new WebApplicationException("El recurso /hojaDeVida/" + hojaDeVidaId + " no existe.", 404);
        }   
    HojaDeVidaDTO hojaDeVida = new HojaDeVidaDTO(prestadorHojaDeVidaLogic.replaceHojaDeVida(prestadorId, hojaDeVidaId).getHojaDeVida());
        LOGGER.log(Level.INFO, "PrestadorHojaDeVidaResource replaceHojaDeVida: output:{0}", hojaDeVida.toString());
     return hojaDeVida;
    }

    
    @DELETE
    public void removeHojaDeVida(@PathParam("prestadorId") Long prestadorId) {
        LOGGER.log(Level.INFO, "PrestadorHojaDeVidaResource removeHojaDeVida: input: prestadorId = {0}",prestadorId);
        if (prestadorHojaDeVidaLogic.getHojaDeVida(prestadorId) == null) {
            throw new WebApplicationException("El recurso /hojaDeVida no existe.", 404);
        }
        prestadorHojaDeVidaLogic.removeHojaDeVida(prestadorId);
        LOGGER.info("PrestadorHojaDeVidaResource removeHojaDeVida: output: void");
    }

}
