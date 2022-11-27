package homework.second.Task2;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import homework.second.Task2.model.Violation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ViolationStatisticUtil {

    public static void writeFineStatisticToFile(File folderIn, File fileOut) throws IOException, NullPointerException {
        List<File> fileList = Arrays.stream(Objects.requireNonNull(folderIn.listFiles()))
                .filter(f -> f.isFile() && f.getName().endsWith("_Violations.json"))
                .collect(toList());
        if (fileList.isEmpty()) {
            throw new NullPointerException("No files with _Violations.json suffix");
        }
        XmlMapper xmlMapper = JacksonMapper.getXmlMapper();
        try (Writer writer = new FileWriter(fileOut)) {
            writer.write(xmlMapper.writer().withRootName("TotalFine")
                    .writeValueAsString(calculateFine(fileList)));
        }
    }


    public static List<Violation> calculateFine(List<File> fileList) throws IOException {
        JsonMapper jsonMapper = JacksonMapper.getJsonMapper();
        Map<String, BigDecimal> allFilesResult = new HashMap<>();
        for (File file : fileList) {
            var oneFileResult = Arrays.stream(jsonMapper.readValue(file, Violation[].class))
                    .collect(Collectors.toMap(Violation::getType, Violation::getFineAmount,
                            BigDecimal::add));
            oneFileResult.forEach((key, val) -> allFilesResult.merge(key, val, BigDecimal::add));
        }
        return allFilesResult.entrySet().stream()
                .map((m) -> new Violation(m.getKey(), m.getValue()))
                .sorted(Comparator.comparing(Violation::getFineAmount).reversed())
                .collect(toList());
    }
}