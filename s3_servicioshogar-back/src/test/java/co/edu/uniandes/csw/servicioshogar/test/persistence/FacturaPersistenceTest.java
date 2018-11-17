/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.entities.FacturaEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.persistence.FacturaPersistence;
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
 * @author Carlos Robles // 3er Ciclo
 */
@RunWith(Arquillian.class)
public class FacturaPersistenceTest 
{
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------
    /*Inyectas las dependencias.*/ 
    @Inject
    private FacturaPersistence facturaPersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<ClienteEntity> listaCliente = new ArrayList<>();
    private List<SolicitudEntity> listaSolicitud = new ArrayList<>();
    private List<FacturaEntity> listaFactura = new ArrayList<>();
    
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
                .addPackage(FacturaEntity.class.getPackage())
                .addPackage(FacturaPersistence.class.getPackage())
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
    private void clearData() 
    {
        //Se elimina la informacion ingresada
        em.createQuery("delete from FacturaEntity").executeUpdate();
        em.createQuery("delete from SolicitudEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();

    }
    
    /**
     * Inserta los datos iniciales para que las pruebas funcionen correctamente.
     */
    private void insertData() 
    {        
        PodamFactory factory = new PodamFactoryImpl();
        //Se agrega un cliente a la bd. 
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);        
        listaCliente.add(cliente);
        em.persist(cliente);
        
        //Se agregan 3 solicitudes a un cliente.
        for(int i = 0; i < 3; i++)
        {
            SolicitudEntity solicitud = factory.manufacturePojo(SolicitudEntity.class);
            solicitud.setCliente(cliente);
            listaSolicitud.add(solicitud);
            em.persist(solicitud);
        }
        cliente.setSolicitudes(listaSolicitud);

        //Se agregan tres facturas a tres solicitudes
        for(int i = 0; i < 3; i++)
        {
            FacturaEntity factura = factory.manufacturePojo(FacturaEntity.class);
            factura.setSolicitud(listaSolicitud.get(i));
            listaFactura.add(factura);
            listaSolicitud.get(i).setFactura(factura);
            em.persist(factura);            
        }      
    }
    
    /**
     * Prueba para crear un Factura.
     */
    @Test
    public void createFacturaTest() 
    {        
        PodamFactory factory = new PodamFactoryImpl();
        
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        FacturaEntity result = facturaPersistence.create(newEntity);
        
        //Asserts
        Assert.assertEquals(newEntity.getFecha(), result.getFecha());
        Assert.assertEquals(newEntity.getMetodoPago(), result.getMetodoPago());        
        Assert.assertEquals(newEntity.getNoFactura(), result.getNoFactura());
        Assert.assertEquals(newEntity.getValor(), result.getValor());
        
        System.out.println("Test realizado exitosamente");
    }   
    
    /**
     * Prueba para consultar una factura.
     */
    @Test
    public void getFacturaTest() 
    {
       FacturaEntity result = listaFactura.get(0);        
       FacturaEntity newEntity = facturaPersistence.find(result.getId());
       
       //Assert
       Assert.assertNotNull(newEntity);
       Assert.assertEquals(newEntity.getFecha(), result.getFecha());
       Assert.assertEquals(newEntity.getMetodoPago(), result.getMetodoPago());        
       Assert.assertEquals(newEntity.getNoFactura(), result.getNoFactura());
       Assert.assertEquals(newEntity.getValor(), result.getValor());
       
       System.out.println("Test realizado exitosamente");
    }
    
    /**
     * Prueba para actualizar una factura.
     */
    @Test
    public void updateFacturaTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        
        //Se verifica la primera factura.
        FacturaEntity old = listaFactura.get(0);  
        FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
        
        newEntity.setId(old.getId());
        facturaPersistence.update(newEntity);

        FacturaEntity resp = em.find(FacturaEntity.class, old.getId());

        Assert.assertEquals(newEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(newEntity.getMetodoPago(), resp.getMetodoPago());        
        Assert.assertEquals(newEntity.getNoFactura(), resp.getNoFactura());
        Assert.assertEquals(newEntity.getValor(), resp.getValor());
        
        //Se verifica la segunda factura.
        old = listaFactura.get(1);    
        newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setId(old.getId());
        facturaPersistence.update(newEntity);

        resp = em.find(FacturaEntity.class, old.getId());

        Assert.assertEquals(newEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(newEntity.getMetodoPago(), resp.getMetodoPago());        
        Assert.assertEquals(newEntity.getNoFactura(), resp.getNoFactura());
        Assert.assertEquals(newEntity.getValor(), resp.getValor());
        
        //Se verifica la tercera factura.
        old = listaFactura.get(2);    
        newEntity = factory.manufacturePojo(FacturaEntity.class);
        newEntity.setId(old.getId());
        facturaPersistence.update(newEntity);

        resp = em.find(FacturaEntity.class, old.getId());

        Assert.assertEquals(newEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(newEntity.getMetodoPago(), resp.getMetodoPago());        
        Assert.assertEquals(newEntity.getNoFactura(), resp.getNoFactura());
        Assert.assertEquals(newEntity.getValor(), resp.getValor());
        
    }
    
    /**
     * Prueba para eliminar una factura.
     */
    @Test
    public void deleteFacturaTest() 
    {
        FacturaEntity entity = listaFactura.get(2);
        facturaPersistence.delete(entity.getId());
        FacturaEntity deleted = em.find(FacturaEntity.class, entity.getId());
        
        Assert.assertNull(deleted);
    }
}
