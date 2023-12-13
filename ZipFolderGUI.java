import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ZipFolderGUI {
    private JTextField sourceFolderField;
    private JTextField zipFilePathField;

    public ZipFolderGUI() {
        JFrame frame = new JFrame("Folder Zipper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 70, 60));

        JLabel sourceFolderLabel = new JLabel("Source Folder:");
        sourceFolderField = new JTextField(30);
        JButton sourceFolderButton = new JButton("Browse");

        JLabel zipFilePathLabel = new JLabel("Zip File Path:");
        zipFilePathField = new JTextField(30);

        JButton zipButton = new JButton("Zip Folder");
        JButton DestinationFolderButton = new JButton("Browse");
        sourceFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    sourceFolderField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        DestinationFolderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    zipFilePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        zipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sourceFolder = sourceFolderField.getText();
                String zipFilePath = zipFilePathField.getText();

                ZipDirectory.ZipDirectoryfile(sourceFolder, zipFilePath);
                JOptionPane.showMessageDialog(frame, "Folder zipped successfully.");
            }
        });

        frame.add(sourceFolderLabel);
        frame.add(sourceFolderField);
        frame.add(sourceFolderButton);

        frame.add(zipFilePathLabel);
        frame.add(zipFilePathField);
        frame.add(zipButton);
        frame.add(DestinationFolderButton);

        frame.setSize(800, 300);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ZipFolderGUI();
            }
        });
    }
}
