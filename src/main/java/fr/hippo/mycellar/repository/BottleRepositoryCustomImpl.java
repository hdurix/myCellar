package fr.hippo.mycellar.repository;

import fr.hippo.mycellar.domain.ColorEnum;
import fr.hippo.mycellar.web.rest.dto.BottleDTO;
import fr.hippo.mycellar.web.rest.dto.BottleLifeDTO;
import fr.hippo.mycellar.web.rest.dto.CategoryDTO;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        String queryString = "select co.name, a.name, d.name, v.name, c.id, c.name, b.year, c.color, b.price, " +
            "u.login, b.id, bl.boughtDate, bl.drinkedDate "
            + "from Bottle b "
            + "left outer join b.category c "
            + "left outer join c.vineward v "
            + "left outer join v.domain d "
            + "left outer join d.appellation a "
            + "left outer join a.country co "
            + "left outer join b.bottleLifes bl "
            + "left outer join bl.user u "
            + "order by b.id, bl.user";
        Query query = entityManager.createQuery(queryString);

        System.out.println("query = " + queryString);

        BottleDTO.Builder builder = new BottleDTO.Builder();

        List<Object[]> resultList = query.getResultList();

        if(resultList.isEmpty()) {
            return null;
        }

        List<BottleDTO> bottles = new ArrayList<>();

        Long tmpBottleId = null;
        String tmpUser = null;

        for (Object[] line : resultList) {

            if (tmpBottleId == null) {
                tmpBottleId = (Long) line[10];

                builder.reinit()
                    .setCountry((String) line[0])
                    .setAppellation((String) line[1])
                    .setDomain((String) line[2])
                    .setVineward((String) line[3])
                    .setCategory(new CategoryDTO((Long) line[4], (String) line[5]))
                    .setYear((Integer) line[6])
                    .setColor(((ColorEnum) line[7]).getName())
                    .setPrice((Float) line[8])
                    .setUser((String) line[9]);

            } else if (tmpBottleId != line[10] || tmpUser != line[9]) {
                //bottles
                tmpBottleId = (Long) line[10];
                tmpUser = (String) line[9];

                bottles.add(builder.build());
                builder.reinit()
                    .setId((Long) line[10])
                    .setCountry((String) line[0])
                    .setAppellation((String) line[1])
                    .setDomain((String) line[2])
                    .setVineward((String) line[3])
                    .setCategory(new CategoryDTO((Long) line[4], (String) line[5]))
                    .setYear((Integer) line[6])
                    .setColor(((ColorEnum) line[7]).getName())
                    .setPrice((Float) line[8])
                    .setUser((String) line[9]);
            }

            builder.addBottleLife(new BottleLifeDTO((DateTime) line[11], (DateTime) line[12]));
        }

        bottles.add(builder.build());

        System.out.println("bottles = " + bottles);

        return bottles;

    }

}
