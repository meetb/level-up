
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class Main {

    private static JLabel pic;
    private static JFrame frame;
    private static BufferedImage img;
    private static BufferedImage newImg;
    private static BufferedImage original;

    public static void main(String[] args) {
        createAndShowGUI();
    }

    private static void createAndShowGUI() {
        //Create and set up the window according to the Windows style format.
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame = new JFrame("Image Editor GUI");
        JMenuBar menuBar = new JMenuBar();
        pic = new JLabel();
        JMenu file = new JMenu("File");
        JMenu options = new JMenu("Options");
        menuBar.add(file);
        menuBar.add(options);
        //Menu Items
        JMenuItem open = new JMenuItem("Open", KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.setActionCommand("open");
        open.addActionListener(new FirstMenu());
        file.add(open);
        JMenuItem save = new JMenuItem("Save As", KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.setActionCommand("save");
        save.addActionListener(new FirstMenu());
        file.add(save);
        JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_E);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        exit.setActionCommand("exit");
        exit.addActionListener(new FirstMenu());
        file.add(exit);
        JMenuItem original = new JMenuItem("Return to Original", KeyEvent.VK_R);
        original.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        original.setActionCommand("original");
        original.addActionListener(new FirstMenu());
        options.add(original);
        JMenuItem hFlip = new JMenuItem("Horizontal Flip", KeyEvent.VK_H);
        hFlip.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        hFlip.setActionCommand("hflip");
        hFlip.addActionListener(new FirstMenu());
        options.add(hFlip);
        JMenuItem vFlip = new JMenuItem("Vertical Flip", KeyEvent.VK_V);
        vFlip.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        vFlip.setActionCommand("vflip");
        vFlip.addActionListener(new FirstMenu());
        options.add(vFlip);
        JMenuItem gScale = new JMenuItem("Gray Scale", KeyEvent.VK_G);
        gScale.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        gScale.setActionCommand("gscale");
        gScale.addActionListener(new FirstMenu());
        options.add(gScale);
        JMenuItem sTone = new JMenuItem("Sepia Tone", KeyEvent.VK_P);
        sTone.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        sTone.setActionCommand("stone");
        sTone.addActionListener(new FirstMenu());
        options.add(sTone);
        JMenuItem iColour = new JMenuItem("Invert Colour", KeyEvent.VK_I);
        iColour.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        iColour.setActionCommand("icolour");
        iColour.addActionListener(new FirstMenu());
        options.add(iColour);
        JMenuItem gBlur = new JMenuItem("Gausian Blur", KeyEvent.VK_U);
        gBlur.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        gBlur.setActionCommand("gblur");
        gBlur.addActionListener(new FirstMenu());
        options.add(gBlur);
        JMenuItem bEffect = new JMenuItem("Buldge Effect", KeyEvent.VK_B);
        bEffect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        bEffect.setActionCommand("beffect");
        bEffect.addActionListener(new FirstMenu());
        options.add(bEffect);
        menuBar.setOpaque(true);
        pic.setPreferredSize(new Dimension(650, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.add(pic);
        frame.setVisible(true);
        frame.pack();
    }

    // Updates the JFrame after a new BufferedImage is set
    public static void rePaint(BufferedImage fileName) {
        img = fileName;
        pic.setIcon(new ImageIcon(fileName));
        frame.repaint();
        frame.pack();
    }

    public static class FirstMenu implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            JFileChooser chooser = new JFileChooser();
            int newRed = 0, newGreen = 0, newBlue = 0;
            if (command.equals("open")) {
                chooser.setFileFilter(new ImageFilter());
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    try {
                        img = ImageIO.read(file);
                        original = ImageIO.read(file);
                        newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                        pic.setPreferredSize(null);
                        rePaint(img);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(frame, "Sorry, the image file could not be opened.");
                    }
                }
            }
            if (command.equals("save")) {
                if (pic.getIcon() != null) {
                    File saveFile = new File("savedimage.jpg");
                    chooser.setSelectedFile(saveFile);
                    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        saveFile = chooser.getSelectedFile();
                        try {
                            ImageIO.write(newImg, "jpg", saveFile);
                            JOptionPane.showMessageDialog(frame, "File was successfully saved at" + saveFile.getAbsolutePath());
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(frame, "Sorry, the image file could not be saved.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "There is no image open.");
                }
            }
            if (command.equals("exit")) {
                System.exit(0);
            }
            if (command.equals("original")) {
                rePaint(original);
            }
            // Flips the image horizontally
            if (command.equals("hflip")) {
                newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                for (int i = 1; i < img.getWidth(); i++) {
                    for (int j = 1; j < img.getHeight(); j++) {
                        newImg.setRGB(i, j, img.getRGB(img.getWidth() - i, j));
                    }
                }
                rePaint(newImg);
            }
            // Flips the image vertically
            if (command.equals("vflip")) {
                newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                for (int i = 1; i < img.getWidth(); i++) {
                    for (int j = 1; j < img.getHeight(); j++) {
                        newImg.setRGB(i, j, img.getRGB(i, img.getHeight() - j));
                    }
                }
                rePaint(newImg);
            }
            // GrayScales the image, converts it into shades of black and white
            if (command.equals("gscale")) {
                int gIntensity;
                newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                for (int i = 0; i < img.getWidth(); i++) {
                    for (int j = 0; j < img.getHeight(); j++) {
                        gIntensity = (int) ((0.2126 * new Color((img.getRGB(i, j))).getRed()) + (0.7152 * new Color((img.getRGB(i, j))).getGreen()) + (0.0722 * new Color((img.getRGB(i, j))).getBlue()));
                        newImg.setRGB(i, j, new Color(gIntensity, gIntensity, gIntensity).getRGB());
                    }
                }
                rePaint(newImg);
            }
            // Adds a sepia tone to the picture
            if (command.equals("stone")) {
                newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                for (int i = 0; i < img.getWidth(); i++) {
                    for (int j = 0; j < img.getHeight(); j++) {
                        newRed = (int) ((new Color((img.getRGB(i, j))).getRed() * 0.393) + (new Color((img.getRGB(i, j))).getGreen() * 0.769) + (new Color((img.getRGB(i, j))).getBlue() * 0.189));
                        newGreen = (int) ((new Color((img.getRGB(i, j))).getRed() * 0.349) + (new Color((img.getRGB(i, j))).getGreen() * 0.686) + (new Color((img.getRGB(i, j))).getBlue() * 0.168));
                        newBlue = (int) ((new Color((img.getRGB(i, j))).getRed() * 0.272) + (new Color((img.getRGB(i, j))).getGreen() * 0.534) + (new Color((img.getRGB(i, j))).getBlue() * 0.131));
                        if (newRed > 255) {
                            newRed = 255;
                        }
                        if (newGreen > 255) {
                            newGreen = 255;
                        }
                        if (newBlue > 255) {
                            newBlue = 255;
                        }
                        newImg.setRGB(i, j, new Color(newRed, newGreen, newBlue).getRGB());
                    }
                }
                rePaint(newImg);
            }
            // Inverts the colours in the picture
            if (command.equals("icolour")) {
                newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                for (int i = 0; i < img.getWidth(); i++) {
                    for (int j = 0; j < img.getHeight(); j++) {
                        newImg.setRGB(i, j, new Color(255 - new Color((img.getRGB(i, j))).getRed(), 255 - new Color((img.getRGB(i, j))).getGreen(), 255 - new Color((img.getRGB(i, j))).getBlue()).getRGB());
                    }
                }
                rePaint(newImg);
            }
            // Computes the weighted average using the Gaussian Method and blurs the image
            if (command.equals("gblur")) {
                newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                double[][] weightedAverage = new double[5][5];
                double sum = 0;
                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 2; j++) {
                        weightedAverage[i + 2][j + 2] = (1 / (Math.PI * 4.5)) * Math.exp(-((i * i) + (j * j)) / (4.5));
                        sum += weightedAverage[i + 2][j + 2];
                    }
                }
                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 2; j++) {
                        weightedAverage[i + 2][j + 2] = weightedAverage[i + 2][j + 2] / sum;
                    }
                }
                for (int i = 2; i < img.getWidth() - 2; i++) {
                    for (int j = 2; j < img.getHeight() - 2; j++) {
                        newRed = 0;
                        newGreen = 0;
                        newBlue = 0;
                        for (int k = -2; k < 2; k++) {
                            for (int p = -2; p < 2; p++) {
                                newRed += (new Color(img.getRGB(i + k, j + p)).getRed() * weightedAverage[k + 2][p + 2]);
                                newGreen += (new Color(img.getRGB(i + k, j + p)).getGreen() * weightedAverage[k + 2][p + 2]);
                                newBlue += (new Color(img.getRGB(i + k, j + p)).getBlue() * weightedAverage[k + 2][p + 2]);
                            }
                        }
                        newImg.setRGB(i, j, new Color((int) newRed, (int) newGreen, (int) newBlue).getRGB());
                    }
                }
                rePaint(newImg);
            }
            // Bulges the image, with a k value of 2
            if (command.equals("beffect")) {
                double radius, theta;
                int newI, newJ, midX = (img.getWidth() / 2), midY = (img.getHeight() / 2);
                newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                for (int i = 0; i < img.getWidth(); i++) {
                    for (int j = 0; j < img.getHeight(); j++) {
                        radius = Math.sqrt(Math.pow((i - midX), 2) + Math.pow((j - midY), 2));
                        radius = (Math.pow(radius, 2)) / (img.getWidth() / 3);
                        theta = Math.atan2(i - midX, j - midY);
                        newJ = (int) ((radius * Math.cos(theta)) + midY);
                        newI = (int) ((radius * Math.sin(theta)) + midX);
                        if (newI < img.getWidth() & newJ < img.getHeight() & newI >= 0 & newJ >= 0) {
                            newImg.setRGB(i, j, img.getRGB(newI, newJ));
                        }
                    }
                }
                rePaint(newImg);
            }
        }

        public static class ImageFilter extends FileFilter {

            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    String extension;
                    String fileName = file.getName();
                    int index = fileName.lastIndexOf('.');
                    if (index > 0 && index < (fileName.length() - 1)) {
                        extension = fileName.substring(index + 1).toLowerCase();
                    } else {
                        extension = null;
                    }
                    if (extension != null) {
                        if (extension.equals("jpeg") || extension.equals("jpg") || extension.equals("gif") || extension.equals("tiff") || extension.equals("tif") || extension.equals("png")) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }

            public String getDescription() {
                return "Images";
            }
        }
    }
}
