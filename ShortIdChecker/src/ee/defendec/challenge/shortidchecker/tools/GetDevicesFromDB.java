package ee.defendec.challenge.shortidchecker.tools;

import ee.defendec.challenge.shortidchecker.exceptions.FileReaderException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class GetDevicesFromDB {

    public static List<List<String>> getDeviceListFromDatabase(String fileLocation) {
        Path path = Paths.get(fileLocation);
        System.out.println(path);
        List<String> dataByLine = new ArrayList<>();
        List<List<String>> devices = new ArrayList<>();
        try (Stream<String> linesStream = Files.lines(path)) {
            linesStream.forEach(s -> dataByLine.addAll(Arrays.asList(s.split("\\\\n"))));
            for (String line : dataByLine) {
                devices.add(Arrays.asList(line.split(",")));
            }
        } catch (IOException e) {
            throw new FileReaderException("No such file", e);
        }
        return devices;
    }
}
