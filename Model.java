package breakthrough;
public class Model {
    private Player[][] board;
    private Player currentPlayer;

    public Model(int size) {
        board = new Player[size][size];
        currentPlayer = Player.A;  // Player A starts first
        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize the board with empty cells
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Player.NONE;
            }
        }

        // Place Player A's pieces on the top two rows
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < board[0].length; col++) {
                board[row][col] = Player.A;
            }
        }

        // Place Player B's pieces on the bottom two rows
        for (int row = board.length - 2; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                board[row][col] = Player.B;
            }
        }
    }

    public Player[][] getBoard() {
        return board;//returns the current state of the board.
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // Switch player turn
    public void switchPlayer() {
        currentPlayer = (currentPlayer == Player.A) ? Player.B : Player.A;
    }

    // Check for a win condition (reaches the opposite row)
    public boolean checkWinCondition() {
        //We already have the current player after he makes a move before switching to the next player
        //So for the current player that has already made a move we calculate his target row
        int targetRow = (currentPlayer == Player.A) ? board.length - 1 : 0;
        
        //If we find that in the target row, is also where the current player is we return winner as the current player
        //And we dont switch players
        for (int col = 0; col < board[0].length; col++) {
            if (board[targetRow][col] == currentPlayer) {
                return true;
            }
        }
        return false;
    }

    
    public boolean makeMove(int fromRow, int fromCol, int toRow, int toCol) {
    // Ensure the move is within bounds
    if (toRow < 0 || toRow >= board.length || toCol < 0 || toCol >= board[0].length) {
        return false;
    }

    // Ensure the starting cell has the current player's piece
    if (board[fromRow][fromCol] != currentPlayer) {
        return false;
    }

    // Calculate row direction based on the current player
    int rowDirection = currentPlayer == Player.A ? 1 : -1; // A moves down (+1), B moves up (-1)

    // Move forward (one step in the row direction)
    if (toRow == fromRow + rowDirection && toCol == fromCol && board[toRow][toCol] == Player.NONE) {
        // Forward move to an empty cell
        board[toRow][toCol] = currentPlayer;
        board[fromRow][fromCol] = Player.NONE;

        // Check for win after moving
        if (checkWinCondition()) {
            System.out.println("Player " + currentPlayer + " wins!");  // Print winner message
            return true;  // Return true to indicate a win and halt further action
        }

        // If no win, switch player
        switchPlayer();
        return true;

    // Move diagonally forward (one step diagonally in the row direction)
    } else if (toRow == fromRow + rowDirection && (toCol == fromCol + 1 || toCol == fromCol - 1)) {
        // Diagonal move to an empty cell or to a cell occupied by the opponent's piece
        if (board[toRow][toCol] == Player.NONE || board[toRow][toCol] == (currentPlayer == Player.A ? Player.B : Player.A)) {
            // Replace the cell with the current player's piece
            board[toRow][toCol] = currentPlayer;
            board[fromRow][fromCol] = Player.NONE;

            // Check for win after moving
            if (checkWinCondition()) {
                System.out.println("Player " + currentPlayer + " wins!");  // Print winner message
                return true;  // Return true to indicate a win
            }

            // If no win, switch player
            switchPlayer();
            return true;
        }
    }

    // Invalid move if none of the conditions are met
    return false;
}


}
