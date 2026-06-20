import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) throws IOException {
        createSampleLog("sample.log");

        JFileChooser chooser = new JFileChooser(".");
        chooser.setDialogTitle("Виберіть файл логів");

        if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        String source = chooser.getSelectedFile().getAbsolutePath();

        chooser.setDialogTitle("Виберіть файл для результату");
        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        String target = chooser.getSelectedFile().getAbsolutePath();

        String[] options = {"ERROR", "CRITICAL"};
        int choice = JOptionPane.showOptionDialog(null, "Рівень фільтрації:", "LogLevel",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice < 0) {
            return;
        }
        LogLevel level = choice == 0 ? LogLevel.ERROR : LogLevel.CRITICAL;

        LogFilter filter = new SevereLogFilter();
        filter.filter(source, target, level);

        JOptionPane.showMessageDialog(null, "Готово: " + target);
    }

    private static void createSampleLog(String path) throws IOException {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("2024-01-01 10:00:00 [INFO] Application started\n");
            fw.write("2024-01-01 10:01:00 [WARNING] Low memory\n");
            fw.write("2024-01-01 10:02:00 [ERROR] Connection failed\n");
            fw.write("2024-01-01 10:03:00 [INFO] Retrying connection\n");
            fw.write("2024-01-01 10:04:00 [CRITICAL] Database unavailable\n");
            fw.write("2024-01-01 10:05:00 [ERROR] Timeout exceeded\n");
        }
    }
}
