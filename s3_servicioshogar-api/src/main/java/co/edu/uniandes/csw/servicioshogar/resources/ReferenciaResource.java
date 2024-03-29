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
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ReferenciaResource {

    private static final Logger LOGGER = Logger.getLogger(HojaDeVidaResource.class.getName());

    /**
     * Constante que representa el path de referencias
     */
    private static final String PATH_REFERENCIA= "El recurso /referencias/";

    /**
     * Constante que represnta un mensaje de error
     */
    private static final String ERROR = " no existe";

    /**
     * Variable para acceder a la lógica de la aplicación. Es una inyección de
     * dependencias.
     */
    @Inject
    ReferenciaLogic referenciaLogic;

    /**
     *
     * @param hojaDeVidaId
     * @param referencia
     * @return
     * @throws BusinessLogicException
     */
    @POST
    public ReferenciaDTO crearReferencia(@PathParam("hojaDeVidaId") Long hojaDeVidaId, ReferenciaDTO referencia) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ReferenciaResource crearReferencia: input: {0}", referencia);
        ReferenciaEntity referenciaEntity = referencia.toEntity();
        ReferenciaEntity nuevaReferenciaEntity = referenciaLogic.createReferencia(hojaDeVidaId, referenciaEntity);
        /*Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo*/
        ReferenciaDTO nuevaReferenciaDTO = new ReferenciaDTO(nuevaReferenciaEntity);
        LOGGER.log(Level.INFO, "ReferenciaResource crearReferencia: output: {0}", nuevaReferenciaDTO);
        return nuevaReferenciaDTO;
    }

    /**
     *
     * @param hojaDeVidaId
     * @return
     */
    @GET
    public List<ReferenciaDTO> getReferencias(@PathParam("hojaDeVidaId") Long hojaDeVidaId) {
        LOGGER.info("ReferenciaResource getReferencias: input: void");
        List<ReferenciaDTO> listaReferencias = listEntity2DetailDTO(referenciaLogic.getReferencias(hojaDeVidaId));
        LOGGER.log(Level.INFO, "ReferenciaResource getReferencias: output: {0}", listaReferencias);
        return listaReferencias;
    }

    /**
     *
     * @param hojaDeVidaId
     * @param id
     * @return
     */
    @GET
    @Path("{referenciasId:\\d+}")
    public ReferenciaDTO getReferencia(@PathParam("hojaDeVidaId") Long hojaDeVidaId, @PathParam("referenciasId") Long id) {
        LOGGER.log(Level.INFO, "ReferenciaResource getReferencia: input: {0}", id);
        ReferenciaEntity referenciaEntity = referenciaLogic.getReferencia(hojaDeVidaId, id);
        if (referenciaEntity == null) {
            throw new WebApplicationException(PATH_REFERENCIA + id + ERROR, 404);
        }

        ReferenciaDTO detailDTO = new ReferenciaDTO(referenciaEntity);
        LOGGER.log(Level.INFO, "ReferenciaResource getReferencia: {0}", detailDTO);
        return detailDTO;
    }

    /**
     *
     * @param hojaDeVidaId
     * @param id
     * @param referencia
     * @return
     */
    @PUT
    @Path("{referenciasId:\\d+}")
    public ReferenciaDTO modificarReferencia(@PathParam("hojaDeVidaId") Long hojaDeVidaId, @PathParam("referenciasId") Long id, ReferenciaDTO referencia) {
        LOGGER.log(Level.INFO, "ReferenciaResource modificarReferencia: input: id:{0} , referencia: {1}", new Object[]{id, referencia});
        if (referenciaLogic.getReferencia(hojaDeVidaId, id) == null) {
            throw new WebApplicationException(PATH_REFERENCIA + id + ERROR, 404);
        }
        ReferenciaDTO detailDTO = new ReferenciaDTO(referenciaLogic.updateReferencia(id, referencia.toEntity()));
        LOGGER.log(Level.INFO, "ReferenciaResource modificarReferencia: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     *
     * @param hojaDeVidaId
     * @param id
     * @throws BusinessLogicException
     */
    @DELETE
    @Path("{referenciasId:\\d+}")
    public void deleteReferencia(@PathParam("hojaDeVidaId") Long hojaDeVidaId, @PathParam("referenciasId") Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ReferenciaResource deleteReferencia: input: {0}", id);
        if (referenciaLogic.getReferencia(hojaDeVidaId, id) == null) {
            throw new WebApplicationException(PATH_REFERENCIA + id + ERROR, 404);
        }

        referenciaLogic.deleteReferencia(id);
        LOGGER.info("ReferenciaResource deleteReferencia: output: void");
    }

    private List<ReferenciaDTO> listEntity2DetailDTO(List<ReferenciaEntity> entityList) {
        List<ReferenciaDTO> list = new ArrayList<>();
        for (ReferenciaEntity entity : entityList) {
            list.add(new ReferenciaDTO(entity));
        }
        return list;
    }
}
