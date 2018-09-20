/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;

import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorLogic;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.PrestadorPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Maria Ocampo
 */
@RunWith(Arquillian.class)
public class PrestadorLogicTest {
    
    /**
     * 
     */
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * 
     */
    @Inject
    private PrestadorLogic prestadorLogic;
    
    /**
     * 
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * 
     */
    @Inject
    private UserTransaction utx;
    
    /**
     * 
     */
    private List<PrestadorEntity> data = new ArrayList<PrestadorEntity>();
    
   /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PrestadorEntity.class.getPackage())
                .addPackage(PrestadorLogic.class.getPackage())
                .addPackage(PrestadorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from PrestadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
    
        for (int i = 0; i < 3; i++) {
            PrestadorEntity entity = factory.manufacturePojo(PrestadorEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * 
     * @throws BusinessLogicException 
     */
    @Test
    public void createPrestadorTest() throws BusinessLogicException
    {
        PrestadorEntity newEntity = factory.manufacturePojo(PrestadorEntity.class);
        PrestadorEntity result = prestadorLogic.createPrestador(newEntity);
        Assert.assertNotNull(result);
        PrestadorEntity entity = em.find(PrestadorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCedula(), entity.getCedula());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    /**
     * 
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createPrestadorConCedulaInvalida() throws BusinessLogicException
    {
        PrestadorEntity newEntity = factory.manufacturePojo(PrestadorEntity.class);
        newEntity.setCedula(null);
        prestadorLogic.createPrestador(newEntity);
    }
    
    /**
     * 
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createPrestadorConNombrenvalido() throws BusinessLogicException
    {
        PrestadorEntity newEntity = factory.manufacturePojo(PrestadorEntity.class);
        newEntity.setNombre(null);
        prestadorLogic.createPrestador(newEntity);
    }
    
    /**
     * 
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createPrestadorConCedulaExistente() throws BusinessLogicException
    {
        PrestadorEntity newEntity = factory.manufacturePojo(PrestadorEntity.class);
        newEntity.setCedula(data.get(0).getCedula());
        prestadorLogic.createPrestador(newEntity);
    }
    
    /**
     * 
     */
    @Test
    public void getPrestadoresTest() {
        List<PrestadorEntity> list = prestadorLogic.getPrestadores();
        Assert.assertEquals(data.size(), list.size());
        for (PrestadorEntity entity : list) {
            boolean found = false;
            for (PrestadorEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * 
     * @throws BusinessLogicException 
     */
    @Test
    public void getPrestadorTest() throws BusinessLogicException
    {
        PrestadorEntity entity = data.get(0);
        PrestadorEntity resp = prestadorLogic.getPrestador(entity.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(entity.getId(), resp.getId());
        Assert.assertEquals(entity.getNombre(), resp.getNombre());
        Assert.assertEquals(entity.getCedula(), resp.getCedula());
        
    }
    
    /**
     * 
     * @throws BusinessLogicException 
     */
    @Test
    public void updatePrestadorTest() throws BusinessLogicException
    {
        PrestadorEntity entity = data.get(0);
        PrestadorEntity pojoEntity = factory.manufacturePojo(PrestadorEntity.class);
        pojoEntity.setId(entity.getId());
        prestadorLogic.updatePrestador(pojoEntity.getId(), pojoEntity);
        PrestadorEntity resp = em.find(PrestadorEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getCedula(), resp.getCedula());
        
    }
    
    /**
     * 
     * @throws BusinessLogicException 
     */
    @Test
    public void deletePrestadorTest() throws BusinessLogicException {
        PrestadorEntity entity = data.get(0);
        prestadorLogic.deletePrestador(entity.getId());
        PrestadorEntity deleted = em.find(PrestadorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
