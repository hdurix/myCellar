package fr.hippo.mycellar.service;

import fr.hippo.mycellar.domain.Bottle;
import fr.hippo.mycellar.domain.BottleLife;
import fr.hippo.mycellar.domain.Category;
import fr.hippo.mycellar.domain.User;
import fr.hippo.mycellar.repository.BottleLifeRepository;
import fr.hippo.mycellar.repository.BottleRepository;
import fr.hippo.mycellar.repository.CategoryRepository;
import fr.hippo.mycellar.repository.UserRepository;
import fr.hippo.mycellar.security.SecurityUtils;
import fr.hippo.mycellar.web.rest.dto.BottleCreateDTO;
import fr.hippo.mycellar.web.rest.dto.BottleDrinkDTO;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by Hippolyte on 11/07/2015.
 */
@Service
public class BottleService {

    @Inject
    BottleRepository bottleRepository;

    @Inject
    BottleLifeRepository bottleLifeRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Transactional
    public Bottle createFromDto(BottleCreateDTO bottleDTO) {

        Category category = categoryRepository.findOne(bottleDTO.getCategory().getId());

        Bottle bottle = bottleRepository.findOneByCategoryAndYear(category, bottleDTO.getYear());

        if (bottle == null) {
            bottle = new Bottle();
            bottle.setYear(bottleDTO.getYear());
            category.addBottle(bottle);
        }

        bottle.setPrice(bottleDTO.getPrice());

        DateTime boughtDate = new DateTime();
        String currentLogin = SecurityUtils.getCurrentLogin();

        User user = userRepository.findOneByLogin(currentLogin).orElse(null);

        for (int i = 0; i < bottleDTO.getNumber(); i++) {
            BottleLife bottleLife = new BottleLife(user, boughtDate);
            BottleLife createdBottleLife = bottleLifeRepository.save(bottleLife);
            bottle.addBottleLife(createdBottleLife);
        }

        return bottleRepository.save(bottle);
    }

    @Transactional
    public void drinkBottle(BottleDrinkDTO bottleDTO) {
        Bottle bottle = bottleRepository.findOne(bottleDTO.getId());

        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin()).orElse(null);

        for (int i = 0; i < bottleDTO.getNumber(); i++) {
            Optional<BottleLife> first = bottle.getBottleLifes()
                .stream()
                .filter(bl-> bl.getUser() == user)
                .filter(bl -> bl.getDrinkedDate() == null).findFirst();
            if (first.isPresent()) {
                first.get().setDrinkedDate(bottleDTO.getDrinkedDate());
                bottleLifeRepository.save(first.get());
            }
        }

    }
}
