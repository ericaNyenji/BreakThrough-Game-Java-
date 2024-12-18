package breakthrough;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends BaseWindow {
    
    private List<Window> gameWindows = new ArrayList<>();

    public MainWindow() {
        JButton small = new JButton("6 x 6");
        small.addActionListener(getActionListener(6));

        JButton medium = new JButton("8 x 8");
        medium.addActionListener(getActionListener(8));

        JButton large = new JButton("10 x 10");
        large.addActionListener(getActionListener(10));
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(small);
        getContentPane().add(medium);
        getContentPane().add(large);
   
        setPreferredSize(new Dimension(400, 450)); // Set preferred size for MainWindow       
        pack();
        setVisible(true);
    }

    
    private ActionListener getActionListener(final int size) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window gameWindow = new Window(size);
                gameWindow.setVisible(true);
                gameWindows.add(gameWindow);
            }
        };
    }

    public List<Window> getWindowList() {
        return gameWindows;
    }

    @Override
    protected void doUponExit() {
        System.exit(0);
    }
}
