/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.test.persistence;

import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import co.edu.uniandes.csw.servicioshogar.persistence.SolicitudPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.TestCase;
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
public class SolicitudPersistenceTest{
    @Inject
    private SolicitudPersistence solicitudPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<SolicitudEntity> data = new ArrayList<SolicitudEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SolicitudEntity.class.getPackage())
                .addPackage(SolicitudPersistence.class.getPackage())
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
        em.createQuery("delete from SolicitudEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            SolicitudEntity entity = factory.manufacturePojo(SolicitudEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Solicitud.
     */
    @Test
    public void createSolicitudTest() {
        PodamFactory factory = new PodamFactoryImpl();
        SolicitudEntity newEntity = factory.manufacturePojo(SolicitudEntity.class);
        SolicitudEntity result = solicitudPersistence.create(newEntity);

        Assert.assertNotNull(result);

        SolicitudEntity entity = em.find(SolicitudEntity.class, result.getId());

        Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(newEntity.getFecha(), entity.getFecha());
    }

    /**
     * Prueba para consultar la lista de Solicitudes.
     */
    @Test
    public void getSolicitudesTest() {
        List<SolicitudEntity> list = solicitudPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (SolicitudEntity ent : list) {
            boolean found = false;
            for (SolicitudEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Solicitud.
     */
    @Test
    public void getSolicitudTest() {
        SolicitudEntity entity = data.get(0);
        SolicitudEntity newEntity = solicitudPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getDireccion(), newEntity.getDireccion());
        Assert.assertEquals(entity.getFecha(), newEntity.getFecha());;
    }

    /**
     * Prueba para eliminar un Solicitud.
     */
    @Test
    public void deleteSolicitudTest() {
        SolicitudEntity entity = data.get(0);
        solicitudPersistence.delete(entity.getId());
        SolicitudEntity deleted = em.find(SolicitudEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Solicitud.
     */
    @Test
    public void updateSolicitudTest() {
        SolicitudEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        SolicitudEntity newEntity = factory.manufacturePojo(SolicitudEntity.class);

        newEntity.setId(entity.getId());

        solicitudPersistence.update(newEntity);

        SolicitudEntity resp = em.find(SolicitudEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getDireccion(), resp.getDireccion());
        Assert.assertEquals(newEntity.getFecha(), resp.getFecha());
    }
}
