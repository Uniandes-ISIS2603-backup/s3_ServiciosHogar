/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;
import co.edu.uniandes.csw.servicioshogar.ejb.TarjetaCreditoLogic;
import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.entities.TarjetaCreditoEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.TarjetaCreditoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
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
 * @author Adriana Trujillo
 */
@RunWith(Arquillian.class)
public class TarjetaCreditoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private TarjetaCreditoLogic tarjetaLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction ut;
    
    private List<TarjetaCreditoEntity> data = new ArrayList<TarjetaCreditoEntity>();

    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TarjetaCreditoEntity.class.getPackage())
                .addPackage(TarjetaCreditoLogic.class.getPackage())
                .addPackage(TarjetaCreditoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void configTest()
    {
        try{
            ut.begin();
            clearData();
            insertData();
            ut.commit();
            
        }catch(Exception e)
        {
            e.printStackTrace();
            try{
                ut.rollback();
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }
        }
    }
        
    private void clearData()
    {
        em.createQuery("delete from TarjetaCreditoEntity").executeUpdate();
    }
    
    private void insertData()
    {
        for(int i =0; i<3; i++)
        {
            TarjetaCreditoEntity entity = factory.manufacturePojo(TarjetaCreditoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createTarjetaTest() throws BusinessLogicException
    {
        TarjetaCreditoEntity entity = factory.manufacturePojo(TarjetaCreditoEntity.class);

        TarjetaCreditoEntity result = tarjetaLogic.createTarjeta(entity);
        Assert.assertNotNull(result);
        
        TarjetaCreditoEntity newEntity = em.find(TarjetaCreditoEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNumero(), newEntity.getNumero());
        Assert.assertEquals(entity.getTitular(), newEntity.getTitular());
        Assert.assertEquals(entity.getFechaVencimiento(), newEntity.getFechaVencimiento());
    }
    
       @Test
    public void getTarjetasTest() throws BusinessLogicException
    {
        List<TarjetaCreditoEntity> list = tarjetaLogic.getTarjetas();
        org.junit.Assert.assertEquals(data.size(),list.size());
        for(TarjetaCreditoEntity ent : list)
        {
            boolean found = false;
            for(TarjetaCreditoEntity entity : data)
            {
                if(ent.getId().equals(entity.getId()))
                {
                    found = true;
                }
            }
            org.junit.Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getTarjetaTest() throws BusinessLogicException
    {
        TarjetaCreditoEntity entity = data.get(0);
        TarjetaCreditoEntity newEntity = tarjetaLogic.getTarjeta(entity.getId());
        org.junit.Assert.assertNotNull(newEntity);
        org.junit.Assert.assertEquals(entity.getId(), newEntity.getId());
        org.junit.Assert.assertEquals(entity.getNumero(), newEntity.getNumero());
        org.junit.Assert.assertEquals(entity.getTitular(), newEntity.getTitular());
        org.junit.Assert.assertEquals(entity.getFechaVencimiento(), newEntity.getFechaVencimiento());
    }
    
    @Test
    public void deleteTarjetaTest() throws BusinessLogicException
    {
        TarjetaCreditoEntity entity = data.get(0);
        tarjetaLogic.deleteFactura(entity.getId());
        TarjetaCreditoEntity deleted = em.find(TarjetaCreditoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test()
    public void updateTarjetaTest() 
    {
        TarjetaCreditoEntity entity = data.get(0);
        TarjetaCreditoEntity pojo = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        pojo.setId(entity.getId());
                
        tarjetaLogic.updateTarjeta(pojo.getId(), pojo);
        
        TarjetaCreditoEntity resp = em.find(TarjetaCreditoEntity.class, entity.getId());
        
        Assert.assertEquals(pojo.getId(), resp.getId());
        Assert.assertEquals(pojo.getNumero(), resp.getNumero());
        Assert.assertEquals(pojo.getTitular(), resp.getTitular());
        Assert.assertEquals(pojo.getFechaVencimiento(), resp.getFechaVencimiento());
    }
}