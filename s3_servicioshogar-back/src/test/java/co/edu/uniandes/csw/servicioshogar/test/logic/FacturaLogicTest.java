/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;

import co.edu.uniandes.csw.servicioshogar.ejb.FacturaLogic;
import co.edu.uniandes.csw.servicioshogar.entities.FacturaEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.FacturaPersistence;
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
public class FacturaLogicTest {    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private FacturaLogic facturaLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction ut;
    
    private List<FacturaEntity> data = new ArrayList<FacturaEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaLogic.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
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
        em.createQuery("delete from FacturaEntity").executeUpdate();
    }
    
    private void insertData()
    {
        for(int i =0; i<3; i++)
        {
            FacturaEntity entity = factory.manufacturePojo(FacturaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createFacturaTest() throws BusinessLogicException
    {
        FacturaEntity entity = factory.manufacturePojo(FacturaEntity.class);
        
        FacturaEntity result = facturaLogic.createFactura(entity);
        Assert.assertNotNull(result);
        
        FacturaEntity newEntity = em.find(FacturaEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNoFactura(), newEntity.getNoFactura());
    }
    
       @Test
    public void getFacturasTest()
    {
        List<FacturaEntity> list = facturaLogic.getFacturas();
        org.junit.Assert.assertEquals(data.size(),list.size());
        for(FacturaEntity ent : list)
        {
            boolean found = false;
            for(FacturaEntity entity : data)
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
    public void getFacturaTest()
    {
        FacturaEntity entity = data.get(0);
        FacturaEntity newEntity = facturaLogic.getFactura(entity.getId());
        org.junit.Assert.assertNotNull(newEntity);
        org.junit.Assert.assertEquals(entity.getId(), newEntity.getId());
        org.junit.Assert.assertEquals(entity.getNoFactura(), newEntity.getNoFactura());
        org.junit.Assert.assertEquals(entity.getValor(), newEntity.getValor());
    }
    
    @Test
    public void deleteFacturaTest() throws BusinessLogicException
    {
        FacturaEntity entity = data.get(0);
        facturaLogic.deleteFactura(entity.getId());
        FacturaEntity deleted = em.find(FacturaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void updateFacturaTest()
    {
        FacturaEntity entity = data.get(0);
        FacturaEntity pojo = factory.manufacturePojo(FacturaEntity.class);
        
        pojo.setId(entity.getId());
        
        facturaLogic.updateFactura(pojo.getId(),pojo);
        
        FacturaEntity result = em.find(FacturaEntity.class, entity.getId());
        
        Assert.assertEquals(pojo.getId(), result.getId());
        Assert.assertEquals(pojo.getNoFactura(), result.getNoFactura());
                
    }
}