import java.util.*;
import java.awt.Point;

/**
 * AI created by Tyler Dahl
 *
 * Utilizes minimax algorithm to choose the best move based upon possible future moves.
 * It assumes that the opponent chooses the best possible move they can make.
 * 
 * choose the node with the highest value
 * value is calculated from sub-nodes
 * a node has to be "evaluated" - in this case, 
 * 
 * The first player always chooses the move that will maximize their score.
 * The second player always chooses the move that will minimize their score.
 * 
 * In this case, scores range from 0-1
 * - 0 being guaranteed win for second player
 * - 1 being guaranteed win for first player
 *
 * @author Tyler Dahl/tiedie211 
 */
public class MinimaxAI extends AI {

   private char enemySide;

   public MinimaxAI() {
      name = "Minimax";
   }

   @Override
   public Point move(TTTBoard board) {
      enemySide = (side == 'x') ? 'o' : 'x';

      double bestValue = Double.MIN_VALUE;
      int bestIndex = 0;
      List<TTTBoard> possibleMoves = possibleMoves(board, side);
      for (int i = 0; i < possibleMoves.size(); i++) {
         Point newMove = getNewMove(board, possibleMoves.get(i));
         //System.out.println("MyMove: " + (newMove.x + newMove.y * 3) + " Depth: " + possibleMoves.size());
         double value = minimax(possibleMoves.get(i), possibleMoves.size(), false, 0);
         System.out.println(getNewMove(board, possibleMoves.get(i)) + " = " + value);
         // int value = numberOfWins(possibleMoves.get(i), true, side);
         if (value > bestValue) {
            bestValue = value;
            bestIndex = i;
         }
      }
      return getNewMove(board, possibleMoves.get(bestIndex));
   }

   private int numberOfWins(TTTBoard node, boolean myTurn, char side) {
      if (hasSolution(node, side)) return 1;
      if (hasSolution(node, (side == 'x') ? 'o' : 'x')) return 0;
      if (noMoves(node)) return 0;

      int value = 0;
      for (TTTBoard move : possibleMoves(node, (myTurn) ? side : ((side == 'x') ? 'o' : 'x'))) {
         value += numberOfWins(move, !myTurn, side);
      }
      return value;
   }

   private double minimax(TTTBoard node, int depth, boolean myTurn, int rowsChecked) {
      if (hasSolution(node, side)) return 1;
      if (hasSolution(node, enemySide)) return 0;
      if (depth == 1) return 0.5;

      if (myTurn) {
         double bestValue = Double.MIN_VALUE;
         for (TTTBoard move : possibleMoves(node, side)) {
            Point newMove = getNewMove(node, move);
            //System.out.println("MyMove: " + (newMove.x + newMove.y * 3) + " Depth: " + (depth - 1));
            double value = minimax(move, depth - 1, !myTurn, rowsChecked + 1);
            //System.out.println("MyMove: " + (newMove.x + newMove.y * 3) + " Value: " + value + " Depth: " + depth);
            bestValue = Math.max(bestValue, value);
            // Prune tree
            if (bestValue == 1) {
               break;
            }
         }
         return bestValue;
      } else {
         double bestValue = Double.MAX_VALUE;
         for (TTTBoard move : possibleMoves(node, enemySide)) {
            Point newMove = getNewMove(node, move);
            //System.out.println("EnMove: " + (newMove.x + newMove.y * 3) + " Depth: " + (depth - 1));
            double value = minimax(move, depth - 1, !myTurn, rowsChecked + 1);
            //System.out.println("EnMove: " + (newMove.x + newMove.y * 3) + " Depth: " + depth);
            bestValue = Math.min(bestValue, value);
            // Prune tree
            if (bestValue == 0) {
               break;
            }
         }
         return bestValue;
      }
   }

   private boolean forcedToMove(TTTBoard board, char side) {
      char otherSide = (side == 'x') ? 'o' : 'x';
      List<TTTBoard> moves = possibleMoves(board, otherSide);
      for (TTTBoard move : moves) {
         if (move.winCheck() == otherSide) {
            return true;
         }
      }
      return false;
   }

   private boolean hasSolution(TTTBoard board, char side) {
      return board.winCheck() == side;
   }

   private boolean noMoves(TTTBoard board) {
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            if (board.checkSpace(new Point(i, j)) == ' ') {
               return false;
            }
         }
      }
      return true;
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