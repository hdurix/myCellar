package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.Category;
import fr.hippo.mycellar.domain.Domain;
import fr.hippo.mycellar.domain.Vineward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Category entity.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "from Category c " + "left join fetch c.vineward",
        countQuery = "select count(*) from Category c " + "left join c.vineward")
    Page<Category> findAllWithVineward(Pageable pageable);

    List<Category> findAllByVineward(Vineward vineward);
}
