import java.util.Map;

public class Table {
    private String filePath;
    private Map<String,String> patterns;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, String> getPatterns() {
        return patterns;
    }

    public void setPatterns(Map<String, String> patterns) {
        this.patterns = patterns;
    }

    public Table(String filePath, Map<String, String> patterns) {
        setFilePath(filePath);
        setPatterns(patterns);
    }
}
