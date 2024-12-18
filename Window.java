package breakthrough;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends BaseWindow {// handles user interactions, game board updates, and player turns.
    private int size;// size x size grid
    private JButton[][] buttons;
    private Model model;
    private final JLabel label;  // Displays whose turn it is
    private int fromRow = -1, fromCol = -1;  
    public Window(int size) {
        this.size = size;
        this.model = new Model(size);
        setSize(1300, 1300);
        
        setLocationRelativeTo(null);// Center the window on the screen

        JPanel top = new JPanel();
        label = new JLabel();
        updateLabelText();  

        JButton newGameButton = new JButton("New game");
        newGameButton.addActionListener(e -> newGame());//The button has an ActionListener attached that calls the newGame() method.

        top.add(label);
        top.add(newGameButton);

        JPanel mainPanel = new JPanel(new GridLayout(size, size));
        buttons = new JButton[size][size];

        //Creating Game Board Buttons>>creates a grid of buttons corresponding to the game board:
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50, 50));
                button.setBackground(Color.WHITE);
                button.setFocusPainted(false);//Prevents the button from showing a focus indicator when clicked.
                button.addActionListener(new ButtonClickListener(i, j));  // Each button gets an ActionListener attached that handles clicks by invoking the ButtonClickListener class.,,Add listener for clicks
                mainPanel.add(button);
                buttons[i][j] = button;
            }
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
        updateBoardUI();
    }


    
    private void updateLabelText() {
    label.setText("Player " + model.getCurrentPlayer() + "'s Turn");
}



    private void newGame() {
        model = new Model(size);  // Reset the game model
        fromRow = -1;//Resets the fromRow and fromCol to -1 (clearing the selected piece).
        fromCol = -1;
        updateBoardUI();  // (WE HAVE CREATED A NEW MODEL)
        updateLabelText();  //(WE HAVE CREATED A NEW MODEL) 
    }

    // Update the board UI to reflect the current state of the game
    private void updateBoardUI() {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            if (model.getBoard()[i][j] == Player.A) {
                buttons[i][j].setIcon(new ImageIcon(getClass().getResource("/WhiteDollForA.png")));
                
            } else if (model.getBoard()[i][j] == Player.B) {
                buttons[i][j].setIcon(new ImageIcon(getClass().getResource("/BrownDollForB.png")));
               
            } else {
                buttons[i][j].setIcon(null);
            }
        }
    }
}
    
    
private class ButtonClickListener implements ActionListener {  // Implements the ActionListener interface to handle button clicks.
    private int row, col;  // Store the row and column positions of the button that was clicked.

    public ButtonClickListener(int row, int col) {
        this.row = row;
        this.col = col;  // Used to identify the exact cell the player is interacting with.
    }

    @Override
    public void actionPerformed(ActionEvent e) {  // Defines what happens when a button is clicked.
        if (fromRow == -1 && fromCol == -1) {  // If no piece is selected yet
            // Check if selected piece belongs to the current player
            if (model.getBoard()[row][col] == model.getCurrentPlayer()) {
                fromRow = row;
                fromCol = col;  // Record the selected piece's position
                updateLabelText();  // Update the turn label after selection
            } else {
                System.out.println("Move failed: Selected piece does not belong to the current player.");
            }
        } else {  // If a piece has been selected, attempt to move it
            // Make the move and check if it resulted in a win
            if (model.makeMove(fromRow, fromCol, row, col)) {  // Attempt the move
                updateBoardUI();  // Update the board UI after move

                if (model.checkWinCondition()) {  // Check if the game has been won
                    String winner = model.getCurrentPlayer() == Player.A ? "Player A" : "Player B";
                    JOptionPane.showMessageDialog(Window.this, winner + " wins!");

                    newGame();  // Reset the game after win
                } else {
                    updateLabelText();  // Update the turn label if no win
                }

                // Reset selection for the next turn
                fromRow = -1;
                fromCol = -1;
            }
        }
    }
}















    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Window(6));  // Set up the game window with a board size of 6
    }
}
