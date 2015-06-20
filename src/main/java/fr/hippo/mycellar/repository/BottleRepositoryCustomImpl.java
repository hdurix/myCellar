package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.ColorEnum;
import fr.hippo.mycellar.web.rest.dto.BottleDTO;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hippolyte on 20/06/2015.
 */
@Repository
public class BottleRepositoryCustomImpl implements BottleRepositoryCustom {

    @Inject
    EntityManager entityManager;

    @Override
    public List<BottleDTO> getAllDto() {

        String queryString =  "select co.name, a.name, d.name, v.name, c.name, b.year, c.color, b.price, u.login, count(*) "
            + "from Bottle b "
            + "left outer join b.category c "
            + "left outer join c.vineward v "
            + "left outer join v.domain d "
            + "left outer join d.appellation a "
            + "left outer join a.country co "
            + "left outer join b.bottleLifes bl "
            + "left outer join bl.user u "
            + "group by co.name, a.name, d.name, v.name, c.name, b.year, c.color, b.price, u.login";
        Query query = entityManager.createQuery(queryString);

        System.out.println("query = " + queryString);

        BottleDTO.Builder builder = new BottleDTO.Builder();

        List<Object[]> resultList = query.getResultList();

        return resultList.stream().map(line -> builder.reinit()
                .setCountry((String) line[0])
                .setAppellation((String) line[1])
                .setDomain((String) line[2])
                .setVineward((String) line[3])
                .setCategory((String) line[4])
                .setYear((Integer) line[5])
                .setColor(((ColorEnum) line[6]).getName())
                .setPrice((Float) line[7])
                .setUser((String) line[8])
                .setNumber((Long) line[9])
                .build()
        ).collect(Collectors.toList());

    }

}
