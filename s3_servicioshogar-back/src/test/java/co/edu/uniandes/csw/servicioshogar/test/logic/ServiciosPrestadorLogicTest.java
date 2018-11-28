/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;

import co.edu.uniandes.csw.servicioshogar.ejb.PrestadorLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.ServicioLogic;
import co.edu.uniandes.csw.servicioshogar.ejb.ServiciosPrestadorLogic;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
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
public class ServiciosPrestadorLogicTest {
        private PodamFactory factory = new PodamFactoryImpl();
    @Inject
    private ServiciosPrestadorLogic logic;
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserTransaction utx;
    private List<ServicioEntity> dataServ = new ArrayList<>();
    private List<PrestadorEntity> dataPrestador = new ArrayList<>();
    private List<SolicitudEntity> dataSolicitud = new ArrayList<>();
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
//            clearData();
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


    private void insertData(){
        PrestadorEntity p = factory.manufacturePojo(PrestadorEntity.class);
        p.setServicios(dataServ);
        em.persist(p);
        dataPrestador.add(p);
        PrestadorEntity q = factory.manufacturePojo(PrestadorEntity.class);
        em.persist(q);
        dataPrestador.add(q);
        SolicitudEntity sol= factory.manufacturePojo(SolicitudEntity.class);
        sol.setServicios(dataServ);
        em.persist(sol);
        dataSolicitud.add(sol);
        ServicioEntity s = factory.manufacturePojo(ServicioEntity.class);
        s.setPrestador(p);
        s.setSolicitud(sol);
        em.persist(s);
        dataServ.add(s);
        
    }
    @Test
    public void replacePrestadorTest(){
        ServicioEntity s = logic.replacePrestador(dataPrestador.get(1).getId(),dataSolicitud.get(0).getId(), dataServ.get(0).getId());
       Assert.assertEquals(s.getPrestador().getId(),dataPrestador.get(1).getId());
    }
}