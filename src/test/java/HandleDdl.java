import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HandleDdl {

    public static String createTable = "create table";

    public static String comment = "comment on";

    public static String originFilePath = "C:\\Users\\LENOVO\\Desktop\\新建文件夹\\sql.txt";

    public static String targetFilePath = "C:\\Users\\LENOVO\\Desktop\\新建文件夹\\target.txt";

    public static void main(String[] args) {
//        genDropDdl();
        handleCreateDdl();
    }

    private static void handleCreateDdl() {

    }

    private static List<String> getTableNames() {
        List<String> lines = getAllLines();
        lines = lines.stream().filter(e -> e.contains("create table")).collect(Collectors.toList());
        List<String> tableNames = new ArrayList<>();
        for (String line : lines) {
            String tableName = extractTableName(line);
            tableNames.add(tableName);
        }
        return tableNames;
    }

    private static String extractTableName(String line) {
        // 查找"create table"后的表名位置
        int tableNameStartIndex = line.indexOf(createTable) + createTable.length();
        // 提取表名，去除前后空格
        return line.substring(tableNameStartIndex).trim().split("\\(")[0].trim();
    }

    private static List<String> getAllLines() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(originFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }


    private static void genDropDdl() {
        List<String> tableNames = getTableNames();
        List<String> dropLines = new ArrayList<>();
        for (String tableName : tableNames) {
            dropLines.add("drop table " + tableName + ";");
        }
        System.out.println(String.format("================ 条数：%s ++++++++++++++++++++", dropLines.size()));
        System.out.println(String.join("\n", dropLines));
    }
}
