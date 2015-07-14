package fr.hippo.mycellar.web.rest.dto;

/**
 * Created by Hippolyte on 20/06/2015.
 */
public class BottleCreateDTO {

    private Long id;
    private CategoryDTO category;
    private Integer year;
    private Float price;
    private int number;

    public BottleCreateDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
