/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.logic;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.ejb.ServicioLogic;
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
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
@RunWith(Arquillian.class)
public class ServicioLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ServicioLogic servicioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ServicioEntity> data = new ArrayList<ServicioEntity>();

    private List<SolicitudEntity> dataSolicitud = new ArrayList<SolicitudEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioEntity.class.getPackage())
                .addPackage(ServicioLogic.class.getPackage())
                .addPackage(ServicioPersistence.class.getPackage())
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
    private void clearData() {
        em.createQuery("delete from ServicioEntity").executeUpdate();
        em.createQuery("delete from SolicitudEntity").executeUpdate();
        em.createQuery("delete from EditorialEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            entity.setSolicitud(dataSolicitud.get(1));
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Servicio.
     *
     * @throws co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException
     */
    @Test
    public void createServicioTest() throws BusinessLogicException {
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        newEntity.setSolicitud(dataSolicitud.get(1));
        ServicioEntity result = servicioLogic.createServicio(dataSolicitud.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        ServicioEntity entity = em.find(ServicioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getRequerimientos(), entity.getRequerimientos());
    }

    /**
     * Prueba para consultar la lista de Servicios.
     *
     * @throws co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException
     */
    @Test
    public void getServiciosTest() throws BusinessLogicException {
        List<ServicioEntity> list = servicioLogic.getServicios(dataSolicitud.get(1).getId());
        Assert.assertEquals(data.size(), list.size());
        for (ServicioEntity entity : list) {
            boolean found = false;
            for (ServicioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Servicio.
     */
    @Test
    public void getServicioTest() {
        ServicioEntity entity = data.get(0);
        ServicioEntity resultEntity = servicioLogic.getServicio(dataSolicitud.get(1).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getRequerimientos(), resultEntity.getRequerimientos());
    }

    /**
     * Prueba para actualizar un Servicio.
     */
    @Test
    public void updateServicioTest() {
        ServicioEntity entity = data.get(0);
        ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);

        pojoEntity.setId(entity.getId());

        servicioLogic.updateServicio(dataSolicitud.get(1).getId(), pojoEntity);

        ServicioEntity resp = em.find(ServicioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getRequerimientos(), resp.getRequerimientos());
    }

    /**
     * Prueba para eliminar un Servicio.
     *
     * @throws co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException
     */
    @Test
    public void deleteServicioTest() throws BusinessLogicException {
        ServicioEntity entity = data.get(0);
        servicioLogic.deleteServicio(dataSolicitud.get(1).getId(), entity.getId());
        ServicioEntity deleted = em.find(ServicioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminarle un servicio a un solicitud del cual no pertenece.
     *
     * @throws co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteServicioConSolicitudNoAsociadoTest() throws BusinessLogicException {
        ServicioEntity entity = data.get(0);
        servicioLogic.deleteServicio(dataSolicitud.get(0).getId(), entity.getId());
    }
}
