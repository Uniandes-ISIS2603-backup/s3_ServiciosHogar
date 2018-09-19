/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;
import co.edu.uniandes.csw.servicioshogar.entities.SolicitudEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "fecha": date,
 *      "direccion": string,
 *      "servicios":[{@link ServicioDTO}]
 *   }
 * </pre> Por ejemplo una editorial se representa asi:<br>
 *
 * <pre>
 *   {
 *      "fecha": "2000-08-20T00:00:00-05:00",
 *      "direccion": "Cale 22 No. 22-22",
 *      "servicios": [
 *          {
 *              "descripción": "Cambiar las cerraduras de tres puertas",
                "requerimientos": "CERRAJERIA",
 *              "solicitud":
 *              {
 *                  "fecha": "2000-08-20T00:00:00-05:00",
 *                  "direccion": "Cale 22 No. 22-22"
 *              }
 *          },
 *          {
 *              "descripción": "Desinstalar retrete antiguo y cambiar por uno nuevo",
                "requerimientos": "PLOMERIA",
 *              "solicitud":
 *              {
 *                  "fecha": "2000-08-20T00:00:00-05:00",
 *                  "direccion": "Cale 22 No. 22-22"
 *              }
 *              }
 *          }
 *      ]
 *   }
 * </pre>
 *
 * @author Steven Tarazona <ys.tarazona@uniandes.edu.co>
 */
public class SolicitudDetailDTO extends SolicitudDTO implements Serializable{
    // relación  cero o muchos servicios 
    private List<ServicioDTO> servicios;

    public SolicitudDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param solicitudEntity La entidad de la cual se construye el DTO
     */
    public SolicitudDetailDTO(SolicitudEntity solicitudEntity) {
        super(solicitudEntity);
        if (solicitudEntity.getServicios() != null) {
            servicios = new ArrayList<>();
            for (ServicioEntity entityServicio : solicitudEntity.getServicios()) {
                servicios.add(new ServicioDTO(entityServicio));
            }
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el solicitud.
     */
    @Override
    public SolicitudEntity toEntity() {
        SolicitudEntity solicitudEntity = super.toEntity();
        if (servicios != null) {
            List<ServicioEntity> serviciosEntity = new ArrayList<>();
            for (ServicioDTO dtoServicio : getServicios()) {
                serviciosEntity.add(dtoServicio.toEntity());
            }
            solicitudEntity.setServicios(serviciosEntity);
        }
        return solicitudEntity;
    }

    /**
     * Devuelve las reseñas asociadas a este solicitud
     *
     * @return Lista de DTOs de Reseñas
     */
    public List<ServicioDTO> getServicios() {
        return servicios;
    }

    /**
     * Modifica las reseñas de este solicitud.
     *
     * @param servicios Las nuevas reseñas
     */
    public void setServicios(List<ServicioDTO> servicios) {
        this.servicios = servicios;
    }
}
