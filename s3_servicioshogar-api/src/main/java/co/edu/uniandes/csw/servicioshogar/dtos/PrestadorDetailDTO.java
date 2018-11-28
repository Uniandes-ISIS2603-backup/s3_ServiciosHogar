/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicioshogar.dtos;

import co.edu.uniandes.csw.servicioshogar.entities.HabilidadEntity;
import co.edu.uniandes.csw.servicioshogar.entities.PrestadorEntity;
import co.edu.uniandes.csw.servicioshogar.entities.ServicioEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link PrestadorDTO} para manejar las relaciones entre los
 * PrestadorDTO y otros DTOs. Para conocer el contenido de la un Prestador vaya a la
 * documentacion de {@link PrestadorDTO}
 * <p>
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "cedula": number,
 *      "habilidades": [{@link HabilidadDTO}]
 *   }
 * </pre> Por ejemplo un prestador se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 123,
 *      "nombre": "Maria Ocampo",
 *      "cedula": 1007784099,
 *      "habilidades": [
 *          {
 *              "id": 123,
 *              "tipo": "CERRAJERIA",
 *              "descripcion": "Duplicado de llaves",
 *              "prestador":
 *              {
 *                  "id": 123,
 *                  "nombre": "Maria Ocampo",
 *                   "cedula": 1007784099
 *              }
 *          },
 *          {
 *              "id": 124,
 *              "tipo": "CARPINTERIA",
 *              "descripcion": "Fabricaicon de puertas y muebles"
 *              "prestador":
 *              {
 *                 "id": 123,
 *                 "nombre": "Maria Ocampo",
 *                 "cedula": 1007784099
 *              }
 *          }
 *      ]
 *   }
 *
 * </pre>
 *
 * @author Maria Ocampo
 */
public class PrestadorDetailDTO extends PrestadorDTO implements Serializable {

    /**
     * Lista de habilidades asociadas a un prestador.
     */
    private List<HabilidadDTO> habilities;
    
    /**
     * 
     */
    private List<ServicioDTO> servicios;
    /**
     * Constructor por defecto.
     */
    public PrestadorDetailDTO() {

    }

    /**
     * Constructor para transformar un Entity en un DTO
     *
     * @param prestadorEntity La entidad a la cual se le construye el DTO
     */
    public PrestadorDetailDTO(PrestadorEntity prestadorEntity) {
        super(prestadorEntity);
        if (prestadorEntity != null && prestadorEntity.getHabilidades() != null) {
            habilities = new ArrayList<>();
            for (HabilidadEntity entityHability : prestadorEntity.getHabilidades()) {
                habilities.add(new HabilidadDTO(entityHability));
            }

        }
        if (prestadorEntity != null && prestadorEntity.getServicios() != null) {
            servicios = new ArrayList<>();
            for (ServicioEntity entityHability : prestadorEntity.getServicios()) {
                servicios.add(new ServicioDTO(entityHability));
            }

        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el prestador
     */
    @Override
    public PrestadorEntity toEntity() {
        PrestadorEntity prestadorEntity = super.toEntity();
        if (habilities != null) {
            List<HabilidadEntity> habilitiesEntity = new ArrayList<>();
            for (HabilidadDTO hability : habilities) {
                habilitiesEntity.add(hability.toEntity());
            }

            prestadorEntity.setHabilidades(habilitiesEntity);
        }
        if (servicios != null) {
            List<ServicioEntity> habilitiesEntity = new ArrayList<>();
            for (ServicioDTO hability : servicios) {
                habilitiesEntity.add(hability.toEntity());
            }

            prestadorEntity.setServicios(habilitiesEntity);
        }
        return prestadorEntity;
    }

    /**
     * Devuelve las habilidades del prestador
     *
     * @return Habilidades
     */
    public List<HabilidadDTO> getHabilities() {
        return habilities;
    }

    /**
     * Modifica las habilidades del prestador
     *
     * @param habilities Nuevas habilidades
     */
    public void setHabilities(List<HabilidadDTO> habilities) {
        this.habilities = habilities;
    }

    public List<ServicioDTO> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioDTO> servicios) {
        this.servicios = servicios;
    }
    
    
}
