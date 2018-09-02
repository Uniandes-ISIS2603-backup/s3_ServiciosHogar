/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Servicio.
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@Stateless
public class ServicioLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioLogic.class.getName());

    @Inject
    private ServicioPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una servicio en la persistencia.
     *
     * @param servicioEntity La entidad que representa el servicio a
     * persistir.
     * @return La entiddad del servicio luego de persistirla.
     */
    public ServicioEntity createServicio(ServicioEntity servicioEntity){
        LOGGER.log(Level.INFO, "Inicia proceso de creación del servicio");
        // Invoca la persistencia para crear el servicio
        persistence.create(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del servicio");
        return servicioEntity;
    }

    /**
     * Obtener todas los servicios existentes en la base de datos.
     *
     * @return una lista de servicios.
     */
    public List<ServicioEntity> getServicios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los servicios");
        List<ServicioEntity> servicios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los servicios");
        return servicios;
    }

    /**
     * Obtener un servicio por medio de su id.
     *
     * @param serviciosId: id del servicio para ser buscado.
     * @return el servicio solicitado por medio de su id.
     */
    public ServicioEntity getServicio(Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el servicio con id = {0}", serviciosId);
        ServicioEntity servicioEntity = persistence.find(serviciosId);
        if (servicioEntity == null)
            LOGGER.log(Level.SEVERE, "El servicio con el id = {0} no existe", serviciosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el servicio con id = {0}", serviciosId);
        return servicioEntity;
    }

    /**
     * Actualizar un servicio.
     *
     * @param serviciosId: id del servicio para buscarlo en la base de
     * datos.
     * @param servicioEntity: servicio con los cambios para ser actualizado,
     * por ejemplo la calificacion.
     * @return el servicio con los cambios actualizados en la base de datos.
     */
    public ServicioEntity updateServicio(Long serviciosId, ServicioEntity servicioEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el servicio con id = {0}", serviciosId);
        ServicioEntity newEntity = persistence.update(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el servicio con id = {0}", servicioEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un servicio
     *
     * @param serviciosId: id del servicio a borrar
     */
    public void deleteServicio(Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el servicio con id = {0}", serviciosId);
        persistence.delete(serviciosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el servicio con id = {0}", serviciosId);
    }
}
