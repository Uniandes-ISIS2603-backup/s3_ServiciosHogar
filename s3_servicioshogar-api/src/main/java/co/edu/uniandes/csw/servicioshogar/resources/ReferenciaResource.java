/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.ReferenciaDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.ReferenciaLogic;
import co.edu.uniandes.csw.servicioshogar.entities.ReferenciaEntity;
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

@Path("referencias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ReferenciaResource {
    private static final Logger LOGGER = Logger.getLogger(HojaDeVidaResource.class.getName());
  
    /**
     * Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    ReferenciaLogic referenciaLogic;
    
     @POST
    public ReferenciaDTO crearReferencia(ReferenciaDTO referencia) throws BusinessLogicException
    {
              LOGGER.log(Level.INFO, "ClienteResource crearCliente: input: {0}", referencia.toString());
         ReferenciaEntity referenciaEntity = referencia.toEntity();
         ReferenciaEntity nuevaReferenciaEntity = referenciaLogic.createReferencia(referenciaEntity);
         /*Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo*/
         ReferenciaDTO nuevaHojaDeVidaDTO = new ReferenciaDTO(nuevaReferenciaEntity);
         LOGGER.log(Level.INFO, "HojaDeVidaResource crearHojaDeVida: output: {0}", nuevaHojaDeVidaDTO.toString());
         return nuevaHojaDeVidaDTO;
    }

    @GET
    public List<ReferenciaDTO> getReferencias()
    {
        LOGGER.info("HojaDeVidaResource getHojasDeVida: input: void");
        List<ReferenciaDTO> listaHojasDeVida = listEntity2DetailDTO(referenciaLogic.getReferencias());
        LOGGER.log(Level.INFO, "HojaDeVidaResource getHojasDeVida: output: {0}", listaHojasDeVida.toString());
        return listaHojasDeVida;
    }
    

    @GET
    @Path("{referencias:\\d+}")
    public ReferenciaDTO getReferencia(@PathParam("idRemitente") Long idRemitente) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "HojaDeVidaResource getHojaDeVida: input: {0}", idRemitente);
        ReferenciaEntity clienteEntity = referenciaLogic.getReferencia(idRemitente);
        if (clienteEntity == null)
            throw new WebApplicationException("El recurso /clientes/" + idRemitente + " no existe.", 404);
        
        ReferenciaDTO detailDTO = new ReferenciaDTO(clienteEntity);
        LOGGER.log(Level.INFO, "HojaDeVidaResource getHojaDeVida: {0}", detailDTO.toString());
        return detailDTO;
    }

    @PUT
    @Path("{referencias:\\d+}")
    public ReferenciaDTO modificarReferencia(@PathParam("idRemitente") Long idRemitente, ReferenciaDTO referencia) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "HojaDeVidaResource modificarReferencia: input: id:{0} , cliente: {1}", new Object[]{idRemitente, referencia.toString()});
        referencia.setIdRemitente(idRemitente);
        if (ReferenciaLogic.getReferencia(idRemitente) == null)
            throw new WebApplicationException("El recurso /clientes/" + idRemitente + " no existe.", 404);
        
        ReferenciaDTO detailDTO = new ReferenciaDTO(ReferenciaLogic.updateReferencia(idRemitente, referencia.toEntity()));
        LOGGER.log(Level.INFO, "HojaDeVidaResource modificarHojaDeVida: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    

    @DELETE
    @Path("{referencias:\\d+}")
    public void deleteReferencia(@PathParam("idRemitente") Long idRemitente) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "HojaDeVidaResource deleteCliente: input: {0}", idRemitente);
        if (referenciaLogic.getReferencia(idRemitente) == null) 
            throw new WebApplicationException("El recurso /clientes/" + idRemitente + " no existe.", 404);
        
        referenciaLogic.deleteReferencia(idRemitente);
        LOGGER.info("HojaDeVidaResource deleteCliente: output: void");
    }
    
  
    private List<ReferenciaDTO> listEntity2DetailDTO(List<ReferenciaEntity> entityList) {
        List<ReferenciaDTO> list = new ArrayList<>();
        for (ReferenciaEntity entity : entityList) 
        {
            list.add(new ReferenciaDTO(entity));
        }
        return list;
    }
}
