import Game.Drawable;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class previewMouseListener implements MouseListener {

    private PreviewPanel previewPanel;
    private JList<Drawable> jList;

    public previewMouseListener(PreviewPanel previewPanel, JList<Drawable> jList) {
        this.previewPanel = previewPanel;
        this.jList = jList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        try {
            if(!previewPanel.localLandscape.objects.isEmpty()) {
                for(Drawable d : previewPanel.localLandscape.objects) {
                    if(mouseX >= d.position.x && mouseX <= d.position.x + d.width
                            && mouseY >= d.position.y && mouseY <= d.position.y + d.height) {
                        jList.setSelectedValue(d,true);
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Nothing to be clicked on! " + ex);
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
