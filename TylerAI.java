import java.util.*;
import java.awt.Point;

/**
 * A simple AI with some "smartness"
 *
 * If a move exists that will win the game next turn, it will take that move.
 * If a move exists for the enemy that will win the game for them, it will take the move to block them.
 * Otherwise it will just choose a random move.
 *
 * @author Tyler Dahl/tiedie211
 */
public class TylerAI extends AI {

   private static final int BOARD_SIZE = 3;

   private char enemySide;

   public TylerAI() {
      name = "Tyler";
   }

   @Override
   public Point move(TTTBoard board) {
      enemySide = (side == 'x') ? 'o' : 'x';

      // If I can win, do that move
      List<TTTBoard> myMoves = possibleMoves(board, side);
      for (TTTBoard move : myMoves) {
         if (move.winCheck() == side) {
            System.out.println("Win: " + getNewMove(board, move));
            return getNewMove(board, move);
         }
      }

      // If the enemy can win, block them
      List<TTTBoard> enemyMoves = possibleMoves(board, enemySide);
      for (TTTBoard move : enemyMoves) {
         if (move.winCheck() == enemySide) {
            System.out.println("Lose: " + getNewMove(board, move));
            return getNewMove(board, move);
         }
      }

      // Otherwise, choose random
      return getNewMove(board, myMoves.get((int)(Math.random() * myMoves.size())));
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