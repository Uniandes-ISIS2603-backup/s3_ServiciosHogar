/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;

import co.edu.uniandes.csw.servicioshogar.ejb.SolicitudLogic;
import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.SolicitudPersistence;
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
 * @author Juan Sebastian Gomez
 */
@RunWith(Arquillian.class)
public class SolicitudLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    @Inject
    private SolicitudLogic logic;
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserTransaction utx;
    private List<SolicitudEntity> data = new ArrayList<>();
    private ClienteEntity cliente;
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SolicitudEntity.class.getPackage())
                .addPackage(SolicitudLogic.class.getPackage())
                .addPackage(SolicitudPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    @Before
    public void configTest(){
        try{
            utx.begin();
            clearData();
            insertData();
            utx.commit();
            
        }catch(Exception e)
        {
            e.printStackTrace();
            try{
                utx.rollback();
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }
        }
    }
    private void clearData(){
        em.createQuery("delete from SolicitudEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }
    private void insertData(){
        cliente = factory.manufacturePojo(ClienteEntity.class);
        cliente.setSolicitudes(data);
        em.persist(cliente);
        for(int i =0; i<3;i++){
            SolicitudEntity s = factory.manufacturePojo(SolicitudEntity.class);
            s.setCliente(cliente);
            em.persist(s);
            data.add(s);
        }
    }
    @Test
    public void createSolicitudTest(){
        SolicitudEntity s = factory.manufacturePojo(SolicitudEntity.class);
        s = logic.createSolicitud(cliente.getId(), s);
        Assert.assertNotNull(s);
        SolicitudEntity c = em.find(SolicitudEntity.class, s.getId());
        Assert.assertEquals(s.getId(),c.getId());
        Assert.assertEquals(s.getDireccion(),c.getDireccion());
        Assert.assertEquals(s.getFecha(),c.getFecha());
    }
    @Test
    public void getSolicitudesTest(){
        List<SolicitudEntity> copia = data;
        List<SolicitudEntity> c= logic.getSolicitudes(cliente.getId());
        Assert.assertEquals(copia.size(),c.size());
        for(SolicitudEntity s:copia){
            boolean found = false;
            for(SolicitudEntity k: c ){
                if(k.getId()==s.getId()){
                    found =true;
                    break;
                }
            }
            Assert.assertTrue(found);
        }
    }
    @Test
    public void getSolicitudTest(){
        SolicitudEntity s = data.get(0);
        SolicitudEntity c = logic.getSolicitud(cliente.getId(), s.getId());
        Assert.assertEquals(s.getId(),c.getId());
        Assert.assertEquals(s.getDireccion(),c.getDireccion());
        Assert.assertEquals(s.getFecha(),c.getFecha());
    }
    @Test
    public void updateSolicitudTest(){
        SolicitudEntity x = data.get(0);
        SolicitudEntity c = factory.manufacturePojo(SolicitudEntity.class);
        c.setId(x.getId());
        SolicitudEntity s = logic.updateSolicitud(x.getId(), c);
        Assert.assertNotNull(s);
        Assert.assertEquals(s.getId(),c.getId());
        Assert.assertEquals(s.getDireccion(),c.getDireccion());
        Assert.assertEquals(s.getFecha(),c.getFecha());
    }
    @Test
    public void deleteSolicitudTest() throws BusinessLogicException{
        SolicitudEntity s = data.get(0);
        logic.deleteSolicitud(cliente.getId(), s.getId());
        SolicitudEntity c = em.find(SolicitudEntity.class, s.getId());
        Assert.assertNull(c);
    }
}