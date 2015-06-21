package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.Country;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Country entity.
 */
public interface CountryRepository extends JpaRepository<Country,Long> {

    @Query("select distinct c from Country c "
        + "left join fetch c.appellations a "
        + "left join fetch a.domains d "
        + "left join fetch d.vinewards v "
        + "left join fetch v.categorys ca "
        + "left join fetch ca.bottles")
    List<Country> findAllWithDependencies();
}
