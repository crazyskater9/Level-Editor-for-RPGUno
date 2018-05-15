import Game.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class LevelEditor implements ActionListener {
    private JFrame frame;
    private JPanel objectSettings, preview;
    private JMenuBar menuBar;
    private JLabel label;
    private JFileChooser fileChooser;
    private Landscape landscape;


    public LevelEditor() {
        fileChooser = new JFileChooser("./../RPGUno/levels");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Level-Files","map"));

        initMenu();
        initPanels();
        initFrame();
    }

    void initMenu() {

        menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("New File", KeyEvent.VK_N);
        menuItem.getAccessibleContext().setAccessibleDescription("Create a new File");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Load", KeyEvent.VK_L);
        menuItem.getAccessibleContext().setAccessibleDescription("Load a File");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save", KeyEvent.VK_S);
        menuItem.getAccessibleContext().setAccessibleDescription("Save a File");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
    }

    void initPanels(){
        objectSettings = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        objectSettings.setBackground(Color.GRAY);
        objectSettings.setPreferredSize(new Dimension(200, 580));
        label = new JLabel("No File selected!");
        objectSettings.add(label);

        preview = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        preview.setBackground(Color.BLACK);
        preview.setPreferredSize(new Dimension(600, 580));
    }

    void initFrame(){
        frame = new JFrame("Level Editor for RPGUno");

        frame.setJMenuBar(menuBar);
        frame.add(objectSettings, BorderLayout.WEST);
        frame.add(preview, BorderLayout.EAST);

        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "New File":
                newFile();
                break;
            case "Load":
                loadFile();
                break;
            case "Save":
                saveFile();
                break;
        }
    }

    void newFile() {

    }

    void loadFile() {
        if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                landscape = new Landscape(fileChooser.getSelectedFile().getAbsolutePath());
//                System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
                label.setText(fileChooser.getSelectedFile().getName());
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            System.out.println("No file selected!");
        }


    }

    void saveFile() {

    }
}
