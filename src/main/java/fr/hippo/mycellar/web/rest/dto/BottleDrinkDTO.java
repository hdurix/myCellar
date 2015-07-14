package fr.hippo.mycellar.web.rest.dto;

import org.joda.time.DateTime;

/**
 * Created by Hippolyte on 20/06/2015.
 */
public class BottleDrinkDTO {

    private Long id;
    private DateTime drinkedDate;
    private int number;

    public BottleDrinkDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getDrinkedDate() {
        return drinkedDate;
    }

    public void setDrinkedDate(DateTime drinkedDate) {
        this.drinkedDate = drinkedDate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
