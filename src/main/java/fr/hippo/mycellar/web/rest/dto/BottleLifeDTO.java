package fr.hippo.mycellar.web.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.hippo.mycellar.domain.util.CustomDateTimeDeserializer;
import fr.hippo.mycellar.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;

/**
 * Created by Hippolyte on 20/06/2015.
 */
public class BottleLifeDTO {

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime boughtDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime drinkedDate;

    public BottleLifeDTO(DateTime boughtDate, DateTime drinkedDate) {
        this.boughtDate = boughtDate;
        this.drinkedDate = drinkedDate;
    }

    public DateTime getBoughtDate() {
        return boughtDate;
    }

    public DateTime getDrinkedDate() {
        return drinkedDate;
    }

    public static class Builder {

        private DateTime boughtDate;
        private DateTime drinkedDate;

        public Builder() {
        }

        public Builder reinit() {
            boughtDate = null;
            drinkedDate = null;
            return this;
        }

        public Builder setBoughtDate(DateTime boughtDate) {
            this.boughtDate = boughtDate;
            return this;
        }

        public Builder setDrinkedDate(DateTime drinkedDate) {
            this.drinkedDate = drinkedDate;
            return this;
        }

        public BottleLifeDTO build() {
            return new BottleLifeDTO(boughtDate, drinkedDate);
        }
    }
}
