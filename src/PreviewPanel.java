import Game.*;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class PreviewPanel extends JPanel {

    public Landscape localLandscape;
    private Drawable highlightedObject;

    public PreviewPanel(FlowLayout layout)
    {
        super(layout);
        localLandscape = null;
        highlightedObject = null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(localLandscape != null)
        {
            for (Drawable d : localLandscape.objects) {
                d.paint(g);
            }

            if(highlightedObject != null) {
                g.setColor(Color.MAGENTA);
                g.drawRect((int) highlightedObject.position.x, (int) highlightedObject.position.y, highlightedObject.width, highlightedObject.height);
            }
        }
    }

    public void highlightObject(Drawable d) {
        if(d != null) highlightedObject = d;
        else highlightedObject = null;

        repaint();
    }

    void setLocalLandscape(Landscape remoteLandscape) {
        localLandscape = remoteLandscape;
    }
}