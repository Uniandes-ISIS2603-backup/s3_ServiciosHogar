/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import co.edu.uniandes.csw.servicioshogar.dtos.TarjetaCreditoDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.TarjetaCreditoLogic;
import co.edu.uniandes.csw.servicioshogar.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Adriana Trujillo
 * @author Carlos Robles / 3er Ciclo
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class TarjetaCreditoResource {

    private static final Logger LOGGER = Logger.getLogger(TarjetaCreditoResource.class.getName());
    
    private static final String EL_RECURSO = "El recurso /cliente/";
    private static final String NO_EXISTE = " no existe";
    private static final String PATH = "/tarjetasCredito/";    

    @Inject
    private TarjetaCreditoLogic tarjetaLogic;

    @POST
    public TarjetaCreditoDTO crearTarjetaCredito(TarjetaCreditoDTO tarjeta, @PathParam("clientesId") Long clientesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "TarjetaCreditoResource crearTarjetaCredito: input : {0}", tarjeta.toString());
        TarjetaCreditoDTO tarjetaDTO = new TarjetaCreditoDTO(tarjetaLogic.createTarjeta(tarjeta.toEntity(), clientesId));
        LOGGER.log(Level.INFO, "TarjetaCreditoResource crearTarjetaCredito: output : {0}", tarjetaDTO.toString());
        return tarjetaDTO;
    }

    @GET
    public List<TarjetaCreditoDTO> getTarjetasCredito(@PathParam("clientesId") Long clientesId) {
        LOGGER.info("TarjetaCreditoResource getTarjetasCredito: input: void");
        List<TarjetaCreditoDTO> listaTarjetas = listEntity2DTO(tarjetaLogic.getTarjetasCredito(clientesId));
        LOGGER.log(Level.INFO, "TarjetaCreditoResource getTarjetasCredito: output: {0}", listaTarjetas);
        return listaTarjetas;
    }

    @GET
    @Path("{tarjetaCreditoId : \\d+}")
    public TarjetaCreditoDTO getTarjeta(@PathParam("clientesId") Long clientesId, @PathParam("tarjetaCreditoId") Long tarjetaCreditoId ) 
    {
        LOGGER.log(Level.INFO, "TarjetaCreditoResource getTarjeta: input: {0}", tarjetaCreditoId);
        TarjetaCreditoEntity entity = tarjetaLogic.getTarjeta(clientesId, tarjetaCreditoId);

        if (entity == null) 
            throw new WebApplicationException(EL_RECURSO + clientesId + PATH + tarjetaCreditoId + NO_EXISTE, 404);
        
        TarjetaCreditoDTO tarjetaDTO = new TarjetaCreditoDTO(entity);
        LOGGER.log(Level.INFO, "TarjetaCreditoResource getTarjeta: output: {0}", tarjetaDTO.toString());
        return tarjetaDTO;
    }   

    @PUT
    @Path("{tarjetaCreditoId : \\d+}")
    public TarjetaCreditoDTO updateTarjeta(@PathParam("tarjetaCreditoId") Long tarjetaCreditoId, TarjetaCreditoDTO tarjeta, @PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "TarjetaCreditoResource updateTarjeta: input: {0}", tarjetaCreditoId);
        TarjetaCreditoEntity entity = tarjetaLogic.getTarjeta(clientesId, tarjetaCreditoId);
        if (entity == null) 
            throw new WebApplicationException(EL_RECURSO + clientesId + PATH + tarjetaCreditoId + NO_EXISTE, 404);
        
        TarjetaCreditoDTO tarjetaDTO = new TarjetaCreditoDTO(tarjetaLogic.updateTarjeta(clientesId, entity));
        LOGGER.log(Level.INFO, "TarjetaCreditoResource updateTarjeta: output: {0}", tarjetaDTO.toString());
        return tarjetaDTO;
    }

    @DELETE
    @Path("{tarjetaCreditoId : \\d+}")
    public void deleteTarjeta(@PathParam("clientesId") Long clientesId, @PathParam("tarjetaCreditoId") Long tarjetaCreditoId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "TarjetaCreditoResource deleteTarjeta: input: {0}", tarjetaCreditoId);
        TarjetaCreditoEntity entity = tarjetaLogic.getTarjeta(clientesId, tarjetaCreditoId);
        if (entity == null)
            throw new WebApplicationException(EL_RECURSO + clientesId + PATH + tarjetaCreditoId + NO_EXISTE, 404);

        tarjetaLogic.deleteTarjeta(clientesId, tarjetaCreditoId);
        LOGGER.info("TarjetaCreditoResource deleteTarjeta: output: void");      
    }
    
    private List<TarjetaCreditoDTO> listEntity2DTO(List<TarjetaCreditoEntity> entityList) {
        List<TarjetaCreditoDTO> list = new ArrayList<>();
        
        for (TarjetaCreditoEntity entity : entityList) 
            list.add(new TarjetaCreditoDTO(entity));
        
        return list;
    }

}
