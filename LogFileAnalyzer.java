
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileAnalyzer {

    private static final String LOG_FILE_PATH = "access.log";
    private static final Pattern LOG_PATTERN = Pattern.compile("^(\\S+) \\S+ \\S+ \\[.+\\] \"\\S+ (\\S+) \\S+\" (\\d{3})");

    public static void analyzeLogFile(String logFilePath) {
        File logFile = new File(logFilePath);

        if (!logFile.exists()) {
            System.out.println("Log file " + logFilePath + " not found!");
            return;
        }

        Map<String, Integer> ipCount = new HashMap<>();
        Map<String, Integer> pageCount = new HashMap<>();
        Map<String, Integer> statusCount = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = LOG_PATTERN.matcher(line);
                if (matcher.matches()) {
                    String ip = matcher.group(1);
                    String page = matcher.group(2);
                    String status = matcher.group(3);

                    ipCount.put(ip, ipCount.getOrDefault(ip, 0) + 1);
                    pageCount.put(page, pageCount.getOrDefault(page, 0) + 1);
                    statusCount.put(status, statusCount.getOrDefault(status, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Most common IP addresses:");
        ipCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " requests"));

        System.out.println("\nMost requested pages:");
        pageCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " requests"));

        System.out.println("\nStatus code summary:");
        statusCount.forEach((status, count) -> System.out.println(status + ": " + count + " occurrences"));
    }

    public static void main(String[] args) {
        analyzeLogFile(LOG_FILE_PATH);
    }
}
