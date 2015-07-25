package fr.hippo.mycellar.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A user.
 */
@Entity
@Table(name = "FRIENDSHIP")
public class Friendship extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User adder;

    @ManyToOne
    private User addeed;

    @Column
    private Boolean accepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAdder() {
        return adder;
    }

    public void setAdder(User adder) {
        this.adder = adder;
    }

    public User getAddeed() {
        return addeed;
    }

    public void setAddeed(User addeed) {
        this.addeed = addeed;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Friendship user = (Friendship) o;

        if (!id.equals(user.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Friendship{" +
            "id='" + id + '\'' +
            ", accepted='" + accepted + '\'' +
            ", adder='" + (adder == null ? null : adder.getLogin()) + '\'' +
            ", addeed='" + (addeed == null ? null : addeed.getLogin()) + '\'' +
            "}";
    }
}
