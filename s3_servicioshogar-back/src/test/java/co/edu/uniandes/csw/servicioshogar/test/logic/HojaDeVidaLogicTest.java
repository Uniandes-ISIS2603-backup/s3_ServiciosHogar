/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;

import co.edu.uniandes.csw.servicioshogar.ejb.HojaDeVidaLogic;
import co.edu.uniandes.csw.servicioshogar.entities.HojaDeVidaEntity;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.HojaDeVidaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Daniela Rocha Torres
 */
@RunWith(Arquillian.class)
public class HojaDeVidaLogicTest{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private HojaDeVidaLogic hojaDeVidaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<HojaDeVidaEntity> data = new ArrayList<HojaDeVidaEntity>();
    
    private List<PrestadorEntity> dataPrestador = new ArrayList<>();
    

 //   private List<ReferenciaEntity> referenciaData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HojaDeVidaEntity.class.getPackage())
                .addPackage(HojaDeVidaLogic.class.getPackage())
                .addPackage(HojaDeVidaPersistence.class.getPackage())
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
        em.createQuery("delete from HojaDeVidaEntity").executeUpdate();
        em.createQuery("delete from PrestadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
         for (int i = 0; i < 1; i++) {
            PrestadorEntity entity = factory.manufacturePojo(PrestadorEntity.class);
            em.persist(entity);
            dataPrestador.add(entity);
        }
         
        for (int i = 0; i < 1; i++) {
            HojaDeVidaEntity entity = factory.manufacturePojo(HojaDeVidaEntity.class);
            entity.setPrestador(dataPrestador.get(0));
            em.persist(entity);
            data.add(entity);
          
        }
    }

    /**
     * Prueba para crear un Book
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void createHojaDeVidaTest() throws BusinessLogicException {
        HojaDeVidaEntity newEntity = factory.manufacturePojo(HojaDeVidaEntity.class);
        HojaDeVidaEntity result = hojaDeVidaLogic.createHojaDeVida(dataPrestador.get(0).getId(), newEntity);
        Assert.assertNotNull(result);
        HojaDeVidaEntity entity = em.find(HojaDeVidaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }


    /**
     * Prueba para consultar un Editorial.
     */
    @Test
    public void getHojaDeVidaTest() {
        HojaDeVidaEntity entity = data.get(0);
        HojaDeVidaEntity resultEntity = hojaDeVidaLogic.getHojaDeVida(dataPrestador.get(0).getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }

    /**
     * Prueba para actualizar un Editorial.
     */
    @Test
    public void updateHojaDeVidaTest() {
        HojaDeVidaEntity entity = data.get(0);
        HojaDeVidaEntity pojoEntity = factory.manufacturePojo(HojaDeVidaEntity.class);
        pojoEntity.setId(entity.getId());
        hojaDeVidaLogic.updateHojaDeVida(pojoEntity.getId(), pojoEntity);
        HojaDeVidaEntity resp = em.find(HojaDeVidaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());  
    }

    /**
     * Prueba para eliminar un Editorial.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteHojaDeVidaTest() throws BusinessLogicException {
        HojaDeVidaEntity entity = data.get(0);
        
        hojaDeVidaLogic.deleteHojaDeVida(dataPrestador.get(0).getId());
        HojaDeVidaEntity deleted = em.find(HojaDeVidaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }


}
