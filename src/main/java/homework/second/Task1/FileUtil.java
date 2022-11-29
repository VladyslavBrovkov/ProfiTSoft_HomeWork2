package homework.second.Task1;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Utility class for copying files with persons' data and merging name and surname fields
 * with input data format saving
*/

public class FileUtil {
    private static final Pattern NAME_PATTERN = Pattern.compile(" name[= ]*\"(.*?)\"");
    private static final Pattern SURNAME_PATTERN = Pattern.compile(" surname[= ]*\"(.*?)\"");


    /**
     * copy data from input file to output file without full file uploading to memory
     * @param fromFilePathName - input File's path
     * @param toFilePathName - output File's path
     */
    public static void copyAndModifyFile(String fromFilePathName, String toFilePathName) throws IOException {
        try (Scanner sc = new Scanner(new File(fromFilePathName)).useDelimiter("");
             Writer fw = new FileWriter(toFilePathName)) {
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.next());
                if (!sb.toString().contains("/>") && sc.hasNext()) {
                    continue;
                }
                fw.write(modifyName(sb));
                sb.setLength(0);
            }
        }
    }


    /**
     * modify input data with separate name and surname field to one field with merging data
     * @param unmodifiedData - one person's input data
     * @return String value of modified person's data
     */
    public static String modifyName(StringBuilder unmodifiedData) {
        Matcher surnameMatcher = SURNAME_PATTERN.matcher(unmodifiedData);
        Matcher nameMatcher = NAME_PATTERN.matcher(unmodifiedData);
        String modifiedData = unmodifiedData.toString();
        while (surnameMatcher.find() && nameMatcher.find()) {
            String name = nameMatcher.group();
            String surname = surnameMatcher.group();
            String clearSurname = surname.substring(surname.indexOf("\"") + 1, surname.length() - 1);
            String modifiedName = name.substring(0, name.length() - 1) + " " + clearSurname + '"';
            modifiedData = modifiedData.replaceAll(surnameMatcher.group(), "").replaceAll(name, modifiedName);
        }
        return modifiedData;
    }

}

