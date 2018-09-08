/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.csw.servicioshogar.test.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.uniandes.csw.servicioshogar.persistence.PrestadorPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.TestCase;
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
 * @author María Ocampo
 */
@RunWith(Arquillian.class)
public class PrestadorPersistenceTest extends TestCase{
    @PersistenceContext
    EntityManager em;
    
    @Inject
    PrestadorPersistence prestadorPersistence;
    
    @Inject
    UserTransaction utx;
    
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
            em.joinTransaction();
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
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            PrestadorEntity entity = factory.manufacturePojo(PrestadorEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test 
    public void createPrestadorTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        PrestadorEntity newEntity = factory.manufacturePojo(PrestadorEntity.class);
        PrestadorEntity result = prestadorPersistence.create(newEntity);

        assertNotNull(result);

        PrestadorEntity entity = em.find(PrestadorEntity.class, result.getId());

        assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    @Test
    public void getPrestadorTest()
    {
       PrestadorEntity prestador = data.get(0);
       PrestadorEntity newPrestador = prestadorPersistence.find(prestador.getId());
       
       assertNotNull(newPrestador);
       assertEquals(prestador.getNombre(), newPrestador.getNombre());
    }
    
    @Test
    public void getPrestadoresTest()
    {
        List<PrestadorEntity> list = prestadorPersistence.findAll();
        assertEquals(data.size(), list.size());
        for(PrestadorEntity pre: data)
        {
            boolean found = false;
            for(PrestadorEntity prestador: list)
            {
                if(pre.getId().equals(prestador.getId()))
                    found = true;
            }
            assertTrue(found);
        }        
    }
    
    @Test
    public void updatePrestadorTest()
    {
        PrestadorEntity prestador = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PrestadorEntity newEntity = factory.manufacturePojo(PrestadorEntity.class);
        
        newEntity.setId(prestador.getId());
        
        prestadorPersistence.update(newEntity);
        
        PrestadorEntity resp = em.find(PrestadorEntity.class, prestador.getId());
        
        assertEquals(newEntity.getCedula(), resp.getCedula());
    }
    
    @Test
    public void deletePrestadorTest()
    {
        PrestadorEntity prestador = data.get(0);
        prestadorPersistence.delete(prestador.getId());
        PrestadorEntity deleted = em.find(PrestadorEntity.class, prestador.getId());
        assertNull(deleted);
    }
    
    @Test
    public void findPrestadorByCedula()
    {
        PrestadorEntity entity = data.get(0);
        PrestadorEntity newEntity = prestadorPersistence.findByCedula(entity.getCedula());
        assertNotNull(newEntity);
        assertEquals(entity.getCedula(), newEntity.getCedula());
        
        newEntity = prestadorPersistence.findByCedula(null);
        assertNull(newEntity);
    }
    
}
