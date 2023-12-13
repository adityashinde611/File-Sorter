
/* 
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.nio.file.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileSorter {
    private static FileWriter deletedMoviesLogWriter;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FileSorter <source_directory>");
            System.exit(1);
        }

        String sourceDirectory = args[0];
        sortFiles(sourceDirectory);
    }

public static void sortFiles(String sourceDirectory) {
    Map<String, String> extensionToFolderMap = createExtensionToFolderMap();

    try {
        deletedMoviesLogWriter = new FileWriter("deleted.txt", true);

        Files.walk(Paths.get(sourceDirectory))
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    String fileName = filePath.getFileName().toString();
                    String fileExtension = getFileExtension(fileName).toLowerCase();

                    String destinationFolder = extensionToFolderMap.get(fileExtension);

                    if (destinationFolder != null) {
                        File destinationDir = new File(sourceDirectory + File.separator + destinationFolder);
                        moveFile(filePath, destinationDir, fileName);
                    } else if (fileExtension.equals("movie")) {
                        logDeletedMovie(filePath.toString());
                        deleteFile(filePath, fileName);
                    } else {
                        // If the extension is not in the map, move the file to the "Other" folder
                        File otherDir = new File(sourceDirectory + File.separator + "Other");
                        moveFile(filePath, otherDir, fileName);
                    }
                });

        Files.walk(Paths.get(sourceDirectory))
                .filter(Files::isDirectory)
                .forEach(folderPath -> {
                    if (!folderPath.equals(Paths.get(sourceDirectory))) {
                        removeDuplicates(folderPath);
                    }
                });
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        closeDeletedMoviesLogWriter();
    }
}

    private static Map<String, String> createExtensionToFolderMap() {
        // Define the extension to folder mappings here as in the previous code.
        // ...
        // (Mappings for different file extensions)

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

    private static void moveFile(Path sourcePath, File destinationDir, String fileName) {
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }
        String destinationFileName = getUniqueFileName(destinationDir.toPath(), fileName);
        Path destinationPath = destinationDir.toPath().resolve(destinationFileName);

        try {
            Files.move(sourcePath, destinationPath);
            System.out.println("Moved: " + fileName + " to " + destinationPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    private static String getUniqueFileName(Path destinationDir, String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        String extension = "";
        String baseName = fileName;
    
        if (dotIndex != -1) {
            baseName = fileName.substring(0, dotIndex);
            extension = fileName.substring(dotIndex);
        }
    
        Path destinationPath = destinationDir.resolve(fileName);
        int count = 1;
    
        while (Files.exists(destinationPath)) {
            fileName = baseName + "_" + count + extension;
            destinationPath = destinationDir.resolve(fileName);
            count++;
        }
        return fileName;
    }
    

    private static void logDeletedMovie(String filePath) {
        if (deletedMoviesLogWriter != null) {
            try {
                deletedMoviesLogWriter.write(filePath + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void deleteFile(Path filePath, String fileName) {
        try {
            Files.delete(filePath);
            System.out.println("Deleted movie file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeDeletedMoviesLogWriter() {
        if (deletedMoviesLogWriter != null) {
            try {
                deletedMoviesLogWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // The rest of your methods (removeDuplicates, fileIsPresent, getHash, toHexadecimal) remain the same.

    public static void removeDuplicates(Path targetFolder) {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(targetFolder)) {
            Map<String, List<Path>> seen = new HashMap<>();

            for (Path path : paths) {
                if (Files.isDirectory(path)) continue;

                String hashSum = getHash(path);

                if (seen.containsKey(hashSum) && fileIsPresent(seen.get(hashSum), path)) {
                    System.out.println("Deleting file: " + path.getFileName());
                    Files.delete(path);
                } else {
                    seen.computeIfAbsent(hashSum, k -> new ArrayList<>()).add(path);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean fileIsPresent(List<Path> paths, Path toCheck) throws IOException {
        boolean isPresent = false;
        for (Path path : paths) {
            if (Files.mismatch(path, toCheck) == -1) {
                isPresent = true;
                break;
            }
        }
        return isPresent;
    }

    public static String getHash(Path path) throws IOException {
        if (Files.exists(path)) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] buffer = new byte[8192];  // Adjust the buffer size as needed
                try (InputStream is = Files.newInputStream(path)) {
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        md.update(buffer, 0, bytesRead);
                    }
                }
                return toHexadecimal(md.digest());
            } catch (NoSuchAlgorithmException e) {
                // Handle or log the exception (though it's unlikely to occur here)
                e.printStackTrace();
                return null;
            }
        } else {
            System.err.println("File does not exist: " + path.toString());
            return null;
        }
    }
    

    public static String toHexadecimal(byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> String.format("%02x", bytes[i]))
                .collect(Collectors.joining());
    }
}


*/

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.nio.file.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileSorter {
    private static FileWriter deletedLogWriter;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FileSorter <source_directory>");
            System.exit(1);
        }

        String sourceDirectory = args[0];
        sortFiles(sourceDirectory);
    }

    public static void sortFiles(String sourceDirectory) {
        Map<String, String> extensionToFolderMap = createExtensionToFolderMap();

        try {
            deletedLogWriter = new FileWriter("deleted.txt", true);

            Files.walk(Paths.get(sourceDirectory))
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        String fileName = filePath.getFileName().toString();
                        String fileExtension = getFileExtension(fileName).toLowerCase();

                        String destinationFolder = extensionToFolderMap.get(fileExtension);

                        if (destinationFolder != null) {
                            File destinationDir = new File(sourceDirectory + File.separator + destinationFolder);
                            moveFile(filePath, destinationDir, fileName);
                        } else {
                            logDeletedFile(filePath.toString());
                            deleteFile(filePath, fileName);
                        }
                    });

            Files.walk(Paths.get(sourceDirectory))
                    .filter(Files::isDirectory)
                    .forEach(folderPath -> {
                        if (!folderPath.equals(Paths.get(sourceDirectory))) {
                            removeDuplicates(folderPath);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeDeletedLogWriter();
        }
    }

    // ... (The rest of your methods and createExtensionToFolderMap)
    private static Map<String, String> createExtensionToFolderMap() {
        // Define the extension to folder mappings here as in the previous code.
        // ...
        // (Mappings for different file extensions)

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

    private static void moveFile(Path sourcePath, File destinationDir, String fileName) {
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }
        String destinationFileName = getUniqueFileName(destinationDir.toPath(), fileName);
        Path destinationPath = destinationDir.toPath().resolve(destinationFileName);

        try {
            Files.move(sourcePath, destinationPath);
            System.out.println("Moved: " + fileName + " to " + destinationPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    private static String getUniqueFileName(Path destinationDir, String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        String extension = "";
        String baseName = fileName;

        if (dotIndex != -1) {
            baseName = fileName.substring(0, dotIndex);
            extension = fileName.substring(dotIndex);
        }

        Path destinationPath = destinationDir.resolve(fileName);
        int count = 1;

        while (Files.exists(destinationPath)) {
            fileName = baseName + "_" + count + extension;
            destinationPath = destinationDir.resolve(fileName);
            count++;
        }
        return fileName;
    }

    private static void deleteFile(Path filePath, String fileName) {
        try {
            Files.delete(filePath);

            System.out.println("Deleted movie file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The rest of your methods (removeDuplicates, fileIsPresent, getHash,
    // toHexadecimal) remain the same.

    public static void removeDuplicates(Path targetFolder) {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(targetFolder)) {
            Map<String, List<Path>> seen = new HashMap<>();

            for (Path path : paths) {
                if (Files.isDirectory(path))
                    continue;

                String hashSum = getHash(path);

                if (seen.containsKey(hashSum) && fileIsPresent(seen.get(hashSum), path)) {
                    logDeletedFile(path.toString());
                    System.out.println("Deleting file: " + path.getFileName());
                    Files.delete(path);
                } else {
                    seen.computeIfAbsent(hashSum, k -> new ArrayList<>()).add(path);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean fileIsPresent(List<Path> paths, Path toCheck) throws IOException {
        boolean isPresent = false;
        for (Path path : paths) {
            if (Files.mismatch(path, toCheck) == -1) {
                isPresent = true;
                break;
            }
        }
        return isPresent;
    }

    public static String getHash(Path path) throws IOException {
        if (Files.exists(path)) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] buffer = new byte[8192]; // Adjust the buffer size as needed
                try (InputStream is = Files.newInputStream(path)) {
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        md.update(buffer, 0, bytesRead);
                    }
                }
                return toHexadecimal(md.digest());
            } catch (NoSuchAlgorithmException e) {
                // Handle or log the exception (though it's unlikely to occur here)
                e.printStackTrace();
                return null;
            }
        } else {
            System.err.println("File does not exist: " + path.toString());
            return null;
        }
    }

    public static String toHexadecimal(byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> String.format("%02x", bytes[i]))
                .collect(Collectors.joining());
    }

    private static void logDeletedFile(String filePath) {
        System.out.println("Shinde");
        if (deletedLogWriter != null) {
            try {
                System.out.println("aditya");
                deletedLogWriter.write(filePath + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeDeletedLogWriter() {
        if (deletedLogWriter != null) {
            try {
                deletedLogWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
