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

import co.edu.uniandes.csw.servicioshogar.entities.HojaDeVidaEntity;
import co.edu.uniandes.csw.servicioshogar.persistence.HojaDeVidaPersistence;
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
 * Pruebas de persistencia Hoja de vida
 * @author Daniela Rocha Torres
 */
@RunWith(Arquillian.class)
public class HojaDeVidaPersistenceTest 
{
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------
    /*Inyectas las dependencias.*/
    @Inject
    private HojaDeVidaPersistence hojaDeVidaPersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<HojaDeVidaEntity> data = new ArrayList<>();
    
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
                .addPackage(HojaDeVidaEntity.class.getPackage())
                .addPackage(HojaDeVidaPersistence.class.getPackage())
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
    private void clearData() {em.createQuery("delete from HojaDeVidaEntity").executeUpdate();}
    
    /**
     * Inserta los datos iniciales para que las pruebas funcionen correctamente.
     */
    private void insertData() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) 
        {
            HojaDeVidaEntity entity = factory.manufacturePojo(HojaDeVidaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createHojaDeVidaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        HojaDeVidaEntity newEntity = factory.manufacturePojo(HojaDeVidaEntity.class);
        HojaDeVidaEntity result = hojaDeVidaPersistence.create(newEntity);

        Assert.assertNotNull(result);

        HojaDeVidaEntity entity = em.find(HojaDeVidaEntity.class, result.getId());

        Assert.assertEquals(entity.getEmail(), newEntity.getEmail());
        Assert.assertEquals(entity.getTelefono(), newEntity.getTelefono());
    }

    /**
     * Prueba para consultar la lista de Servicios.
     */
    @Test
    public void getHojasDeVidaTest() {
        List<HojaDeVidaEntity> list = hojaDeVidaPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (HojaDeVidaEntity ent : list) {
            boolean found = false;
            for (HojaDeVidaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Servicio.
     */
    @Test
    public void getHojaDeVidaTest() {
        HojaDeVidaEntity entity = data.get(0);
        HojaDeVidaEntity newEntity = hojaDeVidaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getEmail(), newEntity.getEmail());
        Assert.assertEquals(entity.getTelefono(), newEntity.getTelefono());
    }

    /**
     * Prueba para eliminar una Servicio.
     */
    @Test
    public void deleteServicioTest() {
        HojaDeVidaEntity entity = data.get(0);
        hojaDeVidaPersistence.delete(entity.getId());
        HojaDeVidaEntity deleted = em.find(HojaDeVidaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Servicio.
     */
    @Test
    public void updateServicioTest() {
        HojaDeVidaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        HojaDeVidaEntity newEntity = factory.manufacturePojo(HojaDeVidaEntity.class);

        newEntity.setId(entity.getId());

        hojaDeVidaPersistence.update(newEntity);

        HojaDeVidaEntity resp = em.find(HojaDeVidaEntity.class, entity.getId());

        Assert.assertEquals(entity.getEmail(), newEntity.getEmail());
        Assert.assertEquals(entity.getTelefono(), newEntity.getTelefono());
    }


}

