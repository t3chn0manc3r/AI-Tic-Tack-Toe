import java.util.*;
import java.awt.Point;

/**
 * AI created by Tyler Dahl
 *
 * Utilizes minimax algorithm to choose the best move based upon possible future moves.
 * It assumes that the opponent always chooses the best possible move they can make.
 * 
 * The first player always chooses the move that will maximize their score.
 * The second player always chooses the move that will minimize their score.
 * 
 * In this case, scores range from 0-10
 * - 10 being guaranteed win for first player
 * - 0 being guaranteed win for second player
 * - 5 being guaranteed tie (unless the opponent messes up, in which case a win is possible)
 *
 * @author Tyler Dahl/tiedie211 
 */
public class MinimaxAI extends AI {

   private char enemySide;
   private int nodesGenerated = 0;

   public MinimaxAI() {
      name = "Minimax";
   }

   @Override
   public Point move(TTTBoard board) {
      enemySide = (side == 'x') ? 'o' : 'x';

      int bestValue = Integer.MIN_VALUE;
      int bestIndex = 0;

      List<TTTBoard> possibleMoves = possibleMoves(board, side);
      Map<Integer, List<Point>> possibleMovesMap = new HashMap<>();
      for (int i = 0; i < possibleMoves.size(); i++) {

         // Use minimax to calculate the best course of action.
         Integer value = minimax(possibleMoves.get(i), possibleMoves.size() - 1, false);
         System.out.printf("%s = %s\n", getNewMove(board, possibleMoves.get(i)), value);
         
         // Add this move to the map of possible moves, using its value as the key
         List<Point> moves = possibleMovesMap.get(value);
         if (moves == null) {
            moves = new ArrayList<>();
         }
         moves.add(getNewMove(board, possibleMoves.get(i)));
         possibleMovesMap.put(value, moves);

         // Remember best value
         if (value > bestValue) {
            bestValue = value;
            bestIndex = i;
         }
      }

      // Print the number of nodes generated for minimax algorithm
      System.out.println("Total nodes generated: " + nodesGenerated);
      nodesGenerated = 0;

      // Return a random move from the list of best moves.
      List<Point> moves = possibleMovesMap.get(bestValue);
      return moves.get((int)(Math.random() * moves.size()));
   }

   private int minimax(TTTBoard node, int depth, boolean myTurn) {
      nodesGenerated++;
      if (hasSolution(node, side)) return 10;
      if (hasSolution(node, enemySide)) return 0;
      if (depth == 0) return 5;

      if (myTurn) {
         int bestValue = Integer.MIN_VALUE;
         for (TTTBoard move : possibleMoves(node, side)) {
            Point newMove = getNewMove(node, move);
            int value = minimax(move, depth - 1, !myTurn);
            bestValue = Math.max(bestValue, value);

            // Prune tree
            if (bestValue >= 10) { // guaranteed win
               break;
            }
         }
         return bestValue;
      } else {
         int bestValue = Integer.MAX_VALUE;
         for (TTTBoard move : possibleMoves(node, enemySide)) {
            Point newMove = getNewMove(node, move);
            int value = minimax(move, depth - 1, !myTurn);
            bestValue = Math.min(bestValue, value);

            // Prune tree
            if (bestValue <= 0) { // guaranteed loss
               break;
            }
         }
         return bestValue;
      }
   }

   private boolean hasSolution(TTTBoard board, char side) {
      return board.winCheck() == side;
   }

   private List<TTTBoard> possibleMoves(TTTBoard board, char side) {
      List<TTTBoard> moves = new ArrayList<>();

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            if (board.checkSpace(new Point(i, j)) == ' ') {
               TTTBoard temp = board.getBoardCopy();
               temp.place(side, new Point(i, j));
               moves.add(temp);
            }
         }
      }

      return moves;
   }

   private Point getNewMove(TTTBoard oldBoard, TTTBoard newBoard) {
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            if (oldBoard.checkSpace(new Point(i, j)) != newBoard.checkSpace(new Point(i, j))) {
               return new Point(i, j);
            }
         }
      }
      return null;
   }
}