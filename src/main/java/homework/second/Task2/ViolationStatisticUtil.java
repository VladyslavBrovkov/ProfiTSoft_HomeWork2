package homework.second.Task2;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import homework.second.Task2.model.Violation;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Utility class for
 */
public class ViolationStatisticUtil {


    /**
     * method for calculating and writing Statistic to file without uploading whole files to memory
     * @param folderIn - folder's path with all violation files in
     * @param fileOut -  statistic file's path
     */
    public static void writeFineStatisticToFile(File folderIn, File fileOut) throws IOException, NullPointerException {
        List<File> fileList = Arrays.stream(Objects.requireNonNull(folderIn.listFiles()))
                .filter(f -> f.isFile() && f.getName().endsWith("_Violations.json"))
                .collect(toList());
        if (fileList.isEmpty()) {
            throw new NullPointerException("No files with _Violations.json suffix");
        }
        XmlMapper xmlMapper = JacksonXmlMapper.getXmlMapper();
        try (Writer writer = new FileWriter(fileOut)) {
            writer.write(xmlMapper.writer().withRootName("TotalFine")
                    .writeValueAsString(calculateTotalFine(fileList)));
        }
    }

    /**
     * method for calculating total Statistic
     * @param fileList - list of violation files
     */
    public static List<Violation> calculateTotalFine(List<File> fileList) throws IOException {
        Map<String, BigDecimal> allFilesResult = new HashMap<>();
        for (File file : fileList) {
            var oneFileResult = calculateSingleJsonFile(file);
            oneFileResult.forEach((key, val) -> allFilesResult.merge(key, val, BigDecimal::add));
        }
        return allFilesResult.entrySet().stream()
                .map((m) -> new Violation(m.getKey(), m.getValue()))
                .sorted(Comparator.comparing(Violation::getFineAmount).reversed())
                .collect(toList());
    }

    /**
     * method for parsing and calculating statistic from one File
     * @param file - one JSON file with violation data
     */
    public static Map<String, BigDecimal> calculateSingleJsonFile(File file) throws IOException {
        Map<String, BigDecimal> oneFileResult = new HashMap<>();
        JsonFactory jsonFactory = new JsonFactory();
        try (InputStream is = new FileInputStream(file);
             JsonParser jsonParser = jsonFactory.createParser(is)) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalStateException("Expected content must be an array");
            }
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                Violation violation = calculateSingleViolation(jsonParser);
                String type = violation.getType();
                BigDecimal fineAmount = violation.getFineAmount();
                if (!oneFileResult.containsKey(type)) {
                    oneFileResult.put(type, fineAmount);
                } else {
                    oneFileResult.put(type, oneFileResult.get(type).add(fineAmount));
                }
            }
        }
        return oneFileResult;
    }

    /**
     * method for parsing one Violation object in one JSON file
     * @param jsonParser - JSON parser object
     */
    public static Violation calculateSingleViolation(JsonParser jsonParser) throws IOException {
        if (jsonParser.currentToken() != JsonToken.START_OBJECT) {
            throw new IllegalStateException("Expected content must be an object");
        }
        Violation violation = new Violation();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String property = jsonParser.getCurrentName();
            jsonParser.nextToken();
            switch (property) {
                case "type" -> violation.setType(jsonParser.getText());
                case "fine_amount" -> violation.setFineAmount(jsonParser.getDecimalValue());
            }
        }
        return violation;
    }

}