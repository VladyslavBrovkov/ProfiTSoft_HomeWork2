package homework.second.Task1;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    private static final Pattern NAME_PATTERN = Pattern.compile(" name[= ]*\"(.*?)\"");
    private static final Pattern SURNAME_PATTERN = Pattern.compile(" surname[= ]*\"(.*?)\"");

    public static void copyAndModifyFile(String fromFilePathName, String toFilePathName) throws IOException {
        try (Scanner sc = new Scanner(new File(fromFilePathName));
             Writer fw = new FileWriter(toFilePathName)) {
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine()).append(System.lineSeparator());
                if (!sb.toString().trim().endsWith("/>") && sc.hasNextLine()) {
                    continue;
                }
                fw.write(modifyName(sb));
                sb.setLength(0);
            }
        }
    }

    public static String modifyName(StringBuilder unmodifiedData) {
        Matcher surnameMatcher = SURNAME_PATTERN.matcher(unmodifiedData);
        Matcher nameMatcher = NAME_PATTERN.matcher(unmodifiedData);
        String modified = unmodifiedData.toString();
        while (surnameMatcher.find() && nameMatcher.find()) {
            String name = nameMatcher.group();
            String surname = surnameMatcher.group();
            String clearSurname = surname.substring(surname.indexOf("\"") + 1, surname.length() - 1);
            String modifiedName = name.substring(0, name.length() - 1) + " " + clearSurname + '"';
            modified = modified.replaceAll(surnameMatcher.group(), "").replaceAll(name, modifiedName);
        }
        return modified;
    }

}

