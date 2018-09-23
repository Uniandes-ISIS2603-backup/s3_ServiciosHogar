/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.FacturaDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.FacturaLogic;
import co.edu.uniandes.csw.servicioshogar.entities.FacturaEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
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
 * @author Adriana Trujillo
 */
@Path("facturas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FacturaResource {
    private static final Logger LOGGER = Logger.getLogger(FacturaResource.class.getName());
  
  @Inject 
 private FacturaLogic facturaLogic;
 
 @POST
 public FacturaDTO crearFactura( FacturaDTO factura, @PathParam("clientesId") Long clientesId) throws BusinessLogicException 
 { 
     LOGGER.log(Level.INFO, "FacturaResource: input : {0}", factura.toString());
     FacturaDTO facturaDTO = new FacturaDTO(facturaLogic.createFactura(factura.toEntity(), clientesId));
     LOGGER.log(Level.INFO, "FacturaResource: output : {0}", facturaDTO.toString());
     return facturaDTO;
 }
  
 @GET
 @Path("{facturaId : \\d+}")
 public FacturaDTO getTarjeta (@PathParam ("facturaId") Long facturaId)
 {
     LOGGER.log(Level.INFO, "FacturaResource: input: {0}", facturaId);
     FacturaEntity entity = facturaLogic.getFactura(facturaId);
     
     if(entity == null)
     { throw new WebApplicationException("El recurso factura" + facturaId + "no existe", 404);}
     
     FacturaDTO facturaDTO = new FacturaDTO(entity);
     LOGGER.log(Level.INFO, "FacturaResource: output: {0}", facturaDTO.toString());
     return facturaDTO;     
 }
 
 @PUT
 @Path("{facturaId : \\d+}")
public FacturaDTO updateTarjeta(@PathParam ("facturaId") Long facturaId, FacturaDTO factura)
{
    LOGGER.log(Level.INFO, "FacturaResource: input: {0}", facturaId);
    FacturaEntity entity = facturaLogic.getFactura(facturaId);
    if(entity == null)
    {throw new WebApplicationException("El recurso factura " + facturaId, 404);}
    
    FacturaDTO facturaDTO = new FacturaDTO(facturaLogic.updateFactura(facturaId, entity));
    LOGGER.log(Level.INFO, "FacturaResource: output: {0}", facturaDTO.toString());
    return facturaDTO;
}
 
 @DELETE
 @Path("{facturaId : \\d+}")
 public void deleteTarjeta(@PathParam ("facturaId") Long facturaId)throws BusinessLogicException
 {
     FacturaEntity entity = facturaLogic.getFactura(facturaId);
     if(entity == null)
     { throw new WebApplicationException("El recurso factura " + facturaId + "no existe", 404);}
     
     facturaLogic.deleteFactura(facturaId);
 }
}
