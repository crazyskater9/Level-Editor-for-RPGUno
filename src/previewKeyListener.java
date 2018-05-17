import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class previewKeyListener implements KeyListener {
    private JScrollPane scrollPane;

    public previewKeyListener(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getValue() - 20);
                break;
            case KeyEvent.VK_A:
                scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() - 20);
                break;
            case KeyEvent.VK_S:
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getValue() + 20);
                break;
            case KeyEvent.VK_D:
                scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() + 20);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
