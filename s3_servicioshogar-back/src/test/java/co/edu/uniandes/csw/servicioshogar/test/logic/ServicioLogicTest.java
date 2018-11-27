/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;



import co.edu.uniandes.csw.servicioshogar.ejb.ServicioLogic;
import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
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
public class ServicioLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    @Inject
    private ServicioLogic logic;
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserTransaction utx;
    private List<ServicioEntity> data = new ArrayList<>();
    private List<ClienteEntity> dataCliente = new ArrayList<>();
    private List<SolicitudEntity>dataSolicitud = new ArrayList<>();
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioEntity.class.getPackage())
                .addPackage(ServicioLogic.class.getPackage())
                .addPackage(ServicioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
   @Before
   public void configTest(){
       try{
           utx.begin();
           clearData();
           insertData();
           utx.commit();
       }
       catch(Exception e){
           e.printStackTrace();
           try{
               utx.rollback();
           }
           catch(Exception e2){
               e2.printStackTrace();
           }
       }
   }
   private void clearData(){
       em.createQuery("delete from ServicioEntity").executeUpdate();
       em.createQuery("delete from SolicitudEntity").executeUpdate();
       em.createQuery("delete from ClienteEntity").executeUpdate();
   }
   private void insertData(){
       ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
       em.persist(cliente);
       dataCliente.add(cliente);
       SolicitudEntity solicitud = factory.manufacturePojo(SolicitudEntity.class);
       solicitud.setCliente(cliente);
       em.persist(solicitud);
       dataSolicitud.add(solicitud);
       for(int i = 0; i<3;i++){
           ServicioEntity serv = factory.manufacturePojo(ServicioEntity.class);
           serv.setSolicitud(solicitud);
           em.persist(serv);
           data.add(serv);
       }
      
   }
   @Test
   public void createPrestadorTest() throws BusinessLogicException{
       ServicioEntity nuevo = factory.manufacturePojo(ServicioEntity.class);
       ServicioEntity resultado = logic.createServicio(dataCliente.get(0).getId(),dataSolicitud.get(0).getId(), nuevo);
       Assert.assertNotNull(resultado);
       ServicioEntity enti = em.find(ServicioEntity.class, resultado.getId());
       Assert.assertEquals(enti.getId(), nuevo.getId());
       Assert.assertEquals(enti.getRequerimientos(), nuevo.getRequerimientos());
       Assert.assertEquals(enti.getDescripcion(), nuevo.getDescripcion());
   }
   @Test
   public void getServiciosTest(){
       List<ServicioEntity> listica = logic.getServicios(dataCliente.get(0).getId(),dataSolicitud.get(0).getId());
       Assert.assertEquals(data.size(), listica.size());
       for(ServicioEntity ent: listica){
           boolean encontro= false;
           for(ServicioEntity comp: data){
               if(ent.getId()==comp.getId()){
                   encontro=true;
                   break;
               }
           }
           Assert.assertTrue(encontro);
       }
   }
   @Test
   public void getServicioTest(){
       ServicioEntity enti = data.get(0);
       ServicioEntity nuevo = logic.getServicio(dataSolicitud.get(0).getId(),enti.getId());
       Assert.assertNotNull(nuevo);
       Assert.assertEquals(enti.getId(), nuevo.getId());
       Assert.assertEquals(enti.getRequerimientos(), nuevo.getRequerimientos());
       Assert.assertEquals(enti.getDescripcion(), nuevo.getDescripcion());
   }
 @Test
    public void updateServicioTest(){
        ServicioEntity enti = data.get(0);
        ServicioEntity nuevo = factory.manufacturePojo(ServicioEntity.class);
        nuevo.setId(enti.getId());
        logic.updateServicio(dataCliente.get(0).getId(), dataSolicitud.get(0).getId(),nuevo);
        ServicioEntity resultado = em.find(ServicioEntity.class, enti.getId());
        Assert.assertEquals(resultado.getId(), nuevo.getId());
        Assert.assertEquals(resultado.getRequerimientos(), nuevo.getRequerimientos());
        Assert.assertEquals(resultado.getDescripcion(), nuevo.getDescripcion()); 
    }
    @Test
    public void deleteServicioTest()throws BusinessLogicException{
        ServicioEntity enti = data.get(0);
        logic.deleteServicio(dataSolicitud.get(0).getId(),enti.getId());
        ServicioEntity res = em.find(ServicioEntity.class,enti.getId());
        Assert.assertNull(res);
    }
}