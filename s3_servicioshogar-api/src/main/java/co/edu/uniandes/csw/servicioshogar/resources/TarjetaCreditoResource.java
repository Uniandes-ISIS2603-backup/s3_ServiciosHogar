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
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
/**
 *
 * @author Adriana Trujillo
 */
@Path("tarjetasCredito")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class TarjetaCreditoResource {
    private static final Logger LOGGER = Logger.getLogger(TarjetaCreditoResource.class.getName());
 
 @Inject 
 private TarjetaCreditoLogic tarjetaLogic;
 
 @POST
 public TarjetaCreditoDTO crearTarjetaCredito( TarjetaCreditoDTO tarjeta, @PathParam("clientesId") Long clientesId) throws BusinessLogicException
 { 
     LOGGER.log(Level.INFO, "TarjetaCreditoResource: input : {0}", tarjeta.toString());
     TarjetaCreditoDTO tarjetaDTO = new TarjetaCreditoDTO(tarjetaLogic.createTarjeta(tarjeta.toEntity(), clientesId));
     LOGGER.log(Level.INFO, "TarjetaCreditoResource: output : {0}", tarjetaDTO.toString());
     return tarjetaDTO;
 }
  
 @GET
 @Path("{tarjetaCreditoId : \\d+}")
 public TarjetaCreditoDTO getTarjeta (@PathParam ("tarjetaCreditoId") Long tarjetaCreditoId, @PathParam("clienteId") Long clienteId)
 {
     System.out.println("Entro a get TarjetaCredito");
     LOGGER.log(Level.INFO, "TarjetaCreditoResource: input: {0}", tarjetaCreditoId);
     TarjetaCreditoEntity entity = tarjetaLogic.getTarjeta(tarjetaCreditoId, clienteId);
     
     if(entity == null)
     { throw new WebApplicationException("El recurso cliente " + clienteId + "tarjeta de credito " + tarjetaCreditoId + "no existe", 404);}
     
     TarjetaCreditoDTO tarjetaDTO = new TarjetaCreditoDTO(entity);
     LOGGER.log(Level.INFO, "TarjetaCreditoResource: output: {0}", tarjetaDTO.toString());
     return tarjetaDTO;     
 }
 
 @PUT
 @Path("{tarjetaCreditoId : \\d+}")
public TarjetaCreditoDTO updateTarjeta(@PathParam ("tarjetaCreditoId") Long tarjetaCreditoId, TarjetaCreditoDTO tarjeta, @PathParam ("clienteId") Long clienteId)
{
    LOGGER.log(Level.INFO, "TarjetaCreditoResource: input: {0}", tarjetaCreditoId);
    TarjetaCreditoEntity entity = tarjetaLogic.getTarjeta(tarjetaCreditoId, clienteId);
    if(entity == null)
    {throw new WebApplicationException("El recurso cliente " + clienteId + "tarjeta credito " + tarjetaCreditoId, 404);}
    
    TarjetaCreditoDTO tarjetaDTO = new TarjetaCreditoDTO(tarjetaLogic.updateTarjeta(clienteId, entity));
    LOGGER.log(Level.INFO, "TarjetaCreditoResource: output: {0}", tarjetaDTO.toString());
    return tarjetaDTO;
}
 
 @DELETE
 @Path("{tarjetaCreditoId : \\d+}")
 public void deleteTarjeta(@PathParam ("tarjetaCreditoNum") Long tarjetaCreditoId, @PathParam("clienteId") Long clienteId)throws BusinessLogicException
 {
     TarjetaCreditoEntity entity = tarjetaLogic.getTarjeta(tarjetaCreditoId, clienteId);
     if(entity == null)
     { throw new WebApplicationException("El recurso cliente " + clienteId + "tarjeta de credito " + tarjetaCreditoId + "no existe", 404);}
     
     tarjetaLogic.deleteTarjeta(clienteId, tarjetaCreditoId);
 }
 
}
