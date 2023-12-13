
/*import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame("FILE CATEGORIZER");
            frame.setSize(1000, 800);
            frame.setResizable(false);

            // Create a panel with a dark metallic color
            JPanel panel = new JPanel();
            panel.setBackground(new Color(51, 51, 51)); // Use the RGB color values for dark metallic color
            frame.getContentPane().add(panel);

            // Use a FlowLayout for the panel
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 200));

            // Create and add three images to the panel
            ImageIcon image1 = new ImageIcon("1st.png"); // Replace with your image file path
            JLabel label1 = new JLabel(image1);
            label1.setPreferredSize(new Dimension(250, 200));
            panel.add(label1);

            ImageIcon image2 = new ImageIcon("2nd.png"); // Replace with your image file path
            JLabel label2 = new JLabel(image2);
            label2.setPreferredSize(new Dimension(250, 200));
            panel.add(label2);

            ImageIcon image3 = new ImageIcon("3rd.png"); // Replace with your image file path
            JLabel label3 = new JLabel(image3);
            label3.setPreferredSize(new Dimension(250, 200));
            panel.add(label3);

            label1.addMouseListener(new MouseClickListener(frame, label1, "Folder Sort By Path"));
            label2.addMouseListener(new MouseClickListener(frame, label2, "Second Image Frame"));
            label3.addMouseListener(new MouseClickListener(frame, label3, "Third Image Frame"));

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    static class MouseClickListener implements java.awt.event.MouseListener {
        private JFrame mainFrame;
        private JLabel label;
        private String frameTitle;

        public MouseClickListener(JFrame mainFrame, JLabel label, String frameTitle) {
            this.mainFrame = mainFrame;
            this.label = label;
            this.frameTitle = frameTitle;
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            // Hide the main frame
            mainFrame.setVisible(false);

            // Create a new frame when an image is clicked
            JFrame newFrame = new JFrame(frameTitle);
            newFrame.setSize(1000, 800); // Set the size for the new frame
            newFrame.setResizable(false);
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.getContentPane().setBackground(new Color(51, 51, 51)); // Dark metallic background
            newFrame.setVisible(true);

            // Show the main frame again when the new frame is closed
            newFrame.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    mainFrame.setVisible(true);
                }
            });
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
        }
    }
}
*/
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame("FILE CATEGORIZER");
            frame.setSize(1000, 800);
            frame.setResizable(false);

            // Set the default close operation to DO_NOTHING_ON_CLOSE
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            // Create a panel with a dark metallic color
            JPanel panel = new JPanel();
            panel.setBackground(new Color(51, 51, 51)); // Use the RGB color values for dark metallic color
            frame.getContentPane().add(panel);

            // Use a FlowLayout for the panel
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 200));

            // Create and add three images to the panel
            ImageIcon image1 = new ImageIcon("1st.png"); // Replace with your image file path
            JLabel label1 = new JLabel(image1);
            label1.setPreferredSize(new Dimension(250, 200));
            panel.add(label1);

            ImageIcon image2 = new ImageIcon("zip.png"); // Replace with your image file path
            JLabel label2 = new JLabel(image2);
            label2.setPreferredSize(new Dimension(250, 200));
            panel.add(label2);

            ImageIcon image3 = new ImageIcon("3rd.png"); // Replace with your image file path
            JLabel label3 = new JLabel(image3);
            label3.setPreferredSize(new Dimension(250, 200));
            panel.add(label3);

        //    ImageIcon image4 = new ImageIcon("google.png"); // Replace with your image file path
          //  JLabel label4 = new JLabel(image4);
         //   label3.setPreferredSize(new Dimension(250, 200));
          //  panel.add(label4);

            label1.addMouseListener(new MouseClickListener(frame, "Folder Sort By Path"));
            label2.addMouseListener(new MouseClickListener(frame, "Second Image Frame"));
            label3.addMouseListener(new MouseClickListener(frame, "Third Image Frame"));
          //  label4.addMouseListener(new MouseClickListener(frame, "Google Drive"));

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    static class MouseClickListener implements java.awt.event.MouseListener {
        private JFrame mainFrame;
        private String frameTitle;

        public MouseClickListener(JFrame mainFrame, String frameTitle) {
            this.mainFrame = mainFrame;
            this.frameTitle = frameTitle;
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (frameTitle.equals("Third Image Frame")) {
                new FileSearch();
            } else if (frameTitle.equals("Folder Sort By Path")) {
                new DirectoryChoseApp();
            } else if (frameTitle.equals("Second Image Frame")) {
                new ZipFolderGUI();
            } else {
                // Hide the main frame
                mainFrame.setVisible(false);

                // Create a new frame when an image is clicked
                JFrame newFrame = new JFrame(frameTitle);
                newFrame.setSize(1000, 800); // Set the size for the new frame
                newFrame.setResizable(false);

                // Set the default close operation to DISPOSE_ON_CLOSE
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newFrame.getContentPane().setBackground(new Color(51, 51, 51)); // Dark metallic background
                newFrame.setVisible(true);

                // Add a window listener to dispose of the main frame when it's closed
                newFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        mainFrame.dispose();
                    }
                });
            }
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
        }
    }
}
