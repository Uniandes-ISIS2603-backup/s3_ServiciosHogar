/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.SolicitudPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Stateless
public class SolicitudServiciosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(SolicitudServiciosLogic.class.getName());

    @Inject
    private ServicioPersistence servicioPersistence;

    @Inject
    private SolicitudPersistence solicitudPersistence;

    /**
     * Agregar un servicio a la solicitud
     *
     * @param serviciosId El id servicio a guardar
     * @param solicitudesId El id de la solicitud en la cual se va a guardar el
     * servicio.
     * @return El servicio creado.
     */
    public ServicioEntity addServicio(Long serviciosId, Long solicitudesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un servicio a la solicitud con id = {0}", solicitudesId);
        SolicitudEntity solicitudEntity = solicitudPersistence.find(solicitudesId);
        ServicioEntity servicioEntity = servicioPersistence.find(serviciosId);
        servicioEntity.setSolicitud(solicitudEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un servicio a la solicitud con id = {0}", solicitudesId);
        return servicioEntity;
    }

    /**
     * Retorna todos los servicios asociados a una solicitud
     *
     * @param solicitudesId El ID de la solicitud buscada
     * @return La lista de servicios de la solicitud
     */
    public List<ServicioEntity> getServicios(Long solicitudesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los servicios asociados a la solicitud con id = {0}", solicitudesId);
        return solicitudPersistence.find(solicitudesId).getServicios();
    }

    /**
     * Retorna un servicio asociado a una solicitud
     *
     * @param solicitudesId El id de la solicitud a buscar.
     * @param serviciosId El id del servicio a buscar
     * @return El servicio encontrado dentro de la solicitud.
     * @throws BusinessLogicException Si el servicio no se encuentra en la
     * solicitud
     */
    public ServicioEntity getServicio(Long solicitudesId, Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el servicio con id = {0} de la solicitud con id = " + solicitudesId, serviciosId);
        List<ServicioEntity> servicios = solicitudPersistence.find(solicitudesId).getServicios();
        ServicioEntity servicioEntity = servicioPersistence.find(serviciosId);
        int index = servicios.indexOf(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el servicio con id = {0} de la solicitud con id = " + solicitudesId, serviciosId);
        if (index >= 0) {
            return servicios.get(index);
        }
        throw new BusinessLogicException("El servicio no está asociado a la solicitud");
    }

    /**
     * Remplazar servicios de una solicitud
     *
     * @param servicios Lista de servicios que serán los de la solicitud.
     * @param solicitudesId El id de la solicitud que se quiere actualizar.
     * @return La lista de servicios actualizada.
     */
    public List<ServicioEntity> replaceServicios(Long solicitudesId, List<ServicioEntity> servicios) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la solicitud con id = {0}", solicitudesId);
        SolicitudEntity solicitudEntity = solicitudPersistence.find(solicitudesId);
        List<ServicioEntity> servicioList = servicioPersistence.findAll();
        for (ServicioEntity servicio : servicioList) {
            if (servicios.contains(servicio)) {
                servicio.setSolicitud(solicitudEntity);
            } else if (servicio.getSolicitud() != null && servicio.getSolicitud().equals(solicitudEntity)) {
                servicio.setSolicitud(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la solicitud con id = {0}", solicitudesId);
        return servicios;
    }
}
