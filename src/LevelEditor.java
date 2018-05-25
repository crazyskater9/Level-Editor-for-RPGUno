import Game.*;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;

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
    private JLabel[] objectSettingsLabels;
    private JTextField[] objectSettingsTextFields;
    private JButton addObjectButton, removeObjectButton;


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
        infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setPreferredSize(new Dimension(200, 60));
        fileLabel = new JLabel("No File selected!");
        infoPanel.add(fileLabel);
        JLabel mouseLabel = new JLabel("Mouse-Coords: X =  | Y =  ");
        infoPanel.add(mouseLabel);
        addObjectButton = new JButton("+");
        addObjectButton.setBackground(Color.GREEN);
        addObjectButton.setVisible(false);
        addObjectButton.addActionListener(this);
        infoPanel.add(addObjectButton);
        removeObjectButton = new JButton("x");
        removeObjectButton.setBackground(Color.RED);
        removeObjectButton.setVisible(false);
        removeObjectButton.addActionListener(this);
        infoPanel.add(removeObjectButton);

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
                removeObjectButton.setVisible(true);
            }
        });
        objectListScrollPane = new JScrollPane(objectList);
        objectListScrollPane.setPreferredSize(new Dimension(200,200));

        objectSettingsPanel = new JPanel(new SpringLayout());
        objectSettingsPanel.setBackground(Color.WHITE);
        objectSettingsPanel.setPreferredSize(new Dimension(200, 320));
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
            case "+":
                addObjectToLandscape();
                break;
            case "x":
                removeObjectFromLandscape();
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
                addObjectButton.setVisible(true);
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

    private void removeObjectFromLandscape() {
        Drawable selectedValue = objectList.getSelectedValue();
        Drawable d;
        for(Iterator<Drawable> iterator = landscape.objects.iterator(); iterator.hasNext();) {
            d = iterator.next();
            if(d.equals(selectedValue)){
                listModel.removeElement(d);
                if(listModel.isEmpty()) removeObjectButton.setVisible(false);
                iterator.remove();
                preview.repaint();
                editPanel.updateUI();
            }
        }
    }

    private void addObjectToLandscape() {
//        WIP
//        JDialog dialog = new JDialog ();
//        dialog.setTitle("Add Object");
//        dialog.setModal(true);
//        dialog.setAlwaysOnTop(true);
//        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
//        dialog.setLocationRelativeTo(null);
//        dialog.setResizable(false);
//        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        dialog.pack();
//        dialog.setVisible(true);
    }

    private void setObjectSettingsPanel(Drawable d) {
        int position = 0;
        SpringLayout layout = (SpringLayout) objectSettingsPanel.getLayout();
        objectSettingsPanel.removeAll();
        objectSettingsPanel.add(new JLabel("Object-Settings: "));

        if(d instanceof Player){
            objectSettingsLabels = new JLabel[3];
            objectSettingsTextFields = new JTextField[3];
        }
        else if(d instanceof Wall){
            objectSettingsLabels = new JLabel[3];
            objectSettingsTextFields = new JTextField[3];
        }
        else if(d instanceof Ground){
            objectSettingsLabels = new JLabel[2];
            objectSettingsTextFields = new JTextField[2];
        }
        else return;

        addDataRowToObjectSettings(layout, position, "X: ", Float.toString(d.position.x), e -> {
            d.position.x = Float.parseFloat(e.getActionCommand());
            preview.repaint();
        });
        position++;

        addDataRowToObjectSettings(layout, position, "Y: ", Float.toString(d.position.y), e -> {
            d.position.y = Float.parseFloat(e.getActionCommand());
            preview.repaint();
        });
        position++;



        if(d instanceof Player){
            addDataRowToObjectSettings(layout, position, "Health: ", Integer.toString(d.health), e -> {
                d.health = (int) Float.parseFloat(e.getActionCommand());
                preview.repaint();
            });
            position++;
        }
        else if(d instanceof Wall){
            addDataRowToObjectSettings(layout, position, "Health: ", Integer.toString(d.health), e -> {
                d.health = (int) Float.parseFloat(e.getActionCommand());
                preview.repaint();
            });
            position++;
        }
        else if(d instanceof Ground){

        }

        objectSettingsPanel.updateUI();
    }

    private void setLayoutConstraints(SpringLayout layout, int position) {
        layout.putConstraint(SpringLayout.NORTH, objectSettingsLabels[position], position * 30 + 40, SpringLayout.NORTH, objectSettingsPanel);
        layout.putConstraint(SpringLayout.WEST, objectSettingsLabels[position], 10, SpringLayout.WEST, objectSettingsPanel);
        layout.putConstraint(SpringLayout.EAST, objectSettingsTextFields[position], -20, SpringLayout.EAST, objectSettingsPanel);
        layout.putConstraint(SpringLayout.NORTH, objectSettingsTextFields[position], 0, SpringLayout.NORTH, objectSettingsLabels[position]);
    }

    private void addDataRowToObjectSettings(SpringLayout layout, int position, String labelString, String textFieldString, ActionListener actionListener){
        objectSettingsLabels[position] = new JLabel(labelString);
        objectSettingsTextFields[position] = new JTextField(textFieldString);
        objectSettingsTextFields[position].setPreferredSize(new Dimension(40,20));
        objectSettingsTextFields[position].setHorizontalAlignment(JTextField.RIGHT);
        objectSettingsTextFields[position].addActionListener(actionListener);
        objectSettingsPanel.add(objectSettingsLabels[position]);
        objectSettingsPanel.add(objectSettingsTextFields[position]);
        setLayoutConstraints(layout, position);
    }
}
