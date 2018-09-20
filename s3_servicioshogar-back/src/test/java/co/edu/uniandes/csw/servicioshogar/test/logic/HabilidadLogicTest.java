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
import co.edu.uniandes.csw.servicioshogar.ejb.HabilidadLogic;
import co.edu.uniandes.csw.servicioshogar.entities.HabilidadEntity;
import co.edu.uniandes.csw.servicioshogar.persistence.HabilidadPersistence;
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
public class HabilidadLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private HabilidadLogic habilidadLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<HabilidadEntity> data = new ArrayList<>();
    
    private List<PrestadorEntity> dataPrestador = new ArrayList<>();
    
   /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HabilidadEntity.class.getPackage())
                .addPackage(HabilidadLogic.class.getPackage())
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
    
        for (int i = 0; i < 3; i++) {
            PrestadorEntity entity = factory.manufacturePojo(PrestadorEntity.class);
            em.persist(entity);
            dataPrestador.add(entity);
        }
        
        for (int i = 0; i < 3; i++) {
            HabilidadEntity entity = factory.manufacturePojo(HabilidadEntity.class);
            entity.setPrestador(dataPrestador.get(0));
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createHabilidadTest() throws BusinessLogicException
    {
        HabilidadEntity newEntity = factory.manufacturePojo(HabilidadEntity.class);
        HabilidadEntity result = habilidadLogic.createHabilidad(dataPrestador.get(0).getId(), newEntity);
        Assert.assertNotNull(result);
        HabilidadEntity entity = em.find(HabilidadEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createPrestadorConDescripcionInvalida() throws BusinessLogicException
    {
        HabilidadEntity newEntity = factory.manufacturePojo(HabilidadEntity.class);
        newEntity.setDescripcion(null);
        HabilidadEntity result = habilidadLogic.createHabilidad(dataPrestador.get(0).getId(), newEntity);      
        
    }
    
    @Test(expected = BusinessLogicException.class)
    public void creatHabilidadConPrestadorInvalido() throws BusinessLogicException
    {
        HabilidadEntity newEntity = factory.manufacturePojo(HabilidadEntity.class);
        HabilidadEntity result = habilidadLogic.createHabilidad(new Long(0), newEntity);     

    }
    
    @Test(expected = BusinessLogicException.class)
    public void createHabilidadConTipoInvalido() throws BusinessLogicException
    {
        HabilidadEntity newEntity = factory.manufacturePojo(HabilidadEntity.class);
        newEntity.setTipo(null);
        HabilidadEntity result = habilidadLogic.createHabilidad(dataPrestador.get(0).getId(), newEntity);

    }
    
    @Test
    public void getHabilidadest() {
        List<HabilidadEntity> list = habilidadLogic.getHabilidades(dataPrestador.get(0).getId());
        Assert.assertEquals(data.size(), list.size());
        for (HabilidadEntity entity : list) {
            boolean found = false;
            for (HabilidadEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getHabilidadTest() throws BusinessLogicException
    {
        HabilidadEntity entity = data.get(0);
        HabilidadEntity resp = habilidadLogic.getHabilidad(dataPrestador.get(0).getId(), entity.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(entity.getId(), resp.getId());
        Assert.assertEquals(entity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(entity.getTipo(), resp.getTipo());
        
    }
    @Test
    public void updateHabilidadTest() throws BusinessLogicException
    {
        HabilidadEntity entity = data.get(0);
        HabilidadEntity pojoEntity = factory.manufacturePojo(HabilidadEntity.class);
        pojoEntity.setId(entity.getId());
        habilidadLogic.updateHabilidad(pojoEntity.getId(), pojoEntity);
        HabilidadEntity resp = em.find(HabilidadEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
        
    }
    
    @Test
    public void deleteHabilidadTest() throws BusinessLogicException {
        HabilidadEntity entity = data.get(0);
        habilidadLogic.deleteHabilidad(dataPrestador.get(0).getId(), entity.getId());
        HabilidadEntity deleted = em.find(HabilidadEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
