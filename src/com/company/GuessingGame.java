package com.company;

import java.util.Random;

/**
 * Created by abendlied on 4/28/17.
 */
public class GuessingGame {

    public int omiGuess;
    boolean finished;

    public GuessingGame(){
        Random num = new Random();
        omiGuess = num.nextInt(9) + 1;
        finished = false;
    }



    String takeGuess(int guess){
        if (guess > omiGuess){
            return "Nope, it's lower than that!";
        }
        else if (guess < omiGuess){
            return "Nope, it's higher than that!";
        }
        else if (guess == omiGuess){
            finished = true;
            return "Yay! That's my number!";
        }
        else {
            return "Hmm, it's not that. Better try again!";
        }
    }

    boolean isFinished(){
        return finished;
    }


}
