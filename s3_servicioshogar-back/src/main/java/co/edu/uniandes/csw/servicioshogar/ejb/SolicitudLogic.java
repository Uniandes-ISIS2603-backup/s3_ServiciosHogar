/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.ClientePersistence;
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
public class SolicitudLogic {

    private static final Logger LOGGER = Logger.getLogger(SolicitudLogic.class.getName());

    @Inject
    private SolicitudPersistence persistence;

    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Guardar un nuevo solicitud
     *
     * @param solicitudEntity La entidad de tipo solicitud del nuevo solicitud a
     * persistir.
     * @return La entidad luego de persistirla persistencia.
     */
    public SolicitudEntity createSolicitud(Long clientesId, SolicitudEntity solicitudEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear solicitud");
        ClienteEntity cliente = clientePersistence.find(clientesId);
        solicitudEntity.setCliente(cliente);
        LOGGER.log(Level.INFO, "Termina proceso de creación del solicitud");
        return persistence.create(solicitudEntity);
    }

    /**
     * Devuelve todos los solicitudes que hay en la base de datos.
     *
     * @return Lista de entidades de tipo solicitud.
     */
    public List<SolicitudEntity> getSolicitudes(Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los solicitudes asociados al cliente con id = {0}", clientesId);
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar los solicitudes asociados al cliente con id = {0}", clientesId);
        return clienteEntity.getSolicitudes();
    }

    /**
     * Busca un solicitud por ID
     *
     * @param clienteId
     * @param solicitudesId El id del solicitud a buscar
     * @return El solicitud encontrado, null si no lo encuentra.
     */
    public SolicitudEntity getSolicitud(Long clienteId, Long solicitudesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el solicitud con id = {0}", solicitudesId);
        SolicitudEntity solicitudEntity = persistence.find(clienteId, solicitudesId);
        if (solicitudEntity == null) {
            LOGGER.log(Level.SEVERE, "El solicitud con el id = {0} no existe", solicitudesId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el solicitud con id = {0}", solicitudesId);
        return solicitudEntity;
    }

    /**
     * Actualizar un solicitud por ID
     *
     * @param clientesId
     * @param solicitudEntity La entidad del solicitud con los cambios deseados
     * @return La entidad del solicitud luego de actualizarla
     */
    public SolicitudEntity updateSolicitud(Long clientesId, SolicitudEntity solicitudEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la solicitud con id = {1} del cliente con id ={0} ", new Long[]{clientesId, solicitudEntity.getId()});
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        solicitudEntity.setCliente(clienteEntity);
        persistence.update(solicitudEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la solicitud con id = {1} del cliente con id ={0}", new Long[]{clientesId, solicitudEntity.getId()});
        return solicitudEntity;
    }

    /**
     * Eliminar un solicitud por ID
     *
     * @param solicitudesId El ID del solicitud a eliminar
     */
    public void deleteSolicitud(Long clientesId, Long solicitudesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la solicitud con id = {1} del cliente con id ={0} ", new Long[]{clientesId, solicitudesId});
        SolicitudEntity old = getSolicitud(clientesId, solicitudesId);
        if (old == null) {
            throw new BusinessLogicException("El solicitud con id = " + solicitudesId + " no esta asociado a el libro con id = " + clientesId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la solicitud con id = {1} del cliente con id {0}= ", new Long[]{clientesId, solicitudesId});
    }

    public SolicitudEntity getSolicitudById(Long solicitudesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el solicitud con id = {0}", solicitudesId);
        SolicitudEntity solicitudEntity = persistence.findById(solicitudesId);
        if (solicitudEntity == null) {
            LOGGER.log(Level.SEVERE, "El solicitud con el id = {0} no existe", solicitudesId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el solicitud con id = {0}", solicitudesId);
        return solicitudEntity;
    }
}
