package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.Bottle;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bottle entity.
 */
public interface BottleRepository extends JpaRepository<Bottle,Long> {

}
