package co.uniandes.csw.servicioshogar.test.persistence;


import co.edu.uniandes.csw.servicioshogar.entities.HabilidadEntity;
import co.uniandes.csw.servicioshogar.persistence.HabilidadPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.TestCase;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author María Ocampo
 */
@RunWith(Arquillian.class)
public class HabilidadPersistenceTest extends TestCase{
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    HabilidadPersistence habilidadPersistence;
    
    @Inject
    UserTransaction utx;
    
     private List<HabilidadEntity> data = new ArrayList<HabilidadEntity>();
    
      /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HabilidadEntity.class.getPackage())
                .addPackage(HabilidadPersistence.class.getPackage())
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
        em.createQuery("delete from EditorialEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            HabilidadEntity entity = factory.manufacturePojo(HabilidadEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
        
    @Test
    public void createHabilidadTest(){
        PodamFactory factory = new PodamFactoryImpl();
        HabilidadEntity newHabilidad = factory.manufacturePojo(HabilidadEntity.class);
        HabilidadEntity result = habilidadPersistence.create(newHabilidad);
        
        assertNotNull(result);    
        
        
        
    }
}
