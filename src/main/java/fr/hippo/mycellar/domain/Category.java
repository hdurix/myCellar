package fr.hippo.mycellar.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "time_to_wait")
    private Integer timeToWait;

    @ManyToOne
    private Vineward vineward;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Bottle> bottles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getTimeToWait() {
        return timeToWait;
    }

    public void setTimeToWait(Integer timeToWait) {
        this.timeToWait = timeToWait;
    }

    public Vineward getVineward() {
        return vineward;
    }

    public void setVineward(Vineward vineward) {
        this.vineward = vineward;
    }

    public Set<Bottle> getBottles() {
        return bottles;
    }

    public void setBottles(Set<Bottle> bottles) {
        this.bottles = bottles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Category category = (Category) o;

        if ( ! Objects.equals(id, category.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", color='" + color + "'" +
                ", timeToWait='" + timeToWait + "'" +
                '}';
    }
}
