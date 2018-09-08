package co.edu.uniandes.csw.servicioshogar.test.persistence;


import co.edu.uniandes.csw.servicioshogar.entities.HabilidadEntity;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.persistence.HabilidadPersistence;
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
    
     private List<HabilidadEntity> data = new ArrayList<>();
     
     private List<PrestadorEntity> dataPrestadores = new ArrayList<>();
    
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
        em.createQuery("delete from HabilidadEntity").executeUpdate();
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

            dataPrestadores.add(entity);
        }
        for (int i = 0; i < 3; i++) {

            HabilidadEntity entity = factory.manufacturePojo(HabilidadEntity.class);
            if(i== 0)
            {
                entity.setPrestador(dataPrestadores.get(0));
            }
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
        
        assertEquals(newHabilidad.getDescripcion(), result.getDescripcion());
        assertEquals(newHabilidad.getPrestador(), result.getPrestador());
        assertEquals(newHabilidad.getTipo(), result.getTipo());
        
    }
    
    @Test
     public void getHabilidadTest() {
        HabilidadEntity entity = data.get(0);
        HabilidadEntity newEntity = habilidadPersistence.find(dataPrestadores.get(0).getId(), entity.getId());
        assertNotNull(newEntity);
        assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
        assertEquals(entity.getId(), newEntity.getId());
        assertEquals(entity.getPrestador(), newEntity.getPrestador());
    }
     
      @Test
    public void deleteHabilidadTest() {
        HabilidadEntity entity = data.get(0);
        habilidadPersistence.delete(entity.getId());
        HabilidadEntity deleted = em.find(HabilidadEntity.class, entity.getId());
        assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Review.
     */
    @Test
    public void updateReviewTest() {
        HabilidadEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
       HabilidadEntity newEntity = factory.manufacturePojo(HabilidadEntity.class);

        newEntity.setId(entity.getId());

       habilidadPersistence.update(newEntity);

        HabilidadEntity resp = em.find(HabilidadEntity.class, entity.getId());

        assertEquals(newEntity.getId(), resp.getId());
        assertEquals(newEntity.getPrestador(), resp.getPrestador());
        assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        assertEquals(newEntity.getTipo(), resp.getTipo());
    }
}
