public interface LogFilter {
    void filter(String source_file, String target_file, LogLevel level);
}
