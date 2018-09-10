/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.ejb;

import co.edu.uniandes.csw.servicioshogar.entities.ReferenciaEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.uniandes.csw.servicioshogar.persistence.ReferenciaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class ReferenciaLogic {
    
       private static final Logger LOGGER = Logger.getLogger(ReferenciaLogic.class.getName());
    
         /*Inyecta las dependencias*/
    @Inject
    private ReferenciaPersistence persistence;
    
}
