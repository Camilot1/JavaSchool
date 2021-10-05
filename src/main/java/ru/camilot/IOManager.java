package ru.camilot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class IOManager {

    /**
     * Метод считывания списка массивов строк из файла "CAR_DATA.csv".
     * @return Список массивов строк
     * @throws URISyntaxException .
     * @throws IOException .
     */
    public static List<String[]> readFile(String filePath) throws URISyntaxException, IOException {
        URL resource = IOManager.class.getClassLoader().getResource(filePath);
        Path path = Paths.get(resource.toURI());

        return Files.lines(path, StandardCharsets.UTF_8)
                .filter(str -> !str.equals("car model,car make,car model year,color"))
                .map(str -> str.replace(",,", ",Не определено,"))
                .map(str -> str.split(","))
                .collect(Collectors.toList());
    }

    /**
     * Метод записи в файл
     * @param iterable записываемые данные
     * @param filename имя файла
     * @throws IOException .
     */
    public static void writeFile(Iterable<?> iterable, String filename) throws IOException {
        Path path = Paths.get("")
                .toAbsolutePath()
                .resolve("results")
                .resolve(filename);
        Files.deleteIfExists(path);

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Object elem : iterable) {
                bw.write(elem.toString());
                bw.newLine();
            }
        }
    }
}
