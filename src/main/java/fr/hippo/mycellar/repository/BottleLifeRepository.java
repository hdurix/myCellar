package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.BottleLife;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BottleLife entity.
 */
public interface BottleLifeRepository extends JpaRepository<BottleLife,Long> {

}
