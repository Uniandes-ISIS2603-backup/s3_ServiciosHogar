/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.entities.TarjetaCreditoEntity;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * @author Carlos Robles / 3er Ciclo
 */
@RunWith(Arquillian.class)
public class TarjetaCreditoPersistenceTest 
{
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------
    /*Inyectas las dependencias.*/
    @Inject
    private TarjetaCreditoPersistence tarjetaCreditoPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<ClienteEntity> listaCliente = new ArrayList<ClienteEntity>();
    private List<TarjetaCreditoEntity> listaTarjetaCredito = new ArrayList<>(); 
	

    //------------------------------------------
    //------------------Metodos-----------------
    //------------------------------------------    
    /**
     * ???.
     *
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TarjetaCreditoEntity.class.getPackage())
                .addPackage(TarjetaCreditoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuracion inicial de la prueba.
     */
    @Before
    public void configTest() 
    {
        try {
            utx.begin();
            em.joinTransaction();
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
     * Limpia las tablas implicadas en la prueba.
     */
    private void clearData() 
    {
        em.createQuery("delete from TarjetaCreditoEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para que las pruebas funcionen correctamente.
     */
    private void insertData() 
    {
        PodamFactory factory = new PodamFactoryImpl(); 
        
        //Se crea el cliente y se persiste
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
        em.persist(cliente);
        listaCliente.add(cliente);
        
        for (int i = 0; i < 3; i++) 
        {        
            TarjetaCreditoEntity tarjeta = factory.manufacturePojo(TarjetaCreditoEntity.class);
            tarjeta.setCliente(cliente);
            em.persist(tarjeta);
            listaTarjetaCredito.add(tarjeta);
        }
        cliente.setTarjetas(listaTarjetaCredito);
        
    }

    @Test
    public void createTarjetaCreditoTest() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        
        TarjetaCreditoEntity newEntity = factory.manufacturePojo(TarjetaCreditoEntity.class);
        TarjetaCreditoEntity result = tarjetaCreditoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        TarjetaCreditoEntity entity = em.find(TarjetaCreditoEntity.class, result.getId());

        Assert.assertEquals(entity.getCodeSeguridad(), newEntity.getCodeSeguridad());
        Assert.assertEquals(entity.getFechaVencimiento(), newEntity.getFechaVencimiento());
        Assert.assertEquals(entity.getNumero(), newEntity.getNumero());
        Assert.assertEquals(entity.getTitular(), newEntity.getTitular());
    }

    /**
     * Prueba para consultar la lista de TarjetaCreditos.
     */
    @Test
    public void getTarjetasCreditosTest() 
    {
        /*List<TarjetaCreditoEntity> list = tarjetaCreditoPersistence.findAll(listaCliente.get(0).getId());

        Assert.assertEquals(listaTarjetaCredito.size(), list.size());

        for(TarjetaCreditoEntity ent : list) 
        {
            boolean found = false;
            for (TarjetaCreditoEntity entity : listaTarjetaCredito)             
                if (ent.getId().equals(entity.getId())) 
                    found = true;                
            
            Assert.assertTrue(found);
        }*/
    }

    /**
     * Prueba para consultar un TarjetaCredito.
     */
    @Test
    public void getTarjetaCreditoTest() {

    }

    /**
     * Prueba para eliminar una tarjetaCredito.
     */
    @Test
    public void deleteTarjetaCreditoTest() 
    {
        
        TarjetaCreditoEntity entity = listaTarjetaCredito.get(0);
        tarjetaCreditoPersistence.delete(entity.getId());
        TarjetaCreditoEntity deleted = em.find(TarjetaCreditoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una tarjetaCredito.
     */
    @Test
    public void updateTarjetaCreditoTest() 
    {
        

    }
}
