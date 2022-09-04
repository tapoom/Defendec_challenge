package ee.defendec.challenge.shortidchecker.setup;

import java.nio.file.Paths;

public class GeneralSetup {

    public static final String DATEFORMAT = "E MMM dd HH:mm:ss z yyyy";

    private static final String EXTERNAL_DB_NAME = "externalDB";
    private static final String PATH_TO_REPO = Paths.get("").toAbsolutePath().toString();
    private static final String FILE_PATH_IN_REPO_MAC = "/ShortIdChecker/src/ee/defendec/" +
            "challenge/shortidchecker/externalstorage/";
    private static final String FILE_PATH_IN_REPO_WIN = "\\ShortIdChecker\\src\\ee\\defendec\\" +
            "challenge\\shortidchecker\\externalstorage\\";

    public static final String EXTERNAL_DB_LOCATION_WIN = PATH_TO_REPO + FILE_PATH_IN_REPO_WIN + EXTERNAL_DB_NAME;
    public static final String EXTERNAL_DB_LOCATION_MAC = PATH_TO_REPO + FILE_PATH_IN_REPO_MAC + EXTERNAL_DB_NAME;

    public static final String LOCAL_DB_LOCATION = "C:\\Users\\Tanel\\IdeaProjects" +
            "\\Defendec_challenge\\ShortIdChecker\\src\\ee\\defendec\\challenge\\shortidchecker" +
            "\\internalstorage\\localDB";
}
