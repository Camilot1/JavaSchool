package ru.camilot;

import ru.camilot.manager.ParseIndexManager;
import ru.camilot.object.Car;
import ru.camilot.object.CarMaker;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {

    private static final ParseIndexManager pim = new ParseIndexManager();


    public static void main(String[] args) throws IOException, URISyntaxException {

        List<String> dataList = IOManager.readFile("CAR_DATA.csv");

        String header = dataList.get(0);
        dataList.remove(0);

        pim.registerClass(header, Car.class);

        List<Car> carList = new ArrayList<>();
        for(String line: dataList) carList.add(pim.parseObject(line, Car.class));

        System.out.println("Задание №5:");
        ex5(carList);
        System.out.println("Записан файл: ex5.txt.\n");
        System.out.println("Задание №6:");
        ex6(carList);
        System.out.println("Записан файл: ex6.txt.\n");
        System.out.println("Задание №7:");
        ex7(carList);
        System.out.println("Записан файл: ex7.txt.\n");
        System.out.println("Задание №8:");
        ex8(carList);
        System.out.println("Записан файл: ex8.txt.\n");

    }

    /**
     * Реализация 5 пункта задания.
     * @param carList - список объектов Car.
     * @throws IOException .
     */
    private static void ex5(List<Car> carList) throws IOException {
        IOManager.writeFile(carList, "ex5.txt");
    }

    /**
     * Реализация 6 пункта задания.
     * @param carList - список объектов Car
     * @throws IOException .
     */
    private static void ex6(List<Car> carList) throws IOException {
        IOManager.writeFile(createCarColorMap(carList).entrySet(), "ex6.txt");
    }

    /**
     * Реализация 7 пункта задания.
     * @param carList - список объектов Car.
     * @throws IOException .
     */
    private static void ex7(List<Car> carList) throws IOException {
        Map<String, CarMaker> carMakerMap = createCarMakerMap(carList);

        List<CarMaker> carMakerList = convertToSortedCarMakerList(carMakerMap);

        IOManager.writeFile(carMakerList, "ex7.txt");
        System.out.println(carMakerList.stream().map(CarMaker::toString).collect(Collectors.joining()));
    }

    /**
     * Реализация 8 пункта задания.
     * @param carList - список объектов Car.
     * @throws IOException .
     */
    private static void ex8(List<Car> carList) throws IOException {
        Map<String, CarMaker> carMakerMap = createCarMakerMap(carList);

        carMakerMap = carMakerMap.entrySet()
                .stream()
                .filter(ent -> ent.getValue().getCarCount() > 2)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<CarMaker> carMakerList = convertToSortedCarMakerList(carMakerMap);
        IOManager.writeFile(carMakerList, "ex8.txt");

    }

    /**
     * Метод создаёт HashMap (ключ - цвет, значение: список объектов Car).
     * @param carList - список объектов Car.
     * @return HashMap (ключ - цвет, значение: список объектов Car).
     */
    private static Map<String, List<Car>> createCarColorMap(List<Car> carList) {
        Map<String, List<Car>> carColorMap = new HashMap<>();
        List<Car> carColorList;

        for(Car car: carList) {
            if (carColorMap.get(car.getColor()) == null) {
                carColorList = new ArrayList<>();
                carColorList.add(car);
                carColorMap.put(car.getColor(), carColorList);
            } else {
                carColorMap.get(car.getColor()).add(car);
            }
        }
        return carColorMap;
    }

    /**
     * Метод создаёт HashMap (ключ - производитель, значение: список объектов Car).
     * @param carList - список объектов Car.
     * @return HashMap (ключ - производитель, значение: список объектов Car).
     */
    private static Map<String, CarMaker> createCarMakerMap(List<Car> carList) {
        Map<String, CarMaker> carMakerMap = new HashMap<>();
        CarMaker carMaker;
        for(Car car: carList) {
            if (carMakerMap.get(car.getMaker()) == null) {
                carMaker = new CarMaker(car.getMaker(), car);
                carMakerMap.put(carMaker.getMaker(), carMaker);
            } else {
                carMakerMap.get(car.getMaker()).addCar(car);
            }
        }
        return carMakerMap;
    }

    /**
     * Метод конвертирует HashMap в отсортированный по ключу список объектов CarMaker.
     * @param carMakerMap - HashMap (ключ - производитель; значение -> CarMaker)
     * @return HashMap (ключ - производитель, значение: список объектов Car).
     */
    private static List<CarMaker> convertToSortedCarMakerList(Map<String, CarMaker> carMakerMap) {
        List<CarMaker> carMakerList = new ArrayList<>();
        for(String key: carMakerMap.keySet()
                .stream()
                .sorted()
                .collect(Collectors.toList())) {
            carMakerList.add(carMakerMap.get(key));
        }
        return carMakerList;
    }
}
