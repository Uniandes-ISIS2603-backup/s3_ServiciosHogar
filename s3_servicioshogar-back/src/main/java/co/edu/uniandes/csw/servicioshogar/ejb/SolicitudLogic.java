/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.uniandes.csw.servicioshogar.persistence.SolicitudPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Solicitud.
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Stateless
public class SolicitudLogic {
    
    private static final Logger LOGGER = Logger.getLogger(SolicitudLogic.class.getName());

    @Inject
    private SolicitudPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
    
    /**
     * Crea una solicitud en la persistencia.
     *
     * @param solicitudEntity  La entidad que representa la solicitud a
     * persistir.
     * @return La entiddad de la solicitud luego de persistirla.
     */
    public SolicitudEntity createSolicitud(SolicitudEntity solicitudEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la solicitud");
        /*Invoca la persistencia para crear el cliente*/
        persistence.create(solicitudEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del cliente");
        return solicitudEntity;    
    }
    
    /**
     * Obtener todas las solicitudes existentes en la base de datos.
     *
     * @return una lista de solicitudes.
     */
    public List<SolicitudEntity> getSolicitudes() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las editoriales");
        List<SolicitudEntity> solicitudes = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las editoriales");
        return solicitudes;
    }

    
    /**
     * Obtener una solicitud por medio de su id.
     *
     * @param solicitudesId: id de la solicitud para ser buscada.
     * @return la solicitud solicitada por medio de su id.
     */
    public SolicitudEntity getSolicitud(Long solicitudesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la solicitud con id = {0}", solicitudesId);
        SolicitudEntity solicitudEntity = persistence.find(solicitudesId);
        if (solicitudEntity == null)
            LOGGER.log(Level.SEVERE, "La solicitud con el id = {0} no existe", solicitudesId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la solicitud con id = {0}", solicitudesId);
        return solicitudEntity;
    }
    
    /**
     * Actualizar una solicitud.
     *
     * @param solicitudesId : id de la solicitud para buscarla en la base de
     * datos.
     * @param solicitudEntity: solicitud con los cambios para ser actualizada,
     * por ejemplo la fecha.
     * @return la solicitud con los cambios actualizados en la base de datos.
     */
    public SolicitudEntity updateSolicitud(Long solicitudesId, SolicitudEntity solicitudEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la solicitud con id = {0}", solicitudesId);
        SolicitudEntity newEntity = persistence.update(solicitudEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", solicitudEntity.getId());
        return newEntity;
    }

    /**
     * Borrar una solicitud.
     *
     * @param solicitudesId: id de la solicitud a borrar.
     */
    public void deleteSolicitud(Long solicitudesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la editorial con id = {0}", solicitudesId);
        persistence.delete(solicitudesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", solicitudesId);    }
    
}
