package homework.second;

import homework.second.Task1.FileUtil;
import homework.second.Task2.ViolationStatisticUtil;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        //Task 1
        try {
            FileUtil.copyAndModifyFile("src/main/resources/Task1Files/persons.xml",
                    "src/main/resources/Task1Files/personsModified.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Task 2
        try {
            ViolationStatisticUtil.writeFineStatisticToFile(new File("src/main/resources/Task2Files/ViolationFiles"),
                    new File("src/main/resources/Task2Files/violationStat.xml"));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}