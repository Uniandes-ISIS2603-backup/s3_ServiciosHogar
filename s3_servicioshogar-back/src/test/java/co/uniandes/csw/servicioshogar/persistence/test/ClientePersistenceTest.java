/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uniandes.csw.servicioshogar.persistence.test;

import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.uniandes.csw.servicioshogar.persistence.ClientePersistence;
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
 * Pruebas de persistencia Cliente
 * @author Carlos Eduardo Robles Quevedo
 */
@RunWith(Arquillian.class)
public class ClientePersistenceTest 
{
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------
    /*Inyectas las dependencias.*/
    @Inject
    private ClientePersistence clientePersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<ClienteEntity> data = new ArrayList<>();
    
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
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
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
    private void clearData() {em.createQuery("delete from ClienteEntity").executeUpdate();}
    
    /**
     * Inserta los datos iniciales para que las pruebas funcionen correctamente.
     */
    private void insertData() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) 
        {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un Cliente.
     */
    @Test
    public void createClienteTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clientePersistence.create(newEntity);

        Assert.assertNotNull(result);
        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    /**
     * Prueba para consultar la lista de todos los clientes.
     */
    @Test
    public void getClientesTest() 
    {
        List<ClienteEntity> list = clientePersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity ent : list) 
        {
            boolean found = false;
            
            for (ClienteEntity entity : data)             
                if (ent.getId().equals(entity.getId()))
                    found = true;            
            
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un cliente.
     */
    @Test
    public void getClienteTest() 
    {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = clientePersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getCorreo(), newEntity.getCorreo());
        Assert.assertEquals(entity.getDireccion(), newEntity.getDireccion());
    }
    
    /**
     * Prueba para actualizar un cliente.
     */
    @Test
    public void updateClienteTest() 
    {
        ClienteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setId(entity.getId());
        clientePersistence.update(newEntity);
        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }
    
    /**
     * Prueba para eliminar un cliente.
     */
    @Test
    public void deleteAuthorTest() 
    {
        ClienteEntity entity = data.get(0);
        clientePersistence.delete(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }  
}
