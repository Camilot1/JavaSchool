package ru.camilot.object;

import java.util.ArrayList;
import java.util.List;

public class CarMaker {
    private String maker;
    private final List<Car> carList;

    public CarMaker() {
        maker = "Undefined";
        carList = new ArrayList<>();
    }

    public CarMaker(String maker) {
        this();
        setMaker(maker);
    }

    public CarMaker(String maker, Car car) {
        this();
        setMaker(maker);
        addCar(car);
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        if (maker == null) this.maker = "Undefined";
        else if (maker.length() > 0) this.maker = maker;
        else this.maker = "Undefined";
    }

    public void addCar(Car car) {
        carList.add(car);
    }

    public int getCarCount() {
        return carList.size();
    }

    @Override
    public String toString() {
        return "{Maker=" + maker + "; Cars={" + carList + "}}";
    }



}
