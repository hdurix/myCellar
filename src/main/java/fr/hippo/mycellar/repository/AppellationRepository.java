package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.Appellation;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Appellation entity.
 */
public interface AppellationRepository extends JpaRepository<Appellation,Long> {

}
