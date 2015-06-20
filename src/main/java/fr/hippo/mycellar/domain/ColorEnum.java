package fr.hippo.mycellar.domain;

/**
 * Created by Hippolyte on 20/06/2015.
 */
public enum  ColorEnum {
    RED("Rouge"), WHITE("Blanc"), YELLOW("Jaune"), PINK("Rosé");

    private final String name;

    private ColorEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
