import java.util.*;
import java.awt.Point;

public class Human extends AI {

   private Scanner in = new Scanner(System.in);

   public Human() {
      System.out.println("What is your name?");
      name = in.nextLine().trim();
   }

   @Override
   Point move(TTTBoard b) {

      // Prompt the user to make a move
      System.out.println("Make a move:");
      System.out.println("[0] top left    [1] top center    [2] top right");
      System.out.println("[3] center left [4] center center [5] center right");
      System.out.println("[6] bottom left [7] bottom center [8] bottom right");

      // Get the move the user wants to make
      do {
         String choice = in.next().trim();

         if (choice.length() == 1 && choice.charAt(0) >= '0' && choice.charAt(0) <= '8') {
            int position = choice.charAt(0) - '0';
            return new Point(position % 3, position / 3);
         }
         
         System.out.println("Bad Input... Try Again...");
      } while (true);
   }
}