import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SevereLogFilter implements LogFilter {

    @Override
    public void filter(String source_file, String target_file, LogLevel level) {
        String keyword = "[" + level.name() + "]";

        try (FileReader fileReader = new FileReader(source_file);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
             Scanner scanner = new Scanner(bufferedReader);
             FileWriter fileWriter = new FileWriter(target_file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(keyword)) {
                    printWriter.println(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
