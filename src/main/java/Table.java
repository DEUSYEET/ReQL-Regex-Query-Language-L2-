import java.util.Map;

public class Table {
    private String filePath;
    private Map<String,String> patterns;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Table(String name, String filePath, Map<String, String> patterns) {
        setName(name);
        setFilePath(filePath);
        setPatterns(patterns);
    }
}
