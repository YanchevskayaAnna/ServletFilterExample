package fileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileApp {

    private static final String FILE_NAME = "Forbidden_id.txt";
    private static long dateFileEditing;
    private volatile static List<String> fileList;

    public static List<String> readFile(String path)  {
        fileList = new ArrayList<String>();
//        Files.lines(Paths.get(path), StandardCharsets.UTF_8).forEach(s -> fileList.add(s));
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileList.add(line);
            }
        } catch (IOException e) {
            // log error
        }
        return fileList;
    }

    public static List<String> getListForbiddenID() {
        ClassLoader classLoader = FileApp.class.getClassLoader();
        String path = classLoader.getResource(FILE_NAME).getPath();

        File newFile = new File(path);
        if (newFile.lastModified() != dateFileEditing) {
            synchronized (FileApp.class) {
                if (newFile.lastModified() != dateFileEditing) {
                    dateFileEditing = newFile.lastModified();
                    return readFile(path);
                }
            }
        }

        return fileList;
    }
}



