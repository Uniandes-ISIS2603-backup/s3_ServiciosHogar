/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.persistence.ClientePersistence;
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

    /*Manejador de transacciones*/
    @Inject
    UserTransaction utx;     

    //------------------------------------------
    //------------------Listas------------------
    //------------------------------------------
    /*Lista de clientes - Almacena los clientes para las pruebas*/
    private List<ClienteEntity> listaCliente = new ArrayList<>();
    
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
        //Se agregan 3 clientes a la bd
        for (int i = 0; i < 3; i++) 
        {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            listaCliente.add(entity);
        }
    }
    
    /**
     * Prueba para crear un Cliente.
     */
    @Test
    public void createClienteTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        
        //Se crea un cliente
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clientePersistence.create(cliente);
        
        Assert.assertNotNull(result);       
        Assert.assertEquals(cliente.getNombre(), result.getNombre());
        Assert.assertEquals(cliente.getCorreo(), result.getCorreo());
        Assert.assertEquals(cliente.getDireccion(), result.getDireccion());
    }
    
    /**
     * Prueba para consultar la lista de todos los clientes.
     */
    @Test
    public void getClientesTest() 
    {
        List<ClienteEntity> list = clientePersistence.findAll();
        Assert.assertEquals(listaCliente.size(), list.size());
        for (ClienteEntity ent : list) 
        {
            boolean found = false;
            
            for (ClienteEntity entity : listaCliente)             
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
        ClienteEntity entity = listaCliente.get(0);
        ClienteEntity cliente = clientePersistence.find(entity.getId());
        
        Assert.assertNotNull(cliente);
        Assert.assertEquals(entity.getNombre(), cliente.getNombre());
        Assert.assertEquals(entity.getCorreo(), cliente.getCorreo());
        Assert.assertEquals(entity.getDireccion(), cliente.getDireccion());
    }
    
    /**
     * Prueba para actualizar un cliente.
     */
    @Test
    public void updateClienteTest() 
    {
        ClienteEntity entity = listaCliente.get(0);
        
        PodamFactory factory = new PodamFactoryImpl();
        
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
        cliente.setId(entity.getId());
        clientePersistence.update(cliente);
        
        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());
        
        Assert.assertEquals(cliente.getNombre(), resp.getNombre());
        Assert.assertEquals(cliente.getCorreo(), resp.getCorreo());
        Assert.assertEquals(cliente.getDireccion(), resp.getDireccion());
    }
    
    /**
     * Prueba para eliminar un cliente.
     */
    @Test
    public void deleteClienteTest() 
    {
        ClienteEntity entity = listaCliente.get(0);
        clientePersistence.delete(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        
        Assert.assertNull(deleted);
    }    
}