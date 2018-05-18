import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class previewMouseMotionListener implements MouseMotionListener {


    private final JLabel mouseLabel;

    public previewMouseMotionListener(JLabel mouseLabel) {
        this.mouseLabel = mouseLabel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseLabel.setText("Mouse-Coords: X = " + e.getX() + " | Y = " + e.getY());
    }
}
