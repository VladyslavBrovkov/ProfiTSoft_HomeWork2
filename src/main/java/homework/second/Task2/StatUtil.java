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

public class StatUtil {

    public static void writeStatisticToFile(File folderIn, File fileOut) throws IOException, NullPointerException {
        List<File> fileList = Arrays.stream(Objects.requireNonNull(folderIn.listFiles()))
                .filter(f -> f.isFile() && f.getName().endsWith("_Violations.json"))
                .collect(Collectors.toList());
        if (fileList.isEmpty()) {
            throw new NullPointerException("No files with _Violation.json suffix");
        }
        XmlMapper xmlMapper = JacksonMapper.getXmlMapper();
        try (Writer writer = new FileWriter(fileOut)) {
            writer.write(xmlMapper.writer().withRootName("TotalFineByType")
                    .writeValueAsString(calculateStatistic(fileList)));
        }
    }


    public static Map<String, BigDecimal> calculateStatistic(List<File> fileList) throws IOException {
        JsonMapper jsonMapper = JacksonMapper.getJsonMapper();
        Map<String, BigDecimal> allFileResult = new HashMap<>();
        for (File file : fileList) {
            var oneFileResult = Arrays.stream(jsonMapper.readValue(file, Violation[].class))
                    .collect(Collectors.toMap(Violation::getType, Violation::getFine_amount,
                            BigDecimal::add));
            oneFileResult.forEach((key, val) -> allFileResult.merge(key, val, BigDecimal::add));
        }
        return allFileResult.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}
