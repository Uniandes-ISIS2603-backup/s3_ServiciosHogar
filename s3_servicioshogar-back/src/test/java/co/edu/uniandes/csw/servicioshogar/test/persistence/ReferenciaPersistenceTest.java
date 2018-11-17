/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.persistence;

/**
 *
 * @author Daniela Rocha Torres
 */

import co.edu.uniandes.csw.servicioshogar.entities.ReferenciaEntity;
import co.edu.uniandes.csw.servicioshogar.persistence.ReferenciaPersistence;
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
 * Pruebas de persistencia Referencia
 * @author Daniela Rocha Torres
 */
@RunWith(Arquillian.class)
public class ReferenciaPersistenceTest {
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------
    /*Inyectas las dependencias.*/
    @Inject
    private ReferenciaPersistence referenciaPersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<ReferenciaEntity> data = new ArrayList<>();
    
    //------------------------------------------
    //------------------Metodos-----------------
    //------------------------------------------    
    /**
     * ???.
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ReferenciaEntity.class.getPackage())
                .addPackage(ReferenciaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuracion inicial de la prueba.
     */
    @Before
    public void configTest()
    {
        try 
        {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            try {utx.rollback();}             
            catch (Exception e1) {e1.printStackTrace();}
        }
    }
    
    /**
     * Limpia las tablas implicadas en la prueba.
     */
    private void clearData() {em.createQuery("delete from ReferenciaEntity").executeUpdate();}
    
    /**
     * Inserta los datos iniciales para que las pruebas funcionen correctamente.
     */
    private void insertData() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) 
        {
            ReferenciaEntity entity = factory.manufacturePojo(ReferenciaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createReferenciaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ReferenciaEntity newEntity = factory.manufacturePojo(ReferenciaEntity.class);
        ReferenciaEntity result = referenciaPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ReferenciaEntity entity = em.find(ReferenciaEntity.class, result.getId());

        Assert.assertEquals(entity.getIdRemitente(), newEntity.getIdRemitente()
        );
    }

    /**
     * Prueba para consultar la lista de Referencias.
     */
    @Test
    public void getReferenciasTest() {
        List<ReferenciaEntity> list = referenciaPersistence.findAll();
      
        Assert.assertEquals(data.size(), list.size());
      
        for (ReferenciaEntity ent : list) {
            boolean found = false;
            for (ReferenciaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Referencia.
     */
    @Test
    public void getReferenciaTest() {
        ReferenciaEntity entity = data.get(0);
        ReferenciaEntity newEntity = referenciaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getIdRemitente(), newEntity.getIdRemitente());
    }

    /**
     * Prueba para eliminar una referencia.
     */
    @Test
    public void deleteReferenciaTest() {
        ReferenciaEntity entity = data.get(0);
        referenciaPersistence.delete(entity.getId());
        ReferenciaEntity deleted = em.find(ReferenciaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una referencia.
     */
    @Test
    public void updateReferenciaTest() {
        ReferenciaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ReferenciaEntity newEntity = factory.manufacturePojo(ReferenciaEntity.class);

        newEntity.setId(entity.getId());
        referenciaPersistence.update(newEntity);

        ReferenciaEntity resp = em.find(ReferenciaEntity.class, entity.getId());
  
    Assert.assertEquals(resp.getEmpresa(), newEntity.getEmpresa());  
    Assert.assertEquals(resp.getNombreRemitente(), newEntity.getNombreRemitente());
    Assert.assertEquals(resp.getIdRemitente(), newEntity.getIdRemitente());  
    Assert.assertEquals(resp.getTelRemitente(), newEntity.getTelRemitente());  
    Assert.assertEquals(resp.getCargo(), newEntity.getCargo());  
    Assert.assertEquals(resp.getEmpresa(), newEntity.getEmpresa());  
    Assert.assertEquals(resp.getEmail(), newEntity.getEmail());  

    

    }  
}
