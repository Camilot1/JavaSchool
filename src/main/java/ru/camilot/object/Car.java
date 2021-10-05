package ru.camilot.object;

public class Car {
    private String model;
    private String maker;
    private int year;
    private String color;

    public Car() {
        model = "Undefined";
        maker = "Undefined";
        year = 0;
        color = "Undefined";

    }

    public Car(String[] data) {
        this();
        if (data.length == 4) {
            setModel(data[0]);
            setMaker(data[1]);
            setYear(data[2]);
            setColor(data[3]);
        }
    }
    public Car(String model, String maker, String year, String color) {
        this(model, maker, 0, color);
        setYear(year);
    }
    public Car(String model, String maker, int year, String color) {
        setModel(model);
        setMaker(maker);
        setYear(year);
        setColor(color);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model.length() > 0) this.model = model;
        else this.model = "Undefined";
    }

    public int getYear() {
        return year;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        if (maker.length() > 0) this.maker = maker;
        else this.maker = "Undefined";
    }

    public void setYear(String year) {
        try {
            setYear(Integer.parseInt(year));
        } catch (Exception e) {
            this.year = 0;
        }

    }
    public void setYear(int year) {
        if (year > 0) this.year = year;
        else this.year = 0;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color.length() > 0) this.color = color;
        else this.color = "Undefined";
    }

    @Override
    public String toString() {
        return "{Model=" + model + "; Maker=" +  maker + "; Year= " + year + "; Color=" + color + "}";
    }
}
