package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.Bottle;
import fr.hippo.mycellar.domain.Category;
import fr.hippo.mycellar.domain.Vineward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bottle entity.
 */
public interface BottleRepository extends JpaRepository<Bottle, Long> {

    @Query(value = "from Bottle b " + "left join fetch b.category",
        countQuery = "select count(*) from Bottle b " + "left join b.category")
    Page<Bottle> findAllWithCategory(Pageable pageable);

    List<Bottle> findAllByCategory(Category category);

    Bottle findOneByCategoryAndYear(Category category, Integer year);
}
