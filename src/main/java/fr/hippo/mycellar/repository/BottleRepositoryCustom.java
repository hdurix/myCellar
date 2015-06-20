package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.web.rest.dto.BottleDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Bottle entity.
 */
public interface BottleRepositoryCustom {

    public List<BottleDTO> getAllDto();

}
