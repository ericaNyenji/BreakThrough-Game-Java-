package breakthrough;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class BaseWindow extends JFrame {
    
    public BaseWindow() {
        setTitle("Break-Through");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); 
        
        addWindowListener(new WindowAdapter() {//This method registers a listener that reacts to window events (like opening, closing, etc.).
          
            @Override
            public void windowClosing(WindowEvent e) {
                // This method is triggered when the user attempts to close the window (clicks the "X" button). Inside this method, 
               
                showExitConfirmation();
            }
        });
    }

    private void showExitConfirmation() {
        int n = JOptionPane.showConfirmDialog(this, "Do you really want to quit?",
                "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            doUponExit();
        }
    }
    
    protected void doUponExit() {
        dispose(); // Default behavior: close the window
        // This method is used to release the resources used by the window and close it. 
        
    }
}
