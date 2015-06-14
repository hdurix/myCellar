package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.Vineward;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Vineward entity.
 */
public interface VinewardRepository extends JpaRepository<Vineward,Long> {

}
