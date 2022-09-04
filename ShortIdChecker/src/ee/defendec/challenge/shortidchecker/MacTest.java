package ee.defendec.challenge.shortidchecker;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MacTest {
    public static void main(String[] args) {
        System.out.println("wat");
        int i=0;
        String filename="externalDB.txt";
        Path pathToFile = Paths.get(filename);
        System.out.println(pathToFile.toAbsolutePath());

    }
}
