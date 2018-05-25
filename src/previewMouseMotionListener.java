import Game.Drawable;

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
        Drawable d = PreviewPanel.getHighlightedObject();
        if(d != null){
            d.position.x = e.getX() - d.width/2;
            d.position.y = e.getY() - d.height/2;
            ((PreviewPanel) e.getSource()).getRootPane().repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseLabel.setText("Mouse-Coords: X = " + e.getX() + " | Y = " + e.getY());
    }
}
