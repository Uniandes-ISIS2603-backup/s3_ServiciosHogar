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
              LOGGER.log(Level.INFO, "ReferenciaResource crearReferencia: input: {0}", referencia.toString());
         ReferenciaEntity referenciaEntity = referencia.toEntity();
         ReferenciaEntity nuevaReferenciaEntity = referenciaLogic.createReferencia(referenciaEntity);
         /*Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo*/
         ReferenciaDTO nuevaReferenciaDTO = new ReferenciaDTO(nuevaReferenciaEntity);
         LOGGER.log(Level.INFO, "ReferenciaResource crearReferencia: output: {0}", nuevaReferenciaDTO.toString());
         return nuevaReferenciaDTO;
    }

    @GET
    public List<ReferenciaDTO> getReferencias()
    {
        LOGGER.info("ReferenciaResource getReferencias: input: void");
        List<ReferenciaDTO> listaReferencias = listEntity2DetailDTO(referenciaLogic.getReferencias());
        LOGGER.log(Level.INFO, "ReferenciaResource getReferencias: output: {0}", listaReferencias.toString());
        return listaReferencias;
    }
    

    @GET
    @Path("{referencias:\\d+}")
    public ReferenciaDTO getReferencia(@PathParam("idRemitente") Long idRemitente) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "ReferenciaResource getReferencia: input: {0}", idRemitente);
        ReferenciaEntity referenciaEntity = referenciaLogic.getReferencia(idRemitente);
        if (referenciaEntity == null)
            throw new WebApplicationException("El recurso /referencias/" + idRemitente + " no existe.", 404);
        
        ReferenciaDTO detailDTO = new ReferenciaDTO(referenciaEntity);
        LOGGER.log(Level.INFO, "ReferenciaResource getReferencia: {0}", detailDTO.toString());
        return detailDTO;
    }

    @PUT
    @Path("{referencias:\\d+}")
    public ReferenciaDTO modificarReferencia(@PathParam("idRemitente") Long idRemitente, ReferenciaDTO referencia) throws WebApplicationException
    {
       LOGGER.log(Level.INFO, "ReferenciaResource modificarReferencia: input: id:{0} , referencia: {1}", new Object[]{idRemitente, referencia.toString()});
        referencia.setIdRemitente(idRemitente);
        if (referenciaLogic.getReferencia(idRemitente) == null)
            throw new WebApplicationException("El recurso /referencias/" + idRemitente + " no existe.", 404);
        
        ReferenciaDTO detailDTO = new ReferenciaDTO(referenciaLogic.updateReferencia(idRemitente, referencia.toEntity()));
        LOGGER.log(Level.INFO, "ReferenciaResource modificarReferencia: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    

    @DELETE
    @Path("{referencias:\\d+}")
    public void deleteReferencia(@PathParam("idRemitente") Long idRemitente) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ReferenciaResource deleteReferencia: input: {0}", idRemitente);
        if (referenciaLogic.getReferencia(idRemitente) == null) 
            throw new WebApplicationException("El recurso /referencias/" + idRemitente + " no existe.", 404);
        
        referenciaLogic.deleteReferencia(idRemitente);
        LOGGER.info("ReferenciaResource deleteReferencia: output: void");
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
