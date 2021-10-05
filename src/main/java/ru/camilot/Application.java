package ru.camilot;

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

    public static void main(String[] args) throws IOException, URISyntaxException {

        List<String[]> dataList = IOManager.readFile("CAR_DATA.csv");

        System.out.println("Задание №5:");
        ex5(dataList);
        System.out.println("Записан файл: ex5.txt.\n");
        System.out.println("Задание №6:");
        ex6(dataList);
        System.out.println("Записан файл: ex6.txt.\n");
        System.out.println("Задание №7:");
        ex7(dataList);
        System.out.println("Записан файл: ex7.txt.\n");
        System.out.println("Задание №8:");
        ex8(dataList);
        System.out.println("Записан файл: ex8.txt.\n");

    }

    /**
     * Реализация 5 пункта задания.
     * @param dataList - список строчных массивов из входного файла со значениями полей Car.
     * @throws IOException .
     */
    private static void ex5(List<String[]> dataList) throws IOException {
        IOManager.writeFile(createCarList(dataList), "ex5.txt");
    }

    /**
     * Реализация 6 пункта задания.
     * @param dataList - список строчных массивов из входного файла со значениями полей Car.
     * @throws IOException .
     */
    private static void ex6(List<String[]> dataList) throws IOException {
        IOManager.writeFile(createCarColorMap(dataList).entrySet(), "ex6.txt");
    }

    /**
     * Реализация 7 пункта задания.
     * @param dataList - список строчных массивов из входного файла со значениями полей Car.
     * @throws IOException .
     */
    private static void ex7(List<String[]> dataList) throws IOException {
        Map<String, CarMaker> carMakerMap = createCarMakerMap(dataList);

        List<CarMaker> carMakerList = convertToSortedCarMakerList(carMakerMap);

        IOManager.writeFile(carMakerList, "ex7.txt");
        System.out.println(carMakerList.stream().map(CarMaker::toString).collect(Collectors.joining()));
    }

    /**
     * Реализация 8 пункта задания.
     * @param dataList - список строчных массивов из входного файла со значениями полей Car.
     * @throws IOException .
     */
    private static void ex8(List<String[]> dataList) throws IOException {
        Map<String, CarMaker> carMakerMap = createCarMakerMap(dataList);

        carMakerMap = carMakerMap.entrySet()
                .stream()
                .filter(ent -> ent.getValue().getCarCount() > 2)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<CarMaker> carMakerList = convertToSortedCarMakerList(carMakerMap);
        IOManager.writeFile(carMakerList, "ex8.txt");

    }

    /**
     * Метод создаёт список объектов Car из списка с данными полей.
     * @param dataList - список строчных массивов из входного файла со значениями полей Car.
     * @return список объектов Car.
     */
    private static List<Car> createCarList(List<String[]> dataList) {
        List<Car> carList = new ArrayList<>();
        for(String[] data: dataList) carList.add(new Car(data));
        return carList;
    }

    /**
     * Метод создаёт HashMap (ключ - цвет, значение: список объектов Car).
     * @param dataList - список строчных массивов из входного файла со значениями полей Car.
     * @return HashMap (ключ - цвет, значение: список объектов Car).
     */
    private static Map<String, List<Car>> createCarColorMap(List<String[]> dataList) {
        Map<String, List<Car>> carColorMap = new HashMap<>();

        Car car;
        List<Car> carList;
        for(String[] data: dataList) {
            car = new Car(data);
            if (carColorMap.get(car.getColor()) == null) {
                carList = new ArrayList<>();
                carList.add(car);
                carColorMap.put(car.getColor(), carList);
            } else {
                carColorMap.get(car.getColor()).add(car);
            }
        }
        return carColorMap;
    }

    /**
     * Метод создаёт HashMap (ключ - производитель, значение: список объектов Car).
     * @param dataList - список строчных массивов из входного файла со значениями полей Car.
     * @return HashMap (ключ - производитель, значение: список объектов Car).
     */
    private static Map<String, CarMaker> createCarMakerMap(List<String[]> dataList) {
        Map<String, CarMaker> carMakerMap = new HashMap<>();
        Car car;
        CarMaker carMaker;
        for(String[] data: dataList) {
            car = new Car(data);
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
