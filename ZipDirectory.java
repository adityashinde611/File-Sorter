
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory {
    public static void ZipDirectoryfile(String sourcePath, String destinationFolder) {

        // Specify the name of the output ZIP file
        String outputZipFileName = "archive.zip";

        try {
            File destinationDir = new File(destinationFolder);
            if (!destinationDir.exists()) {
                destinationDir.mkdirs(); // Create the destination folder if it doesn't exist
            }

            File zipFile = new File(destinationDir, outputZipFileName);
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            File fileToZip = new File(sourcePath);
            zipDirectory(fileToZip, fileToZip.getName(), zipOut);

            zipOut.close();
            fos.close();

            System.out.println("Directory zipped successfully to " + zipFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipDirectory(File directory, String baseName, ZipOutputStream zipOut) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    zipDirectory(file, baseName + "/" + file.getName(), zipOut);
                } else {
                    zipFile(file, baseName, zipOut);
                }
            }
        }
    }

    private static void zipFile(File file, String baseName, ZipOutputStream zipOut) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(baseName + "/" + file.getName());
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }

        fis.close();
    }
}
