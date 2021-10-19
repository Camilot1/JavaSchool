package ru.camilot.object;

import lombok.Data;
import ru.camilot.annotation.ParseIndex;

@Data
public class Car {
    @ParseIndex(headerName = "car model")
    private String model;
    @ParseIndex(headerName = "car make")
    private String maker;
    @ParseIndex(headerName = "car model year")
    private String year;
    @ParseIndex(headerName = "color")
    private String color;

    public String getModel() {
        return model;
    }

    public String getMaker() {
        return maker;
    }

    public String getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "{Model=" + model + "; Maker=" +  maker + "; Year= " + year + "; Color=" + color + "}";
    }
}
