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
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
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
  
 @POST
 public TarjetaCreditoDTO crearTarjetaCredito( TarjetaCreditoDTO tarjeta) 
 { return tarjeta;}
 
 @GET
 public List<TarjetaCreditoDTO> getTarjetasCredito()
 { return null;}
 
 @GET
 @Path("{tarjetaCreditoNum : \\d+}")
 public TarjetaCreditoDTO getTarjeta (@PathParam ("tarjetaCreditoNum") Integer tarjetaNum)
 {return null;}
 
 @PUT
 @Path("{tarjetaCreditoNum : \\d+}")
public TarjetaCreditoDTO updateTarjeta(@PathParam ("tarjetaCreditoNum") Integer tarjetaNum, TarjetaCreditoDTO tarjeta)
{return tarjeta;}
 
 @DELETE
 @Path("{tarjetaCreditoNum : \\d+}")
 public void deleteTarjeta(@PathParam ("tarjetaCreditoNum") Integer tarjetaNum)
 {}
 
}
