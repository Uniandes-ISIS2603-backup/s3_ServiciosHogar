/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;

import co.edu.uniandes.csw.servicioshogar.ejb.CalificacionLogic;
import co.edu.uniandes.csw.servicioshogar.entities.CalificacionEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
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
public class CalificacionLogicTest 
{
    private List<ClienteEntity> listaCliente = new ArrayList<ClienteEntity>();
    private List<SolicitudEntity> listaSolicitud = new ArrayList<SolicitudEntity>();
    private List<ServicioEntity> listaServicio = new ArrayList<ServicioEntity>();
    private List<CalificacionEntity> listaCalificacion = new ArrayList<CalificacionEntity>();

    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CalificacionLogic calificacionLogic;

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
   
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
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
    private void clearData() {em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from ServicioEntity").executeUpdate();
        em.createQuery("delete from SolicitudEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();}
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {   
     /**   for (int i = 0; i < 3; i++) 
        {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            ServicioEntity entityServicio = factory.manufacturePojo(ServicioEntity.class);                     
            entityServicio.setCalificacion(entity);
            entity.setServicio(entityServicio);
            em.persist(entity);
            dataServicio.add(entityServicio);
            em.persist(entityServicio);
            data.add(entity);
        }
        SolicitudEntity entitySolicitud = factory.manufacturePojo(SolicitudEntity.class);
        entitySolicitud.setServicios(dataServicio);
        for (int i = 0; i < 3; i++)     
        {
            dataServicio.get(i).setSolicitud(entitySolicitud);
        }
        */
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
            if(i != 0)
                calificacion.setServicio(listaServicio.get(i));
            listaCalificacion.add(calificacion);
            em.persist(calificacion);
            if(i != 0)
                listaServicio.get(i).setCalificacion(calificacion);            
        }
    }
    
    /**
     * Prueba para crear un Calificacion.     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void createCalificacionTest() throws BusinessLogicException 
    {
        
        ClienteEntity cliente = listaCliente.get(0);
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        SolicitudEntity solicitudEntity = listaSolicitud.get(0);
        ServicioEntity servicioEntity = listaServicio.get(0);
        newEntity.setServicio(servicioEntity);
        
        CalificacionEntity result = calificacionLogic.createCalificacion(solicitudEntity.getId(), servicioEntity.getId() ,newEntity);

        Assert.assertNotNull(result);
        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCalificacion(), entity.getCalificacion());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
    } 
    
    /**
     * Prueba para consultar un Calificacion.
     */
    @Test
    public void getCalificacionTest() 
    {
        ServicioEntity servicioEntity = listaServicio.get(1);
        CalificacionEntity entity = servicioEntity.getCalificacion();
        CalificacionEntity resultEntity = calificacionLogic.getCalificacion(servicioEntity.getId(),entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getCalificacion(), resultEntity.getCalificacion());
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
    }
    
    /**
     * Prueba para actualizar un Calificacion.
     */
    @Test
    public void updateCalificacionTest() 
    {        
        CalificacionEntity entity = listaCalificacion.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);    
        pojoEntity.setId(entity.getId());
       
        System.out.println("llego");
        calificacionLogic.updateCalificacion(listaServicio.get(1).getSolicitud().getId() ,listaCalificacion.get(1).getId(), pojoEntity);
        System.out.println("Salio");   
        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());       
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCalificacion(), resp.getCalificacion());
        Assert.assertEquals(pojoEntity.getComentario(), resp.getComentario());
    }
    
    /**
     * Prueba para eliminar un Calificacion.
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteCalificacionTest() throws BusinessLogicException 
    {
        CalificacionEntity entity = listaCalificacion.get(1);
        calificacionLogic.deleteCalificacion(entity.getServicio().getId(),entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
