package fr.hippo.mycellar.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Bottle.
 */
@Entity
@Table(name = "BOTTLE")
public class Bottle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "price")
    private Long price;

    @Column(name = "image")
    private String image;

    @ManyToOne
    private Category category;

    @OneToOne(mappedBy = "bottle")
    private BottleLife bottleLife;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BottleLife getBottleLife() {
        return bottleLife;
    }

    public void setBottleLife(BottleLife bottleLife) {
        this.bottleLife = bottleLife;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bottle bottle = (Bottle) o;

        if ( ! Objects.equals(id, bottle.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bottle{" +
                "id=" + id +
                ", year='" + year + "'" +
                ", price='" + price + "'" +
                ", image='" + image + "'" +
                '}';
    }
}
