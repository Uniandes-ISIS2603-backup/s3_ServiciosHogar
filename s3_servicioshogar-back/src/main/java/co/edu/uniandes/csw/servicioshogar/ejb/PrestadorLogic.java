/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.uniandes.csw.servicioshogar.persistence.PrestadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author María José Ocampo Vargas
 */
@Stateless
public class PrestadorLogic {
    
    private static final Logger LOGGER = Logger.getLogger(PrestadorLogic.class.getName());
    
    @Inject
    private PrestadorPersistence persistence; //Variable para acceder a la persistencia de la aplicación.
    
    /**
     * Crea una editorial en la persistencia
     * @param prestadorEntity La entidad que representa el prestador a persistir
     * @return La entidad del prestador luego de la persistencia
     * @throws BusinessLogicException Si el prestador a persistir ya existe.
     */
    public PrestadorEntity createPrestador(PrestadorEntity prestadorEntity) throws BusinessLogicException{
        
        LOGGER.log(Level.INFO, "Inicia proeso de creación del prestador");
        
        //¿Regla de negocio?
        if(persistence.findByCedula(prestadorEntity.getCedula()) != null)
        {
            throw new BusinessLogicException("Ya esxiste un prestador con la cédula \""+prestadorEntity.getCedula()+"\"");
        }
        //Invoca la persistencia para crear el prestador
        persistence.create(prestadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del prestador");
        return prestadorEntity;
    }
    
    /**
     * Obtener todos los prestadores existentes en la BD.
     * @return Una lista de prestadores
     */
    public List<PrestadorEntity> getPrestadores()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los prestadores");
        List<PrestadorEntity> prestadores = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los prestadores");
        return prestadores;
    }
    
    /**
     * Obtener un prestador por medio de su id.
     * @param prestadoresId Id del prestador para ser buscado
     * @return El prestador solicitado por su id
     */
    public PrestadorEntity getPrestador(Long prestadoresId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la editorial con id={0}", prestadoresId);
        
        PrestadorEntity prestadorEntity = persistence.find(prestadoresId);
        if(prestadorEntity == null)
        {
            LOGGER.log(Level.SEVERE,"El prestador con el id={0} no existe", prestadoresId);
        }
        LOGGER.log(Level.INFO, "Termina el proceso de consultar el prestador con id={0}", prestadoresId);
        return prestadorEntity;
    }
    
    /**
     * Actualizar un prestador
     * @param prestadorId Id del prestador para buscarlo en la BD
     * @param prestadorEntity Prestador con los cambios para ser actualizado.
     * @return El prestador con los cambios actualizados en la BD
     */
    public PrestadorEntity updatePrestador(Long prestadorId, PrestadorEntity prestadorEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el prestador con id={0}", prestadorId);
        
        PrestadorEntity newEntity = persistence.update(prestadorEntity);
        LOGGER.log(Level.INFO, "Termina con el proces de actualizar el prestador con id={0}", prestadorEntity);
        return newEntity;
    }
    
    public void deletePrestador(Long prestadorId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de eliminación del prestador con id={0}", prestadorId);
        PrestadorEntity prestadorEntity = persistence.find(prestadorId);
        if(prestadorEntity == null)
        {
            throw new BusinessLogicException("El prestador con el id \""+prestadorId+"\"");
        }
        persistence.delete(prestadorId);
        LOGGER.log(Level.INFO, "Termian el proceso de la elimanación del prestador con id={0}", prestadorId);
    }
}