/*import java.io.File;
import java.awt.Desktop;
import java.io.IOException;

public class OpenFileAfterFinding {
    public static void searchForAndOpenFile(File directory, String targetFileName) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchForAndOpenFile(file, targetFileName); // Recursive call for subdirectories
                } else if (file.getName().equals(targetFileName)) {
                    System.out.println("Found the file: " + file.getAbsolutePath());

                    try {
                        Desktop.getDesktop().open(file); // Open the found file
                    } catch (IOException e) {
                        System.err.println("Error opening the file: " + e.getMessage());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String parentFolderPath = "/path/to/parent/folder";
        String targetFileName = "example.txt"; // The name of the file you want to find

        File parentFolder = new File(parentFolderPath);

        if (parentFolder.exists() && parentFolder.isDirectory()) {
            searchForAndOpenFile(parentFolder, targetFileName);
        } else {
            System.out.println("Parent folder does not exist or is not a directory.");
        }
    }
}
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileSearch {
    private JTextField folderPathField;
    private String targetFileName;

    public FileSearch() {
        JFrame frame = new JFrame("Find and Open File");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 70, 60));

        JLabel label = new JLabel(" Folder:");
        label.setBounds(50, 50, 100, 50);
        folderPathField = new JTextField(20);
        folderPathField.setBounds(55, 55, 150, 55);
        JButton browseButton = new JButton("Browse");
        JLabel nameLabel = new JLabel("File Name:");
        JTextField nameField = new JTextField(20);
        JButton searchButton = new JButton(" Open");

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    folderPathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String parentFolderPath = folderPathField.getText();
                targetFileName = nameField.getText();

                File parentFolder = new File(parentFolderPath);

                if (parentFolder.exists() && parentFolder.isDirectory()) {
                    searchForAndOpenFile(parentFolder, targetFileName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Parent folder does not exist or is not a directory.");
                }
            }
        });

        frame.add(label);
        frame.add(folderPathField);
        frame.add(browseButton);
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(searchButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setVisible(true);
    }

    public void searchForAndOpenFile(File directory, String targetFileName) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchForAndOpenFile(file, targetFileName); // Recursive call for subdirectories
                } else if (file.getName().equals(targetFileName)) {
                    System.out.println("Found the file: " + file.getAbsolutePath());

                    try {
                        Desktop.getDesktop().open(file); // Open the found file
                    } catch (IOException e) {
                        System.err.println("Error opening the file: " + e.getMessage());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FileSearch();
            }
        });
    }
}
