package homework.second;

import homework.second.Task1.FileUtil;
import homework.second.Task2.StatUtil;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        //Task 1 runs
        try {
            FileUtil.copyAndModifyFile("src/main/resources/Task1Files/persons.xml",
                    "src/main/resources/Task1Files/personsModified.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Task 2 runs
        try {
            StatUtil.writeStatisticToFile(new File("src/main/resources/Task2Files/ViolationFiles"),
                    new File("src/main/resources/Task2Files/ViolationStat/ViolationStat.xml"));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}