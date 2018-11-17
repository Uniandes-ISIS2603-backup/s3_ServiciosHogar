/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.persistence.CalificacionPersistence;
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
 * @author Carlos Eduardo Robles
 */
@RunWith(Arquillian.class)
public class CalificacionPersistenceTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CalificacionPersistence calificacionPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ClienteEntity> listaCliente = new ArrayList<ClienteEntity>();
    private List<SolicitudEntity> listaSolicitud = new ArrayList<SolicitudEntity>();
    private List<ServicioEntity> listaServicio = new ArrayList<ServicioEntity>();
    private List<CalificacionEntity> listaCalificacion = new ArrayList<CalificacionEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
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
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from ServicioEntity").executeUpdate();
        em.createQuery("delete from SolicitudEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {              
        PodamFactory factory = new PodamFactoryImpl();
        //Se agrega un cliente a la bd. 
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
        
        listaCliente.add(cliente);
        em.persist(cliente);
        
        //Se agrega una solicitud a un cliente.
        SolicitudEntity solicitud = factory.manufacturePojo(SolicitudEntity.class);
        solicitud.setCliente(cliente);
        listaSolicitud.add(solicitud);
        em.persist(solicitud);
        cliente.setSolicitudes(listaSolicitud);
        
        
        //Se agregan 3 servicios a una solicitud
        for(int i = 0; i < 3; i++)
        {
            ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
            servicio.setSolicitud(solicitud);
            listaServicio.add(servicio);
            em.persist(servicio);
        }
        solicitud.setServicios(listaServicio);
        
        for(int i = 0; i < 3; i++)
        {
            CalificacionEntity calificacion = factory.manufacturePojo(CalificacionEntity.class);
            calificacion.setServicio(listaServicio.get(i));
            listaCalificacion.add(calificacion);
            em.persist(calificacion);
            listaServicio.get(i).setCalificacion(calificacion);            
        }       
    }

    /**
     * Prueba para crear un Calificacion.
     */
    @Test
    public void createCalificacionTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);

        CalificacionEntity result = calificacionPersistence.create(newEntity);

        Assert.assertNotNull(result);

        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());

        Assert.assertEquals(newEntity.getCalificacion(), entity.getCalificacion());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
    }  

    /**
     * Prueba para consultar un Calificacion.
     */
    @Test
    public void getCalificacionTest() 
    {  
        System.out.println("tam cli: " + listaCliente.size());
        System.out.println("tam sol: " + listaSolicitud.size());
        System.out.println("tam servicios: " + listaServicio.size());
        System.out.println("tam calif: "+ listaCalificacion.size());
        System.out.println(  "Aqui llegue");
        CalificacionEntity entity = listaCalificacion.get(0);
                

        CalificacionEntity newEntity = calificacionPersistence.find(entity.getServicio().getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getCalificacion(), newEntity.getCalificacion());
        Assert.assertEquals(entity.getComentario(), newEntity.getComentario());
    }  

    /**
     * Prueba para actualizar un Calificacion.
     */
    @Test
    public void updateCalificacionTest() 
    {
        CalificacionEntity entity = listaCalificacion.get(0);   
        
        PodamFactory factory = new PodamFactoryImpl();
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);

        newEntity.setId(entity.getId());

        calificacionPersistence.update(newEntity);

        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getCalificacion(), resp.getCalificacion());
        Assert.assertEquals(newEntity.getComentario(), resp.getComentario());
    }
    
    /**
     * Prueba para eliminar un Calificacion.
     */
    @Test
    public void deleteCalificacionTest() 
    {
        CalificacionEntity entity = listaCalificacion.get(2);
        calificacionPersistence.delete(entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
