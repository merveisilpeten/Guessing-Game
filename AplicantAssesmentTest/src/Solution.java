// importing necessary packages
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

// Solution class includes the game
public class Solution {

    public static void main(String[] args){

        // generating a random number for user to predict
        Random gen= new Random();
        int target= 0;
        //checks if user enters the same number more than one
        while(hasDupes(target= (gen.nextInt(9000) + 1000)));
        String targetStr = target +"";
        boolean guessed = false;
        //takes user prediction
        Scanner input = new Scanner(System.in);
        Set<String> possibleNums = generatePossibleNums();
        int guesses = 0;
        do{
            // Part1: User guess
            int plus = 0; // the numbers that are true and in correct position
            int minus = 0; // the numbers that are true but in the wrong position
            System.out.println("Guess a 4-digit number with no duplicate digits: ");
            int guess;
            try{
                guess = input.nextInt();
                if(hasDupes(guess) || guess < 1000 || guess > 9999) continue;
            }catch(InputMismatchException e){
                continue;
            }
            guesses++;
            String guessStr = guess + "";
            //comparing the numbers if they are matching with each other
            for(int i= 0;i < 4;i++){
                if(guessStr.charAt(i) == targetStr.charAt(i)){
                    plus++;
                }else if(targetStr.contains(guessStr.charAt(i)+"")){
                    minus++;
                }
            }
            // when all numbers are true and in correct place the game ends
            if(plus == 4){
                guessed = true;
                System.out.println("You won after "+guesses+" guesses!");
                break;
            }else{
                System.out.println("-" + minus+" +"+ plus);
            }

            // Part2: Computer guess
            Iterator<String> iter = possibleNums.iterator();
            String AIguess = iter.next();
            // user gives hint to the computer
            System.out.println("My guess is: " + AIguess);
            System.out.print("Number of negatives :");
            int numberOfMinus = input.nextInt();
            System.out.print("Number of positives:");
            int numberOfPlus = input.nextInt();
            removeWrongNums(new PosNegCount(numberOfPlus, numberOfMinus), AIguess, possibleNums);
            if (numberOfPlus == 4) {
                guessed = true;
                System.out.println("Computer wins in " + guesses + " guesses");
                break;
            }
        }while(!guessed);
    }

    public static boolean hasDupes(int num){
        boolean[] digs = new boolean[10];
        while(num > 0){
            if(digs[num%10]) return true;
            digs[num%10] = true;
            num/= 10;
        }
        return false;
    }
    // all possibilities are created looking at the hints of the user
    public static Set<String> generatePossibleNums() {
        Set<String> set = new HashSet<String>();
        for (int i = 1023; i < 9876; i++) {
            set.add(String.valueOf(i));
        }
        Iterator<String> iter = set.iterator();
        while (iter.hasNext()) {
            String str = iter.next();
            Set<Character> digits = new HashSet<>();
            for (char c : str.toCharArray()) {
                if (digits.contains(c)) {
                    iter.remove();
                    break;
                }
                digits.add(c);
            }
        }
        return set;
    }
    // the numbers that cannot be the true one are eliminated using hints of the user
    public static void removeWrongNums(PosNegCount guessPosNegCount, String guess,
                                       Set<String> possibleNums) {
        Iterator<String> iter = possibleNums.iterator();
        while (iter.hasNext()) {
            String str = iter.next();
            if (calcPlusandMinusCount(guess, str).equals(guessPosNegCount) == false) {
                iter.remove();
            }
        }
    }

    // this method calculates the positive and negative values and returns that count
    public static PosNegCount calcPlusandMinusCount(String guess, String candidate) {
        PosNegCount pnCount = new PosNegCount(0, 0);
        for (int i = 0; i < candidate.length(); i++) {
            if (guess.charAt(i) == candidate.charAt(i)) {
                pnCount.positiveCount++;
            } else if (guess.contains(String.valueOf(candidate.charAt(i)))) {
                pnCount.negativeCount++;
            }
        }
        return pnCount;
    }


}
