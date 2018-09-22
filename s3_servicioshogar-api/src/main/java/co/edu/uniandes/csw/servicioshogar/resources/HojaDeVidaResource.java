/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.HojaDeVidaDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.HojaDeVidaLogic;
import co.edu.uniandes.csw.servicioshogar.entities.HojaDeVidaEntity;
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
 * @author Daniela Rocha Torres
 */

@Path("hojaDeVida")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class HojaDeVidaResource {
        private static final Logger LOGGER = Logger.getLogger(HojaDeVidaResource.class.getName());
  
    /**
     * Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    private HojaDeVidaLogic hojaDeVidaLogic;
    
     @POST
    public HojaDeVidaDTO crearHojaDeVida(HojaDeVidaDTO hojaDeVida) throws BusinessLogicException
    {
         LOGGER.log(Level.INFO, "HojaDeVidaResource crearHojaDeVida: input: {0}", hojaDeVida.toString());
         HojaDeVidaEntity hojaDeVidaEntity = hojaDeVida.toEntity();
         HojaDeVidaEntity nuevaHojaDeVidaEntity = hojaDeVidaLogic.createHojaDeVida(hojaDeVidaEntity);
         /*Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo*/
         HojaDeVidaDTO nuevaHojaDeVidaDTO = new HojaDeVidaDTO(nuevaHojaDeVidaEntity);
         LOGGER.log(Level.INFO, "HojaDeVidaResource crearHojaDeVida: output: {0}", nuevaHojaDeVidaDTO.toString());
         return nuevaHojaDeVidaDTO;
    }


    @GET
    public List<HojaDeVidaDTO> getHojasDeVida()
    {
        LOGGER.info("HojaDeVidaResource getHojasDeVida: input: void");
        List<HojaDeVidaDTO> listaHojasDeVida = listEntity2DetailDTO(hojaDeVidaLogic.getHojasDeVida());
        LOGGER.log(Level.INFO, "HojaDeVidaResource getHojasDeVida: output: {0}", listaHojasDeVida.toString());
        return listaHojasDeVida;
    }
    

    @GET
    @Path("{hojaDeVida:\\d+}")
    public HojaDeVidaDTO getHojaDeVida(@PathParam("id") Long id) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "HojaDeVidaResource getHojaDeVida: input: {0}", id);
        HojaDeVidaEntity hojaDeVidaEntity = hojaDeVidaLogic.getHojaDeVida(id);
        if (hojaDeVidaEntity == null)
            throw new WebApplicationException("El recurso /hojaDeVida/" + id + " no existe.", 404);
        
        HojaDeVidaDTO detailDTO = new HojaDeVidaDTO(hojaDeVidaEntity);
        LOGGER.log(Level.INFO, "HojaDeVidaResource getHojaDeVida: {0}", detailDTO.toString());
        return detailDTO;
    }

    @PUT
    @Path("{hojaDeVida:\\d+}")
    public HojaDeVidaDTO modificarHojaDeVida(@PathParam("id") Long id, HojaDeVidaDTO hojaDeVida) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "HojaDeVidaResource modificarHojaDeVida: input: id:{0} , hojaDeVida: {1}", new Object[]{id, hojaDeVida.toString()});
        hojaDeVida.setTelefono(id);
        if (hojaDeVidaLogic.getHojaDeVida(id) == null)
            throw new WebApplicationException("El recurso /hojaDeVida/" + id + " no existe.", 404);
        
        HojaDeVidaDTO detailDTO = new HojaDeVidaDTO(hojaDeVidaLogic.updateHojaDeVida(id, hojaDeVida.toEntity()));
        LOGGER.log(Level.INFO, "HojaDeVidaResource modificarHojaDeVida: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    

    @DELETE
    @Path("{hojaDeVida:\\d+}")
    public void deleteHojaDeVida(@PathParam("id") Long id) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "HojaDeVidaResource deleteHojaDeVida: input: {0}", id);
        if (hojaDeVidaLogic.getHojaDeVida(id) == null) 
            throw new WebApplicationException("El recurso /hojaDeVida/" + id + " no existe.", 404);
        
        hojaDeVidaLogic.deleteHojaDeVida(id);
        LOGGER.info("HojaDeVidaResource deleteHojaDeVida: output: void");
    }
    
  
    private List<HojaDeVidaDTO> listEntity2DetailDTO(List<HojaDeVidaEntity> entityList) {
        List<HojaDeVidaDTO> list = new ArrayList<>();
        for (HojaDeVidaEntity entity : entityList) 
        {
            list.add(new HojaDeVidaDTO(entity));
        }
        return list;
    }
}
