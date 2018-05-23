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
    private JPanel objectSettingsPanel, infoPanel, editPanel;
    private JList<Drawable> objectList;
    private DefaultListModel<Drawable> listModel;
    private PreviewPanel preview;
    private JScrollPane previewScrollPane, objectListScrollPane;
    private JMenuBar menuBar;
    private JLabel fileLabel;
    private JFileChooser fileChooser;
    private Landscape landscape;
    private JLabel[] objectListLabels;
    private JTextField[] objectListTextFields;


    public LevelEditor() {
        fileChooser = new JFileChooser("./../RPGUno/levels");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Level-Files","map"));

        initMenu();
        initPanels();
        initFrame();
    }

    private void initMenu() {

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

    private void initPanels(){
        infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setPreferredSize(new Dimension(200, 50));
        fileLabel = new JLabel("No File selected!");
        infoPanel.add(fileLabel);
        JLabel mouseLabel = new JLabel("Mouse-Coords: X =  | Y =  ");
        infoPanel.add(mouseLabel);

        listModel = new DefaultListModel<>();
        objectList = new JList<>(listModel);
        objectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        objectList.setLayoutOrientation(JList.VERTICAL);
        objectList.setDragEnabled(false);
        objectList.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
//                    System.out.println(objectList.getSelectedValue());
                preview.highlightObject(objectList.getSelectedValue());
                setObjectSettingsPanel(objectList.getSelectedValue());
            }
        });
        objectListScrollPane = new JScrollPane(objectList);
        objectListScrollPane.setPreferredSize(new Dimension(200,200));

        objectSettingsPanel = new JPanel(new SpringLayout());
        objectSettingsPanel.setBackground(Color.WHITE);
        objectSettingsPanel.setPreferredSize(new Dimension(200, 330));
        JLabel label = new JLabel("Object-Settings: ");
        objectSettingsPanel.add(label);

        editPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        editPanel.setPreferredSize(new Dimension(200,580));
        editPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        editPanel.setBackground(Color.WHITE);
        editPanel.add(infoPanel);
        editPanel.add(objectListScrollPane);
        editPanel.add(objectSettingsPanel);

        preview = new PreviewPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        preview.setBackground(Color.BLACK);
        preview.setPreferredSize(new Dimension(600, 580));
        preview.addMouseMotionListener(new previewMouseMotionListener(mouseLabel));
        preview.addMouseListener(new previewMouseListener(preview, objectList));
        previewScrollPane = new JScrollPane(preview);
        previewScrollPane.setPreferredSize(new Dimension(600,580));
        previewScrollPane.getVerticalScrollBar().setUnitIncrement(10);
    }

    private void initFrame(){
        frame = new JFrame("Level Editor for RPGUno");

        frame.setJMenuBar(menuBar);
        frame.add(editPanel, BorderLayout.WEST);
        frame.add(previewScrollPane, BorderLayout.EAST);
        frame.addKeyListener(new previewKeyListener(previewScrollPane));

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

    private void newFile() {

    }

    private void loadFile() {
        if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                landscape = new Landscape(fileChooser.getSelectedFile().getAbsolutePath());
                fileLabel.setText(fileChooser.getSelectedFile().getName());
                preview.setLocalLandscape(landscape);
                preview.setPreferredSize(new Dimension(Landscape.WIDTH, Landscape.HEIGHT));
                preview.repaint();

                if(!landscape.objects.isEmpty()) {
                    listModel.removeAllElements();
                    for(Drawable d : landscape.objects) {
                        listModel.addElement(d);
                    }
                }

            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            System.out.println("No file selected!");
        }


    }

    private void saveFile() {

    }

    private void setObjectSettingsPanel(Drawable d) {
        SpringLayout layout = (SpringLayout) objectSettingsPanel.getLayout();
        objectSettingsPanel.removeAll();
        switch (d.getClass().getName())
        {
            case "Game.Player":
                objectListLabels = new JLabel[2];
                objectListTextFields = new JTextField[2];
                break;
            case "Game.Wall":
                objectListLabels = new JLabel[2];
                objectListTextFields = new JTextField[2];
                break;
            case "Game.Ground":
                objectListLabels = new JLabel[2];
                objectListTextFields = new JTextField[2];
                break;
        }


        objectSettingsPanel.add(new JLabel("Object-Settings: "));

        objectListLabels[0] = new JLabel("X: ");
        objectListTextFields[0] = new JTextField(Float.toString(d.position.x));
        objectSettingsPanel.add(objectListLabels[0]);
        objectSettingsPanel.add(objectListTextFields[0]);
        layout.putConstraint(SpringLayout.NORTH, objectListLabels[0], 40, SpringLayout.NORTH, objectSettingsPanel);
        layout.putConstraint(SpringLayout.WEST, objectListLabels[0], 10, SpringLayout.WEST, objectSettingsPanel);
        layout.putConstraint(SpringLayout.EAST, objectListTextFields[0], -20, SpringLayout.EAST, objectSettingsPanel);
        layout.putConstraint(SpringLayout.NORTH, objectListTextFields[0], 0, SpringLayout.NORTH, objectListLabels[0]);

        objectListLabels[1] = new JLabel("Y: ");
        objectListTextFields[1] = new JTextField(Float.toString(d.position.y));
        objectSettingsPanel.add(objectListLabels[1]);
        objectSettingsPanel.add(objectListTextFields[1]);
        layout.putConstraint(SpringLayout.NORTH, objectListLabels[1], 80, SpringLayout.NORTH, objectSettingsPanel);
        layout.putConstraint(SpringLayout.WEST, objectListLabels[1], 10, SpringLayout.WEST, objectSettingsPanel);
        layout.putConstraint(SpringLayout.EAST, objectListTextFields[1], -20, SpringLayout.EAST, objectSettingsPanel);
        layout.putConstraint(SpringLayout.NORTH, objectListTextFields[1], 0, SpringLayout.NORTH, objectListLabels[1]);

        objectSettingsPanel.updateUI();
    }
}
