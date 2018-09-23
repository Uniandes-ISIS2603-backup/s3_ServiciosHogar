/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;
import co.edu.uniandes.csw.servicioshogar.entities.FacturaEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.ClientePersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.FacturaPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.SolicitudPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Adriana Trujillo
 */
@Stateless
public class FacturaLogic {
    private static final Logger LOGGER = Logger.getLogger(FacturaLogic.class.getName());

@Inject
private FacturaPersistence facturaPersistence;

@Inject
private SolicitudPersistence solicitudPeristence;

@Inject
private ClientePersistence clientePersistence;

public FacturaEntity createFactura(FacturaEntity facturaEntity, Long clienteId) throws BusinessLogicException {
       LOGGER.log(Level.INFO, "Inicia proceso de creación de factura");
       SolicitudEntity solicitud = solicitudPeristence.find(clienteId, facturaEntity.getSolicitud().getId());
        /*Invoca la persistencia para crear el factura*/
        facturaEntity.setSolicitud(solicitud);
        solicitud.setFactura(facturaEntity);
        facturaEntity = facturaPersistence.create(facturaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del factura");
        return facturaEntity;
    }

    /**
     * Devuelve todos los facturas que hay en la base de datos.
     *
     * @return Lista de entidades de tipo factura.
     */
    public List<FacturaEntity> getFacturas() {
        LOGGER.info("Inicia proceso de consultar todos los facturas");
        List<FacturaEntity> factura = facturaPersistence.findAll();
        LOGGER.info("Termina proceso de consultar todos los facturas");
        return factura;
    }

    /**
     * Busca un factura por ID
     *
     * @param facturaId El id del factura a buscar
     * @return El factura encontrado, null si no lo encuentra.
     */
    public FacturaEntity getFactura(Long facturaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar factura con id = {0}", facturaId);
        FacturaEntity factura = facturaPersistence.find(facturaId);
        if (factura == null) {
            LOGGER.log(Level.SEVERE, "La factura con el id = {0} no existe", facturaId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar factura con id = {0}", facturaId);
        return factura;
    }

    /**
     * Actualizar un factura por ID
     *
     * @param facturaId El ID del factura a actualizar
     * @param facturaEntity La entidad del factura con los cambios deseados
     * @return La entidad del factura luego de actualizarla
     */
    public FacturaEntity updateFactura(Long facturaId, FacturaEntity facturaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar factura con id = {0}", facturaId);
        FacturaEntity newEntity = facturaPersistence.update(facturaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar factura con id = {0}", facturaEntity.getId());
        return newEntity;
    }

    /**
     * Eliminar un factura por ID
     *
     * @param facturaId El ID del factura a eliminar
     * @throws BusinessLogicException si el factura tiene un autor asociado.
     */
    public void deleteFactura(Long facturaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar factura con id = {0}", facturaId);
        facturaPersistence.delete(facturaId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar factura con id = {0}", facturaId);
    }
}
