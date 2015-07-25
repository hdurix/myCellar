package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.Appellation;
import fr.hippo.mycellar.domain.Domain;
import fr.hippo.mycellar.domain.Vineward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Vineward entity.
 */
public interface VinewardRepository extends JpaRepository<Vineward, Long> {

    @Query(value = "from Vineward v " + "left join fetch v.domain",
        countQuery = "select count(*) from Vineward v " + "left join v.domain")
    Page<Vineward> findAllWithDomain(Pageable pageable);

    List<Vineward> findAllByDomain(Domain domain);

}
