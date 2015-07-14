package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.Bottle;
import fr.hippo.mycellar.domain.Category;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bottle entity.
 */
public interface BottleRepository extends JpaRepository<Bottle,Long> {

    Bottle findOneByCategoryAndYear(Category category, Integer year);
}
