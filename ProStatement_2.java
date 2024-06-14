
		import java.io.File;
		import java.io.FileWriter;
		import java.io.IOException;
		import java.lang.management.ManagementFactory;
		import com.sun.management.OperatingSystemMXBean;
		import java.time.LocalDateTime;
		import java.time.format.DateTimeFormatter;

		public class ProStatement_2 {

		    private static final double CPU_THRESHOLD = 80.0;
		    private static final double MEMORY_THRESHOLD = 80.0;
		    private static final double DISK_THRESHOLD = 80.0;
		    private static final String LOG_FILE = "system_health.log";

		    private static void logMessage(String message) {
		        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
		            LocalDateTime now = LocalDateTime.now();
		            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		            writer.write(now.format(formatter) + " - " + message + "\n");
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }

		    @SuppressWarnings("deprecation")
			private static void checkCpuUsage(OperatingSystemMXBean osBean) {
		        double cpuLoad = osBean.getCpuLoad() * 100;
		        if (cpuLoad > CPU_THRESHOLD) {
		            String message = String.format("High CPU usage detected: %.2f%%", cpuLoad);
		            logMessage(message);
		            System.out.println(message);
		        }
		    }

		    @SuppressWarnings("deprecation")
			private static void checkMemoryUsage(OperatingSystemMXBean osBean) {
		        @SuppressWarnings("deprecation")
				double totalMemory = osBean.getTotalMemorySize();
		        double freeMemory = osBean.getFreeMemorySize()
;
		        double memoryUsage = (totalMemory - freeMemory) / totalMemory * 100;
		        if (memoryUsage > MEMORY_THRESHOLD) {
		            String message = String.format("High Memory usage detected: %.2f%%", memoryUsage);
		            logMessage(message);
		            System.out.println(message);
		        }
		    }

		    private static void checkDiskUsage() {
		        File root = new File("/");
		        double totalSpace = root.getTotalSpace();
		        double freeSpace = root.getFreeSpace();
		        double diskUsage = (totalSpace - freeSpace) / totalSpace * 100;
		        if (diskUsage > DISK_THRESHOLD) {
		            String message = String.format("High Disk usage detected: %.2f%%", diskUsage);
		            logMessage(message);
		            System.out.println(message);
		        }
		    }

		    private static void checkRunningProcesses() {
		        int processCount = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
		        String message = String.format("Number of running processes: %d", processCount);
		        logMessage(message);
		        System.out.println(message);
		    }

		    public static void main(String[] args) {
		        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		        checkCpuUsage(osBean);
		        checkMemoryUsage(osBean);
		        checkDiskUsage();
		        checkRunningProcesses();
		    }
		
	
	}


