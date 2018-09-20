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

public TarjetaCreditoEntity createTarjeta(TarjetaCreditoEntity tarjetaEntity) throws BusinessLogicException {
       LOGGER.log(Level.INFO, "Inicia proceso de creación de factura");
        /*Verifica la regla de negocio que dice no puede haber dos facturas con el mismo nombre*/
        persistence.create(tarjetaEntity);

        
        /*Invoca la persistencia para crear el factura*/
        LOGGER.log(Level.INFO, "Termina proceso de creación de la tarjeta");
        return tarjetaEntity;
    }

public boolean validacionNumero( Integer numero )
{
        boolean validar = false;
        if(numero == null)
        {
            return validar;
        }
    else
    {
        String numStr = numero.toString();
        System.out.println(numStr);
        validar = (numStr.length() == 16);
    }
        return validar;
}
    /**
     * Devuelve todos los facturas que hay en la base de datos.
     *
     * @return Lista de entidades de tipo factura.
     */
    public List<TarjetaCreditoEntity> getTarjetas() {
       LOGGER.log(Level.INFO,"Inicia proceso de consultar todos las tarjetas " );
        List<TarjetaCreditoEntity> tarjeta = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las tarjetas ");
        return tarjeta;
    }

    /**
     * Busca un factura por ID
     *
     * @param tarjetaId  El id del factura a buscar
     * @return El factura encontrado, null si no lo encuentra.
     */
    public TarjetaCreditoEntity getTarjeta(Long tarjetaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar factura con id = {0}", tarjetaId);
        TarjetaCreditoEntity tarjeta = persistence.find(tarjetaId);
        if( tarjeta == null)
        {LOGGER.log(Level.SEVERE, "La factura con el id = {0} no existe", tarjetaId);}
        return tarjeta;
    }

    /**
     * Actualizar un factura por ID
     *
     * @param tarjetaId El ID del factura a actualizar
     * @param tarjetaEntity  La entidad del factura con los cambios deseados
     * @return La entidad del factura luego de actualizarla
     */
    public TarjetaCreditoEntity updateTarjeta(Long tarjetaId, TarjetaCreditoEntity tarjetaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar factura con id = {0}", tarjetaId);
        TarjetaCreditoEntity tarjeta = persistence.update(tarjetaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar factura con id = {0}", tarjetaEntity.getId());
        return tarjeta;
    }

    /**
     * Eliminar un factura por ID
     *
     * @param tarjetaId El ID del factura a eliminar
     * @throws BusinessLogicException si el factura tiene un autor asociado.
     */
    public void deleteFactura(Long tarjetaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar factura con id = {0}", tarjetaId);
        persistence.delete(tarjetaId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar factura con id = {0}", tarjetaId);
    }
}
