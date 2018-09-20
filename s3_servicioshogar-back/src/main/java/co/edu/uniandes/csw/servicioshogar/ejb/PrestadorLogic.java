/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.PrestadorPersistence;
import java.util.ArrayList;
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
    
    @Inject
    private PrestadorPersistence persistence;
    
    
    public PrestadorEntity createPrestador(PrestadorEntity prestadorEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia el proceso de creación de un prestador");
        if(prestadorEntity.getCedula() == null || prestadorEntity.getCedula().equals(""))
        {
            throw new BusinessLogicException("La cédula es inválida");
        }
        if(prestadorEntity.getNombre() == null || prestadorEntity.getNombre().equals(""))
        {
            throw new BusinessLogicException("El nombre es inválido");
        }

        if(persistence.findByCedula(prestadorEntity.getCedula()) != null)
        {
            throw new BusinessLogicException("La cédula ya existe");
        }
        
        persistence.create(prestadorEntity);
        LOGGER.log(Level.INFO, "Termina el proceso de creación de un prestador");
        return prestadorEntity;
    } 
    
    public List<PrestadorEntity> getPrestadores()
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los prestadores");
        List<PrestadorEntity> resultado = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los prestadores");
        return resultado;
    }
    
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
    
    public PrestadorEntity updatePrestador(Long prestadorId, PrestadorEntity prestadorEntity) throws BusinessLogicException{
        LOGGER.log(Level.INFO,"Inicia porceso de actualizar el prestador con id = {0}", prestadorId);
        //Reglas de negocio: Puede actualizar su nombre
        if(prestadorEntity.getCedula() == null|| prestadorEntity.getCedula().equals(""))
            throw new BusinessLogicException("La cédula es inválida");
        PrestadorEntity resultado = persistence.update(prestadorEntity);
        LOGGER.log(Level.INFO, "Temirna porceso de actualizar el prestador con id = {0}", prestadorId);
        return resultado;        
    }
    
    public void deletePrestador(Long prestadorId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminar el prestador con id = {0}", prestadorId);
        //Reglas de negocio: No se puede eliminar si tiene servicios asociados
        persistence.delete(prestadorId);
        LOGGER.log(Level.INFO, "Termina proceso de eliminar el prestador con id = {0}", prestadorId);
    }
    
}