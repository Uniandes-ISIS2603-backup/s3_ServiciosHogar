/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.HojaDeVidaDTO;
import co.edu.uniandes.csw.servicioshogar.dtos.HojaDeVidaDetailDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.HojaDeVidaLogic;
import co.edu.uniandes.csw.servicioshogar.entities.HojaDeVidaEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
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
     * Constante que represnta la frase El recurso
     */
    private static final String RECURSO = "El recurso ";

    /**
     * Constante que representa el mensaje de error
     */
    private static final String ERROR = "/hojaDeVida no existe.";

    /**
     * Variable para acceder a la lógica de la aplicación. Es una inyección de
     * dependencias.
     */
    @Inject
    private HojaDeVidaLogic hojaDeVidaLogic;

    @POST
    public HojaDeVidaDTO crearHojaDeVida(@PathParam("prestadorId") Long prestadorId, HojaDeVidaDTO hojaDeVida) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "HojaDeVidaResource crearHojaDeVida: input: {0}", hojaDeVida);
        HojaDeVidaEntity hojaDeVidaEntity = hojaDeVida.toEntity();
        HojaDeVidaEntity nuevaHojaDeVidaEntity = hojaDeVidaLogic.createHojaDeVida(prestadorId, hojaDeVidaEntity);
        /*Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo*/
        HojaDeVidaDTO nuevaHojaDeVidaDTO = new HojaDeVidaDTO(nuevaHojaDeVidaEntity);
        LOGGER.log(Level.INFO, "HojaDeVidaResource crearHojaDeVida: output: {0}", nuevaHojaDeVidaDTO);
        return nuevaHojaDeVidaDTO;
    }

    /**
     *
     * @param prestadorId
     * @return
     */
    @GET
    public HojaDeVidaDetailDTO getHojaDeVida(@PathParam("prestadorId") Long prestadorId) { 
        LOGGER.log(Level.INFO, "HojaDeVidaResource getHojaDeVida: input: {0}", prestadorId);
        HojaDeVidaEntity hojaDeVidaEntity = hojaDeVidaLogic.getHojaDeVida(prestadorId);
        if (hojaDeVidaEntity == null) {
            throw new WebApplicationException(RECURSO + prestadorId + ERROR, 404);
        }
        HojaDeVidaDetailDTO detailDTO = new HojaDeVidaDetailDTO(hojaDeVidaEntity);
        LOGGER.log(Level.INFO, "HojaDeVidaResource getHojaDeVida: {0}", detailDTO);
        return detailDTO;
    }

    /**
     *
     * @param prestadorId
     * @param hojaDeVida
     * @return
     * @throws WebApplicationException
     */
    @PUT
    public HojaDeVidaDTO modificarHojaDeVida(@PathParam("prestadorId") Long prestadorId, HojaDeVidaDTO hojaDeVida) {
        LOGGER.log(Level.INFO, "HojaDeVidaResource modificarHojaDeVida: input: prestadorId:{0} , hojaDeVida: {1}", new Object[]{prestadorId, hojaDeVida});

        if (hojaDeVidaLogic.getHojaDeVida(prestadorId) == null) {
            throw new WebApplicationException(RECURSO + prestadorId + ERROR, 404);
        }

        HojaDeVidaDTO detailDTO = new HojaDeVidaDTO(hojaDeVidaLogic.updateHojaDeVida(prestadorId, hojaDeVida.toEntity()));
        LOGGER.log(Level.INFO, "HojaDeVidaResource modificarHojaDeVida: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     *
     * @param prestadorId
     * @throws BusinessLogicException
     */
    @DELETE
    public void deleteHojaDeVida(@PathParam("prestadorId") Long prestadorId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "HojaDeVidaResource deleteHojaDeVida: input: {0}", prestadorId);
        if (hojaDeVidaLogic.getHojaDeVida(prestadorId) == null) {
            throw new WebApplicationException(RECURSO + prestadorId + ERROR, 404);
        }

        hojaDeVidaLogic.deleteHojaDeVida(prestadorId);
        LOGGER.info("HojaDeVidaResource deleteHojaDeVida: output: void");
    }

    /**
     * Conexión con el servicio de habilidades de un prestador.
     * {@link HabillidadResource} Este método conecta la ruta /prestadores con
     * la ruta /habilidades que dependen del prestador, es una redirección al
     * servicio que maneja el segmento de la URL que se encarga de las
     * habilidades.
     *
     * @param hojaDeVidaId
     * @return El servicio de Referencias para esa hojaDeVida en particular.
     * @throws WebApplicationException {@link WebApplicationException} Error de
     * lógico que se genera cuando no se encuentra el prestador
     */
    @Path("{hojaDeVidaId: \\d+}/referencias")
    public Class<ReferenciaResource> getReferenciasResource(@PathParam("hojaDeVidaId") Long hojaDeVidaId) {
        if (hojaDeVidaLogic.getHojaDeVidaById(hojaDeVidaId) == null) {
            throw new WebApplicationException(RECURSO + "/hojaDeVida/"+ hojaDeVidaId + ERROR, 404);
        }
        return ReferenciaResource.class;
    }

}
