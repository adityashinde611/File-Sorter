import java.io.File;
import java.io.IOException;

import java.nio.file.*;
import java.util.*;



public class FileSorter_NODelete {
    public static void main(String[] args) {
        // In this version, the source directory is passed as a command-line argument.
        if (args.length != 1) {
            System.out.println("Usage: java FileSorter <source_directory>");
            System.exit(1);
        }

        String sourceDirectory = args[0];
        sortFiles(sourceDirectory);
    }

    public static void sortFiles(String sourceDirectory) {
        // Create a map to store the file extensions and their corresponding folder names
        Map<String, String> extensionToFolderMap = createExtensionToFolderMap();

        try {
            Files.walk(Paths.get(sourceDirectory))
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        String fileName = filePath.getFileName().toString();
                        String fileExtension = getFileExtension(fileName).toLowerCase();

                        String destinationFolder = extensionToFolderMap.get(fileExtension);

                        if (destinationFolder != null) {
                            File destinationDir = new File(sourceDirectory + File.separator + destinationFolder);

                            if (!destinationDir.exists()) {
                                destinationDir.mkdirs();
                            }

                            // Handle potential duplicates by adding a unique identifier
                            String destinationFileName = getUniqueFileName(destinationDir.toPath(), fileName);

                            Path destinationPath = destinationDir.toPath().resolve(destinationFileName);

                            try {
                                Files.move(filePath, destinationPath);
                                System.out.println("Moved: " + fileName + " to " + destinationPath.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> createExtensionToFolderMap() {
        // Define the extension to folder mappings here as in the previous code.
        // ...
        Map<String, String> map = new HashMap<>();
        // ... (Mappings for different file extensions)
        map.put("avi", "movie");
        map.put("mp4", "movie");
        map.put("mkv", "movie");
        map.put("wmv", "movie");
        map.put("mov", "movie");
        map.put("flv", "movie");
        map.put("mpeg", "movie");
        map.put("mpg", "movie");
        map.put("rm", "movie");
        map.put("rmvb", "movie");
        map.put("m4v", "movie");

        map.put("mp3", "audio");
        map.put("wav", "audio");
        map.put("flac", "audio");
        map.put("aac", "audio");
        map.put("ogg", "audio");
        map.put("wma", "audio");
        map.put("m4a", "audio");
        map.put("aiff", "audio");
        map.put("ape", "audio");
        map.put("alac", "audio");

        map.put("jpg", "image");
        map.put("jpeg", "image");
        map.put("png", "image");
        map.put("gif", "image");
        map.put("bmp", "image");
        map.put("tiff", "image");
        map.put("webp", "image");
        map.put("svg", "image");
        map.put("ico", "image");

        map.put("pdf", "Document");
        map.put("doc", "Document");
        map.put("docx", "Document");
        map.put("ppt", "Document");
        map.put("pptx", "Document");
        map.put("xls", "Document");
        map.put("xlsx", "Document");
        map.put("odt", "Document");
        map.put("odp", "Document");
        map.put("ods", "Document");
        map.put("txt", "Document");
        map.put("rtf", "Document");

        map.put("zip", "compress");
        map.put("rar", "compress");
        map.put("7z", "compress");
        map.put("tar", "compress");
        map.put("gz", "compress");
        map.put("xz", "compress");
        map.put("bz2", "compress");
        map.put("tar.gz", "compress");
        map.put("tar.xz", "compress");
        map.put("tar.bz2", "compress");

        map.put("cpp", "Developer");
        map.put("h", "Developer");
        map.put("java", "Developer");
        map.put("py", "Developer");
        map.put("html", "Developer");
        map.put("css", "Developer");
        map.put("js", "Developer");
        map.put("php", "Developer");
        map.put("xml", "Developer");
        map.put("json", "Developer");
        map.put("md", "Developer");
        map.put("txt", "Developer");
        map.put("rb", "Developer");
        map.put("swift", "Developer");
        map.put("c", "Developer");
        map.put("cs", "Developer");
        map.put("go", "Developer");
        map.put("sql", "Developer");
        map.put("sh", "Developer");
        map.put("yaml", "Developer");
        map.put("toml", "Developer");

        map.put("html", "web file");
        map.put("css", "web file");
        map.put("js", "web file");
        map.put("php", "web file");
        map.put("asp", "web file");
        map.put("jsp", "web file");
        map.put("xml", "web file");
        map.put("json", "web file");
        map.put("svg", "web file");
        map.put("ico", "web file");
        map.put("woff", "web file");
        map.put("woff2", "web file");
        map.put("ttf", "web file");
        map.put("eot", "web file");       
        return map;
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(dotIndex + 1);
        }
        // If there is no extension, return an empty string or handle it as needed.
        return "";
    }

    private static String getUniqueFileName(Path destinationDir, String fileName) {
        Path destinationPath = destinationDir.resolve(fileName);
        int count = 1;
        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        while (Files.exists(destinationPath)) {
            fileName = baseName + "_" + count + extension;
            destinationPath = destinationDir.resolve(fileName);
            count++;
        }
        return fileName;
    }

}
