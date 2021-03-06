import Game.*;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class PreviewPanel extends JPanel {

    public Landscape localLandscape;
    private static Drawable highlightedObject;

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
                if(d instanceof Player){
                    g.drawImage(d.gameImage.image, (int) d.position.x, (int) d.position.y, null);
                }
                else {
                    d.paint(g);
                }

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

    static Drawable getHighlightedObject(){
        return highlightedObject;
    }
}