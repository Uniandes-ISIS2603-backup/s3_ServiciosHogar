/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.PrestadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class PrestadorLogic {
    
    private static final Logger LOGGER = Logger.getLogger(PrestadorLogic.class.getName());
    
    /**
     * Inyección de persistencia
     */
    @Inject
    private PrestadorPersistence persistence;
    
    /**
     * Crea un prestador en la persistencia
     * @param prestadorEntity. Entidad que representa el prestador a persistir
     * @return La entidad luego de ser persistida
     * @throws BusinessLogicException . Si el prestador ya existe
     */
    public PrestadorEntity createPrestador(PrestadorEntity prestadorEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia el proceso de creación de un prestador");
        
        if(prestadorEntity.getCedula() == null)        
            throw new BusinessLogicException("La cédula es inválida");
        
        if(prestadorEntity.getNombre() == null)        
            throw new BusinessLogicException("El nombre es inválido");
        
        if(persistence.findByCedula(prestadorEntity.getCedula()) != null)
            throw new BusinessLogicException("La cédula ya existe");     
        
        if(persistence.findByCedula(prestadorEntity.getCedula()) != null)
            throw new BusinessLogicException("Ya existe un Prestador con cedula \"" + prestadorEntity.getCedula() + "\"");

        persistence.create(prestadorEntity);
        LOGGER.log(Level.INFO, "Termina el proceso de creación de un prestador");
        return prestadorEntity;
    } 
    
    /**
     * Retorna todos los prestadores
     * @return Una lista con todos los prestadores
     */
    public List<PrestadorEntity> getPrestadores()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los prestadores");
        List<PrestadorEntity> resultado = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los prestadores");
        return resultado;
    }
    
    /**
     * Obtener el prestador con el id dado por parámetro
     * @param prestadorId. El id del prestador que se desea buscar
     * @return El prestador solicitado
     */
    public PrestadorEntity getPrestador(Long prestadorId)
    {
        LOGGER.log(Level.INFO, "Inicia porceso de consultar el prestador con id={0}", prestadorId);
        PrestadorEntity prestador = persistence.find(prestadorId);
        if(prestador == null)
        {
            LOGGER.log(Level.SEVERE, "El prestador con id = {0} no existe", prestadorId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el prestador con id = {0}", prestadorId);
        return prestador;
    }
    
    /**
     * Modifica la información de un prestador con el id dado por parámetro
     * @param prestadorId. El id del prestador que se quiere actualizar
     * @param prestadorEntity. El prestador con las modificaciones que se desean realizar
     * @return El prestador con los cambios actualizados en la BD.
     * @throws BusinessLogicException 
     */
    public PrestadorEntity updatePrestador(Long prestadorId, PrestadorEntity prestadorEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO,"Inicia porceso de actualizar el prestador con id = {0}", prestadorId);
        //Reglas de negocio: Puede actualizar su nombre
        if(prestadorEntity.getCedula() == null)
            throw new BusinessLogicException("La cédula es inválida");
        PrestadorEntity resultado = persistence.update(prestadorEntity);
        LOGGER.log(Level.INFO, "Temirna porceso de actualizar el prestador con id = {0}", prestadorId);
        return resultado;        
    }
    
    /**
     * Borrar un prestador.
     * @param prestadorId. El id del prestador a eliminar. 
     */
    public void deletePrestador(Long prestadorId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminar el prestador con id = {0}", prestadorId);
        //Reglas de negocio: No se puede eliminar si tiene servicios asociados
        persistence.delete(prestadorId);
        LOGGER.log(Level.INFO, "Termina proceso de eliminar el prestador con id = {0}", prestadorId);
    }
    
}
