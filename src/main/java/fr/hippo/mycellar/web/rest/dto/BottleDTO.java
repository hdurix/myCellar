package fr.hippo.mycellar.web.rest.dto;

import fr.hippo.mycellar.domain.ColorEnum;

/**
 * Created by Hippolyte on 20/06/2015.
 */
public class BottleDTO {
    private final String country;
    private final String appellation;
    private final String domain;
    private final String vineward;
    private final String category;
    private final Integer timeToWait;
    private final String color;
    private final Integer year;
    private final Float price;
    private final String user;
    private final Long number;

    public BottleDTO(String country, String appellation, String domain, String vineward, String category,
                     Integer timeToWait, String color, Integer year, Float price, String user, Long number) {
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
        this.number = number;
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

    public String getCategory() {
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

    public Long getNumber() {
        return number;
    }

    public static class Builder {
        private String country;
        private String appellation;
        private String domain;
        private String vineward;
        private String category;
        private Integer timeToWait;
        private String color;
        private Integer year;
        private Float price;
        private String user;
        private Long number;

        public Builder() {
        }

        public Builder reinit() {
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
            number = null;
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

        public Builder setCategory(String category) {
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

        public Builder setNumber(Long number) {
            this.number = number;
            return this;
        }

        public BottleDTO build() {
            return new BottleDTO(country, appellation, domain, vineward, category, timeToWait, color, year, price, user, number);
        }
    }
}
