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
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.junit.Assert.*;
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

    private List<ClienteEntity> dataCliente = new ArrayList<ClienteEntity>();
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }
    
    private void insertData()
    {
        for(int i =0; i<3; i++)
        {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            dataCliente.add(entity);
        }
        for( int i = 0; i<3; i++)
        {
            TarjetaCreditoEntity entity= factory.manufacturePojo(TarjetaCreditoEntity.class);
            entity.setCliente(dataCliente.get(i));
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createTarjetaTest() throws BusinessLogicException
    {
        TarjetaCreditoEntity entity = factory.manufacturePojo(TarjetaCreditoEntity.class);
        entity.setCliente(dataCliente.get(1));
        TarjetaCreditoEntity result = tarjetaLogic.createTarjeta(entity, dataCliente.get(1).getId());
        assertNotNull(result);
        
        TarjetaCreditoEntity newEntity = em.find(TarjetaCreditoEntity.class, result.getId());
        assertEquals(entity.getId(), newEntity.getId());
        assertEquals(entity.getNumero(), newEntity.getNumero());
        assertEquals(entity.getTitular(), newEntity.getTitular());
        assertEquals(entity.getFechaVencimiento(), newEntity.getFechaVencimiento());
    }
    
    
    @Test
    public void getTarjetaTest() throws BusinessLogicException
    {
        TarjetaCreditoEntity entity = data.get(0);
        TarjetaCreditoEntity newEntity = tarjetaLogic.getTarjeta(dataCliente.get(1).getId(), entity.getId());
        assertNotNull(newEntity);
        assertEquals(entity.getId(), newEntity.getId());
        assertEquals(entity.getNumero(), newEntity.getNumero());
        assertEquals(entity.getTitular(), newEntity.getTitular());
        assertEquals(entity.getFechaVencimiento(), newEntity.getFechaVencimiento());
    }
    
    @Test
    public void deleteTarjetaTest() throws BusinessLogicException
    {
        TarjetaCreditoEntity entity = data.get(0);
        tarjetaLogic.deleteTarjeta(dataCliente.get(1).getId(), entity.getId() );
        TarjetaCreditoEntity deleted = em.find(TarjetaCreditoEntity.class, entity.getId());
        assertNull(deleted);
    }
    
    @Test()
    public void updateTarjetaTest() 
    {
        TarjetaCreditoEntity entity = data.get(0);
        TarjetaCreditoEntity pojo = factory.manufacturePojo(TarjetaCreditoEntity.class);
        
        pojo.setId(entity.getId());
                
        tarjetaLogic.updateTarjeta(dataCliente.get(1).getId(), pojo);
        
        TarjetaCreditoEntity resp = em.find(TarjetaCreditoEntity.class, entity.getId());
        
        assertEquals(pojo.getId(), resp.getId());
        assertEquals(pojo.getNumero(), resp.getNumero());
        assertEquals(pojo.getTitular(), resp.getTitular());
        assertEquals(pojo.getFechaVencimiento(), resp.getFechaVencimiento());
    }
}