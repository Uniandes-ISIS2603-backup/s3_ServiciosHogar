/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;

import co.edu.uniandes.csw.servicioshogar.ejb.ServicioLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.SolicitudLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.SolicitudServiciosLogic;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
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
public class SolicitudServiciosLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    @Inject
    private SolicitudServiciosLogic logic;
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserTransaction utx;
    private List<ServicioEntity> dataServ = new ArrayList<>();
    private SolicitudEntity solicitud;
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioEntity.class.getPackage())
                .addPackage(ServicioLogic.class.getPackage())
                .addPackage(ServicioPersistence.class.getPackage())
                .addPackage(SolicitudEntity.class.getPackage())
                .addPackage(SolicitudLogic.class.getPackage())
                .addPackage(SolicitudPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
        @Before
    public void configTest()
    {
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
        em.createQuery("delete from ServicioEntity").executeUpdate();
        em.createQuery("delete from SolicitudEntity").executeUpdate();
    }
    private void insertData(){
        solicitud = factory.manufacturePojo(SolicitudEntity.class);
        solicitud.setServicios(dataServ);
        em.persist(solicitud);
        for(int i=0; i<3;i++){
            ServicioEntity s = factory.manufacturePojo(ServicioEntity.class);
            s.setSolicitud(solicitud);
            em.persist(s);
            dataServ.add(s);
        }
    }
    @Test
    public void findAllServices()throws BusinessLogicException{
        List<ServicioEntity> listica = dataServ;
        List<ServicioEntity> result = logic.findAllServices(solicitud.getId());
        Assert.assertNotNull(result);
        Assert.assertEquals(listica.size(),result.size());
        for(ServicioEntity s: listica){
            boolean encuentra=false;
            for(ServicioEntity e:result){
                if(s.getId()==e.getId()){
                    encuentra=true;
                    break;
                }
            }
            Assert.assertTrue(encuentra);
        }
    }
    
   @Test
   public void finServicesTest()throws BusinessLogicException{
       ServicioEntity s = dataServ.get(0);
       ServicioEntity a = logic.findServices(solicitud.getId(),s.getId());
       Assert.assertEquals(a.getRequerimientos(),s.getRequerimientos());
       Assert.assertEquals(a.getDescripcion(),s.getDescripcion());
       Assert.assertEquals(a.getId(),s.getId());   
   }
}