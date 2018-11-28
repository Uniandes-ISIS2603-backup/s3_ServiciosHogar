/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;

import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorServiciosLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.ServicioLogic;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicioshogar.persistence.PrestadorPersistence;
import co.edu.uniandes.csw.servicioshogar.persistence.ServicioPersistence;
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
public class PrestadorServicioLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    @Inject
    private PrestadorServiciosLogic logic;
    @PersistenceContext
    EntityManager em;
    @Inject
    private UserTransaction utx;
    private List<ServicioEntity> dataServ = new ArrayList<>();
    private List<PrestadorEntity> dataPrestador = new ArrayList<>();
     @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioEntity.class.getPackage())
                .addPackage(ServicioLogic.class.getPackage())
                .addPackage(ServicioPersistence.class.getPackage())
                .addPackage(PrestadorEntity.class.getPackage())
                .addPackage(PrestadorLogic.class.getPackage())
                .addPackage(PrestadorPersistence.class.getPackage())
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
        em.createQuery("delete from PrestadorEntity").executeUpdate();
        
    }
    private void insertData(){
        PrestadorEntity prestador = factory.manufacturePojo(PrestadorEntity.class);
        prestador.setServicios(dataServ);
        em.persist(prestador);
        dataPrestador.add(prestador);
        for(int i = 0; i<3;i++){
            ServicioEntity nuevo = factory.manufacturePojo(ServicioEntity.class);
            nuevo.setPrestador(prestador);
            em.persist(nuevo);
            dataServ.add(nuevo);
        }
    }
    @Test
    public void findAllServices()throws BusinessLogicException{
        List<ServicioEntity> listica = dataServ;
        List<ServicioEntity> result = logic.findAllServices(dataPrestador.get(0).getId());
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
       ServicioEntity a = logic.finServices(dataPrestador.get(0).getId(),s.getId());
       Assert.assertEquals(a.getRequerimientos(),s.getRequerimientos());
       Assert.assertEquals(a.getDescripcion(),s.getDescripcion());
       Assert.assertEquals(a.getId(),s.getId());
       
   }
}