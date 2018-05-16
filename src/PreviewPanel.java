import Game.*;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class PreviewPanel extends JPanel {

    Landscape localLandscape;

    public PreviewPanel(FlowLayout layout)
    {
        super(layout);
        localLandscape = null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(localLandscape != null)
        {
            for(Iterator<Drawable> iterator = localLandscape.objects.iterator(); iterator.hasNext();) {
                Drawable d = iterator.next();
                d.paint(g);
            }
        }
    }

    void setLocalLandscape(Landscape remoteLandscape) {
        localLandscape = remoteLandscape;
    }
}