package fr.hippo.mycellar.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.hippo.mycellar.domain.util.CustomDateTimeDeserializer;
import fr.hippo.mycellar.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BottleLife.
 */
@Entity
@Table(name = "BOTTLELIFE")
public class BottleLife implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "bought_date")
    private DateTime boughtDate;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "drinked_date", nullable = false)
    private DateTime drinkedDate;

    @OneToOne
    @JsonIgnore
    private Bottle bottle;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getBoughtDate() {
        return boughtDate;
    }

    public void setBoughtDate(DateTime boughtDate) {
        this.boughtDate = boughtDate;
    }

    public DateTime getDrinkedDate() {
        return drinkedDate;
    }

    public void setDrinkedDate(DateTime drinkedDate) {
        this.drinkedDate = drinkedDate;
    }

    public Bottle getBottle() {
        return bottle;
    }

    public void setBottle(Bottle bottle) {
        this.bottle = bottle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BottleLife bottleLife = (BottleLife) o;

        if ( ! Objects.equals(id, bottleLife.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BottleLife{" +
                "id=" + id +
                ", boughtDate='" + boughtDate + "'" +
                ", drinkedDate='" + drinkedDate + "'" +
                '}';
    }
}
