package fr.hippo.mycellar.web.rest.dto;

import fr.hippo.mycellar.domain.Bottle;
import fr.hippo.mycellar.domain.ColorEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Hippolyte on 20/06/2015.
 */
public class BottleDTO {
    private final Long id;
    private final String country;
    private final String appellation;
    private final String domain;
    private final String vineward;
    private final CategoryDTO category;
    private final Integer timeToWait;
    private final String color;
    private final Integer year;
    private final Float price;
    private final String user;
    private final Long numberStocked;
    private final Long numberDrinked;
    private final List<BottleLifeDTO> bottleLifes;

    public BottleDTO(Long id, String country, String appellation, String domain, String vineward, CategoryDTO category,
                     Integer timeToWait, String color, Integer year, Float price, String user, Long numberDrinked,
                     Long numberStocked, List<BottleLifeDTO> bottleLifes) {
        this.id = id;
        this.country = country;
        this.appellation = appellation;
        this.domain = domain;
        this.vineward = vineward;
        this.category = category;
        this.timeToWait = timeToWait;
        this.color = color;
        this.year = year;
        this.price = price;
        this.user = user;
        this.numberDrinked = numberDrinked;
        this.numberStocked = numberStocked;
        this.bottleLifes = bottleLifes;
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getAppellation() {
        return appellation;
    }

    public String getDomain() {
        return domain;
    }

    public String getVineward() {
        return vineward;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public Integer getTimeToWait() {
        return timeToWait;
    }

    public String getColor() {
        return color;
    }

    public Integer getYear() {
        return year;
    }

    public Float getPrice() {
        return price;
    }

    public String getUser() {
        return user;
    }

    public Long getNumberDrinked() {
        return numberDrinked;
    }

    public Long getNumberStocked() {
        return numberStocked;
    }

    public List<BottleLifeDTO> getBottleLifes() {
        return bottleLifes;
    }

    public static class Builder {
        private Long id;
        private String country;
        private String appellation;
        private String domain;
        private String vineward;
        private CategoryDTO category;
        private Integer timeToWait;
        private String color;
        private Integer year;
        private Float price;
        private String user;
        private Long numberDrinked;
        private Long numberStocked;
        private List<BottleLifeDTO> bottleLifes = new ArrayList<>();

        public Builder() {
        }

        public Builder reinit() {
            id = null;
            country = null;
            appellation = null;
            domain = null;
            vineward = null;
            category = null;
            timeToWait = null;
            color = null;
            year = null;
            price = null;
            user = null;
            numberDrinked = null;
            numberStocked = null;
            bottleLifes = new ArrayList<>();
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setAppellation(String appellation) {
            this.appellation = appellation;
            return this;
        }

        public Builder setDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder setVineward(String vineward) {
            this.vineward = vineward;
            return this;
        }

        public Builder setCategory(CategoryDTO category) {
            this.category = category;
            return this;
        }

        public Builder setTimeToWait(Integer timeToWait) {
            this.timeToWait = timeToWait;
            return this;
        }

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Builder setYear(Integer year) {
            this.year = year;
            return this;
        }

        public Builder setPrice(Float price) {
            this.price = price;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setBottleLifes(List<BottleLifeDTO> bottleLifes) {
            this.bottleLifes = bottleLifes;
            return this;
        }

        public Builder addBottleLife(BottleLifeDTO bottleLife) {
            bottleLifes.add(bottleLife);
            return this;
        }

        public BottleDTO build() {
            long numberDrinked = (long) bottleLifes.stream().map(BottleLifeDTO::getDrinkedDate).filter(Objects::nonNull).count();
            long numberStocked = bottleLifes.size() - numberDrinked;
            return new BottleDTO(id, country, appellation, domain, vineward, category, timeToWait, color, year, price,
                user, numberDrinked, numberStocked, bottleLifes);
        }
    }
}
