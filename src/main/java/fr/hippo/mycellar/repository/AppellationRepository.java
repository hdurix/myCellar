package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.Appellation;
import fr.hippo.mycellar.domain.Country;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Appellation entity.
 */
public interface AppellationRepository extends JpaRepository<Appellation,Long> {

    @Query("from Appellation a " + "left join fetch a.country")
    List<Appellation> findAllWithCountry();

    List<Appellation> findAllByCountry(Country country);

}
