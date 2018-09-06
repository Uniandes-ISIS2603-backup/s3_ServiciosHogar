/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.FacturaDTO;
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
 * @author Adriana Trujillo
 */
@Path("facturas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FacturaResource {
    private static final Logger LOGGER = Logger.getLogger(FacturaResource.class.getName());
  
    @POST
 public FacturaDTO crearFactura( FacturaDTO factura) 
 { return factura;}
 
 @GET
 public List<FacturaDTO> getFacturas()
 { return null;}
 
 @GET
 @Path("{facturaNum : \\d+}")
 public FacturaDTO getFactura (@PathParam ("facturaNum") Integer facturaNum)
 {return null;}
 
 @PUT
 @Path("{facturaNum : \\d+}")
public FacturaDTO updateFactura(@PathParam ("facturaNum") Integer facturaNum, FacturaDTO factura)
{return factura;}
 
 @DELETE
 @Path("{facturaNum : \\d+}")
 public void deleteFactura(@PathParam ("facturaNum") Integer facturaNum)
 {}
}
