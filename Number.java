import java .util.Random;
import java.util.Scanner;
public class Number{
    public static void main(String[] args) {
        Random Random_number=new Random();
        int right_guess=Random_number.nextInt(100);
        int turns=0;
        Scanner scan=new Scanner(System.in);
        System.out.println("guess a number between 1 to 100,you have 10 turns");
        int guess;
        int i=0;
        boolean win=false;
        while(win==false){
            guess=scan.nextInt();
            turns++;
            if(guess==right_guess){
                win=true;
            }
            else if(i>8){
                System.out.println("you loose!right answer was:"+right_guess);
                return;
            }
            else if(guess<right_guess){
                i++;
                System.out.println("your guess is lower then right answer,turns left:"+(10-i));
            }
            else if(guess>right_guess){
                i++;
                System.out.println("your guess is higher then right answer,turns left:"+(10-i));
            }
        }
        System.out.println("you win");
        System.out.println("thenumber is"+right_guess);
        System.out.println("you used"+turns);
        System.out.println("score is"+((11-turns)*10)+"ot of 100");
    }
}
