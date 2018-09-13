/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.SolicitudPersistence;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Stateless
public class ServicioSolicitudLogic implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ServicioSolicitudLogic.class.getName());

    @Inject
    private ServicioPersistence seervicioPersistence;

    @Inject
    private SolicitudPersistence solicitudPersistence;

    /**
     * Remplazar la solicitud de un seervicio.
     *
     * @param seerviciosId id del libro que se quiere actualizar.
     * @param solicitudesId El id de la solicitud que se será del libro.
     * @return el nuevo libro.
     */
    public ServicioEntity replaceSolicitud(Long seerviciosId, Long solicitudesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar libro con id = {0}", seerviciosId);
        SolicitudEntity solicitudEntity = solicitudPersistence.find(solicitudesId);
        ServicioEntity seervicioEntity = seervicioPersistence.find(seerviciosId);
        seervicioEntity.setSolicitud(solicitudEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar libro con id = {0}", seervicioEntity.getId());
        return seervicioEntity;
    }

    /**
     * Borrar un seervicio de una solicitud. Este metodo se utiliza para borrar la
     * relacion de un libro.
     *
     * @param seerviciosId El libro que se desea borrar de la solicitud.
     */
    public void removeSolicitud(Long seerviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la Solicitud del libro con id = {0}", seerviciosId);
        ServicioEntity seervicioEntity = seervicioPersistence.find(seerviciosId);
        SolicitudEntity solicitudEntity = solicitudPersistence.find(seervicioEntity.getSolicitud().getId());
        seervicioEntity.setSolicitud(null);
        solicitudEntity.getServicios().remove(seervicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la Solicitud del libro con id = {0}", seervicioEntity.getId());
    }
}
