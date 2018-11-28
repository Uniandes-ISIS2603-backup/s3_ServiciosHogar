/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;

import co.edu.uniandes.csw.servicioshogar.ejb.ReferenciaLogic;
import co.edu.uniandes.csw.servicioshogar.entities.HojaDeVidaEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ReferenciaEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.ReferenciaPersistence;
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
public class ReferenciaLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ReferenciaLogic referenciaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ReferenciaEntity> data = new ArrayList<ReferenciaEntity>();
   
    private HojaDeVidaEntity hojaDeVida = new HojaDeVidaEntity();
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ReferenciaEntity.class.getPackage())
                .addPackage(ReferenciaLogic.class.getPackage())
                .addPackage(ReferenciaPersistence.class.getPackage())
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
        em.createQuery("delete from ReferenciaEntity").executeUpdate();
        em.createQuery("delete from HojaDeVidaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        hojaDeVida = factory.manufacturePojo(HojaDeVidaEntity.class);
        
        for (int i = 0; i < 3; i++) {
            ReferenciaEntity entity = factory.manufacturePojo(ReferenciaEntity.class);
            entity.setHojaDeVida(hojaDeVida);
            em.persist(entity);
            data.add(entity);          
        }
        
        hojaDeVida.setReferencias(data);
        em.persist(hojaDeVida);
    }

    /**
     * Prueba para crear un Editorial
     * 
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void createReferenciaTest() throws BusinessLogicException {
        ReferenciaEntity newEntity = factory.manufacturePojo(ReferenciaEntity.class);
        ReferenciaEntity result = referenciaLogic.createReferencia(hojaDeVida.getId(), newEntity);
        Assert.assertNotNull(result);
        ReferenciaEntity entity = em.find(ReferenciaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Prueba para consultar la lista de Editorials.
     */
    @Test
    public void getReferenciasTest() {
        List<ReferenciaEntity> list = referenciaLogic.getReferencias(hojaDeVida.getId());
        Assert.assertEquals(data.size(), list.size());
        for (ReferenciaEntity entity : list) {
            boolean found = false;
            for (ReferenciaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Editorial.
     */
    @Test
    public void getReferenciaTest() {
        ReferenciaEntity entity = data.get(0);
        ReferenciaEntity resultEntity = referenciaLogic.getReferencia(hojaDeVida.getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getIdRemitente(), resultEntity.getIdRemitente());
    }

    /**
     * Prueba para actualizar un Editorial.
     */
    @Test
    public void updateReferenciaTest() {
        ReferenciaEntity entity = data.get(0);
        ReferenciaEntity pojoEntity = factory.manufacturePojo(ReferenciaEntity.class);
        pojoEntity.setId(entity.getId());
        referenciaLogic.updateReferencia(pojoEntity.getId(), pojoEntity);
        ReferenciaEntity resp = em.find(ReferenciaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());  
        Assert.assertEquals(pojoEntity.getIdRemitente(), resp.getIdRemitente());
    }

    /**
     * Prueba para eliminar un Editorial.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteReferenciaTest() throws BusinessLogicException {
        ReferenciaEntity entity = data.get(1);
        referenciaLogic.deleteReferencia(entity.getId());
        ReferenciaEntity deleted = em.find(ReferenciaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }


}
