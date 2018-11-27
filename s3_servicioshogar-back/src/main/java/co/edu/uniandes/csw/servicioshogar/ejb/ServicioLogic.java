/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.SolicitudPersistence;
import java.util.ArrayList;
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
public class ServicioLogic {
    private static final Logger LOGGER = Logger.getLogger(ServicioLogic.class.getName());

    @Inject
    private ServicioPersistence persistence;

    @Inject
    private SolicitudPersistence solicitudPersistence;

    /**
     * Se encarga de crear un Servicio en la base de datos.
     * @param servicioEntity Objeto de ServicioEntity con los datos nuevos
     * @return Objeto de ServicioEntity con los datos nuevos y su ID.
     * @throws BusinessLogicException si solicitudesId no es el mismo que tiene el
     * entity.
     *
     */
    public ServicioEntity createServicio(ServicioEntity servicioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear servicio");
        if(servicioEntity.getSolicitud() == null)
            throw new BusinessLogicException("El servicio debe tener una solicitud asociada.");
        else if(solicitudPersistence.find(servicioEntity.getSolicitud().getCliente().getId(), servicioEntity.getSolicitud().getId()) == null)
            throw new BusinessLogicException("La solicitud que se quiere asociar al servicio no existe.");  
        LOGGER.log(Level.INFO, "Termina proceso de creación del servicio");
        return persistence.create(servicioEntity);
    }

    /**
     * Obtiene la lista de los registros de Servicio que pertenecen a un Solicitud.
     * @return Colección de objetos de ServicioEntity.
     */
    public List<ServicioEntity> getServicios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los servicios");
        List<ServicioEntity> listaServicios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los servicios");
        return listaServicios;
    }

    /**
     * Obtiene los datos de una instancia de Servicio a partir de su ID. La
     * existencia del elemento padre Solicitud se debe garantizar.
     * @param serviciosId Identificador de la Reseña a consultar
     * @return Instancia de ServicioEntity con los datos del Servicio consultado.
     *
     */
    public ServicioEntity getServicio(Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el servicio con id = {0} " ,serviciosId);
        return persistence.find(serviciosId);
    }

    /**
     * Actualiza la información de una instancia de Servicio.
     * @param servicioEntity Instancia de ServicioEntity con los nuevos datos.
     * @return Instancia de ServicioEntity con los datos actualizados.
     *
     */
    public ServicioEntity updateServicio(Long servicioId, ServicioEntity servicioEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el servicio con id = {0} " ,servicioEntity.getId());
        //TODO Mirar como manejar la relación con solicitud, véase create
        persistence.update(servicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el servicio con id = {0}" , servicioEntity.getId());
        return servicioEntity;
    }

    /**
     * Elimina una instancia de Servicio de la base de datos.
     *
     * @param serviciosId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException Si la reseña no esta asociada al solicitud.
     *
     */
    public void deleteServicio(Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el servicio con id = {0} ",  serviciosId);
        ServicioEntity old = getServicio(serviciosId);
        if (old == null) {
            throw new BusinessLogicException("El servicio con id = " + serviciosId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el servicio con id = {0}" ,serviciosId);
    }

}
