/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.csw.servicioshogar.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class HabilidadPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(HabilidadPersistence.class.getName());
    
    @PersistenceContext (unitName = "SechUP")
    protected EntityManager em;
    
}
