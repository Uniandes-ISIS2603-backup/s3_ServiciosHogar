/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.ClientePersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.TarjetaCreditoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TarjetaCreditoLogic {

    private static final Logger LOGGER = Logger.getLogger(TarjetaCreditoLogic.class.getName());

    @Inject
    private TarjetaCreditoPersistence persistence;

    @Inject
    private ClientePersistence clientePersistence;

    public TarjetaCreditoEntity createTarjeta(TarjetaCreditoEntity tarjetaEntity, Long clienteId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de tarjeta");
        ClienteEntity cliente = clientePersistence.find(clienteId);
        if(clientePersistence.find(clienteId) == null)
            throw new BusinessLogicException("No existe un cliente con id: " + clienteId );
        else if(!validacionNumero(tarjetaEntity.getNumero()))
            throw new BusinessLogicException("El numero de la tarjeta debe ser de 16 digitos");
        
        tarjetaEntity.setCliente(cliente);
        LOGGER.log(Level.INFO, "Termina proceso de creación tarjeta");       
        return persistence.create(tarjetaEntity);
    }

    private boolean validacionNumero(String numero) 
    {
        if(numero.length() == 16)          
            return true;        
        else
            return false;
    }
    
    /**
     * Obtener todos las tarjetas existentes en la BD.
     * @return lista con todos las tarjetas.
     */
    public List<TarjetaCreditoEntity> getTarjetasCredito(Long clientesId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las tarjetas");
        List<TarjetaCreditoEntity> tarjetas = persistence.findAll(clientesId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los tarjetas");
        return tarjetas;
    }

    /**
     * Devuelve todos los facturas que hay en la base de datos.
     * @param clienteId
     * @param tarjetaId
     * @return Lista de entidades de tipo factura.
     */
    public TarjetaCreditoEntity getTarjeta(Long clienteId, Long tarjetaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las tarjetas ");

        return persistence.find(tarjetaId, clienteId);
    }

    /**
     * Actualizar un factura por ID
     *
     * @param clienteId El ID del factura a actualizar
     * @param tarjetaEntity La entidad del factura con los cambios deseados
     * @return La entidad del factura luego de actualizarla
     */
    public TarjetaCreditoEntity updateTarjeta(Long clienteId, TarjetaCreditoEntity tarjetaEntity) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la tarjeta con id = {0} del cliente = {1}", new Long[]{clienteId, tarjetaEntity.getId()});
        ClienteEntity clienetEntity = clientePersistence.find(clienteId);
        tarjetaEntity.setCliente(clienetEntity);
        persistence.update(tarjetaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la tarjeta con id = {0} del cliente = {1}", new Long[]{clienteId, tarjetaEntity.getId()});
        return tarjetaEntity;      
    }

    /**
     * Eliminar un factura por ID
     *
     * @param clienteId El ID del factura a eliminar
     * @param tarjetaId
     * @throws BusinessLogicException si el factura tiene un autor asociado.
     */
    public void deleteTarjeta(Long clienteId, Long tarjetaId) throws BusinessLogicException {
        Long[] ids = {tarjetaId, clienteId};
        LOGGER.log(Level.INFO, "Inicia proceso de borrar factura con id = {0} del cliente = {1}", ids);
        TarjetaCreditoEntity old = getTarjeta(clienteId, tarjetaId);
        if (old == null) {
            throw new BusinessLogicException("La tarjeta con id" + tarjetaId + "no esta asociada al cliente con id" + clienteId);
        }
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar factura con id = {0} del cliente = {1}", ids);
    }
}
