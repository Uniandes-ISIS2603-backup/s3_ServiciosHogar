/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.resources;

import co.edu.uniandes.csw.servicioshogar.dtos.ClienteDTO;
import co.edu.uniandes.csw.servicioshogar.ejb.ClienteLogic;
import co.edu.uniandes.csw.servicioshogar.entities.ClienteEntity;
import co.edu.uniandes.csw.servicioshogar.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso 'Clientes'
 * @author Carlos Eduardo Robles
 */
@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResource 
{
    //------------------------------------------
    //-----------------Atributos----------------
    //------------------------------------------
    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());
  
    /**
     * Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    ClienteLogic clienteLogic;
    
    //------------------------------------------
    //------------------Metodos-----------------
    //------------------------------------------ 
    /**
     * Crea un nuevo cliente con la informacion que se recibe en el cuerpo de la
     * peticion y se regra un objeto identico con un id auto-generado por la BD.
     * @param cliente. {@link ClienteDTO} - El cliente que desea guardar.
     * @return JSON {@link ClienteDTO} - El cliente guardado con el id auto-generado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de la logica generado
     * cuando ya existe el cliente.
     */
    @POST
    public ClienteDTO crearCliente(ClienteDTO cliente) throws BusinessLogicException
    {
         LOGGER.log(Level.INFO, "ClienteResource crearCliente: input: {0}", cliente.toString());
         /*Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.*/
         ClienteEntity clienteEntity = cliente.toEntity();
         /*Invoca la logica para crear un cliente nuevo*/
         ClienteEntity nuevoClienteEntity = clienteLogic.crearCliente(clienteEntity);
         /*Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo*/
         ClienteDTO nuevoClienteDTO = new ClienteDTO(nuevoClienteEntity);
         LOGGER.log(Level.INFO, "ClienteResource crearCliente: output: {0}", nuevoClienteDTO.toString());
         return nuevoClienteDTO;
    }

    /**
     * Busca y devuelve todos los clientes existentes en la app.
     * @return JSONArray {@link ClienteDTO} - Los clientes encontrados en la app. Si no hay clientes
     * retorna vacio.
     */
    @GET
    public List<ClienteDTO> getClientes()
    {
        LOGGER.info("ClienteResource getClientes: input: void");
        List<ClienteDTO> listaClientes = listEntity2DetailDTO(clienteLogic.getClientes());
        LOGGER.log(Level.INFO, "ClienteResource getClientes: output: {0}", listaClientes.toString());
        return listaClientes;
    }
    
    /**
     * Busca el cliente con el id ingresado por parametro y lo devuelve.
     * @param clientesId. Id del cliente que se esta buscando.
     * @return JSON{@link ClienteDTO} - Cliente buscado. Exception en caso de no encontrarlo
     * @throws WebApplicationException  {@link WebApplicationExceptionMapper} - Error de logica generado
     * cuando no se encuentra el cliente
     */
    @GET
    @Path("{clientesId:\\d+}")
    public ClienteDTO getCliente(@PathParam("clientesId") Long clientesId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "ClienteResource getCliente: input: {0}", clientesId);
        ClienteEntity clienteEntity = clienteLogic.getCliente(clientesId);
        if (clienteEntity == null)
            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        
        ClienteDTO detailDTO = new ClienteDTO(clienteEntity);
        LOGGER.log(Level.INFO, "ClienteResource getCliente: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    /**
     * Actualiza/Modifica el cliente con el id recibido por parametro.
     * @param clientesId. Id del cliente que se desea actualizar.
     * @param cliente. {@link ClienteDTO} - El cliente que se desea guardar.
     * @return JSON{@link ClienteDTO} - El cliente guardado con modificaciones.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error en la logica generado
     * cuando no se encuentra el cliente a modificar
     */
    @PUT
    @Path("{clientesId:\\d+}")
    public ClienteDTO modificarCliente(@PathParam("clientesId") Long clientesId, ClienteDTO cliente) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "ClienteResource modificarCliente: input: id:{0} , cliente: {1}", new Object[]{clientesId, cliente.toString()});
        cliente.setId(clientesId);
        if (clienteLogic.getCliente(clientesId) == null)
            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        
        ClienteDTO detailDTO = new ClienteDTO(clienteLogic.modificarCliente(clientesId, cliente.toEntity()));
        LOGGER.log(Level.INFO, "ClienteResource modificarCliente: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    /**
     * Borra el cliente asociado con el id ingresado por parametro.
     * @param clientesId. Identificado del cliente a borrar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica generado
     * cuando no se puede eliminar el cliente.
     */
    @DELETE
    @Path("{clientesId:\\d+}")
    public void deleteCliente(@PathParam("clientesId") Long clientesId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ClienteResource deleteCliente: input: {0}", clientesId);
        if (clienteLogic.getCliente(clientesId) == null) 
            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        
        clienteLogic.deleteCliente(clientesId);
        LOGGER.info("ClienteResource deleteCliente: output: void");
    }
    
    /**
     * Conexión con el servicio de reseñas para un libro. {@link SolicitudResource}
     *
     * Este método conecta la ruta de /clientes con las rutas de /solicitudes que
     * dependen del libro, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param clientesId El ID del libro con respecto al cual se accede al
     * servicio.
     * @return El servicio de Reseñas para ese libro en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @Path("{clientesId: \\d+}/solicitudes")
    public Class<SolicitudResource> getSolicitudResource(@PathParam("clientesId") Long clientesId) {
        if (clienteLogic.getCliente(clientesId) == null) 
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/solicitudes no existe.", 404);
        
        return SolicitudResource.class;
    }
    
    /**
     * Convierte una lista de entidades a DTO
     * @param entityList. Corresponde a la lista de clientes de tipo Entity que se convertira en DTO.
     * @return lista de editoriales en forma DTO (json).
     */
    private List<ClienteDTO> listEntity2DetailDTO(List<ClienteEntity> entityList) {
        List<ClienteDTO> list = new ArrayList<>();
        for (ClienteEntity entity : entityList) 
        {
            list.add(new ClienteDTO(entity));
        }
        return list;
    }
}
