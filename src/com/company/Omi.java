package com.company;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * Created by abendlied on 4/27/17.
 */
public class Omi {

    private int happiness;
    private int fullness;
    private boolean healthy;
    private long startLife;
    private int snackCounter;
    private long age;
    private boolean playing;
    private GuessingGame game;

    public Omi(){
        happiness = 1;
        fullness = 1;
        healthy = true;
        snackCounter = 0;
        startLife = System.nanoTime();
        playing = false;
    }

    public OmiResponse processIntent(OmiIntent intent){
        String name = intent.getName();
        if (name.equals("PetOmiIntent")){
            return this.petOmi();
        }
        else if (name.equals("Launch")){
            return new OmiResponse("Hello, I am Omi! I am your virtual pet! It is nice to meet you!");
        }
        else if (name.equals("FeedOmiIntent")){
            return this.feedOmi(intent);
        }
        else if (name.equals("StartGameIntent")){
            return this.startGuessingGame(intent);
        }
        else if (name.equals("PlayWithOmiIntent")){
            return this.playGuessingGame(intent);
        }

        return new OmiResponse("Unknown.");
    }

    boolean isPlaying(){
        return playing;
    }

    void setPlaying(boolean i){
        playing = i;
    }

    void setHappiness(int h){
        happiness = h;
    }

    int getHappiness(){
        return happiness;
    }

    void setFullness(int f){
        fullness = f;
    }

    int getFullness(){
        return fullness;
    }

    boolean isHealthy(){
        return healthy;
    }

    void checkandsetHealthy(){
        if (happiness == 0 || fullness == 0 || snackCounter >= 5){
            healthy = false;
        }
        else healthy = true;
    }

    void receiveMedicine(){
        healthy = true;
    }

    void addSnack(){
        snackCounter++;
    }

    int getSnack(){
        return snackCounter;
    }

    String getAge(){
        String unit;
        age = System.nanoTime() - startLife;
        age = TimeUnit.MINUTES.convert(age, TimeUnit.NANOSECONDS);
        unit = "minutes old.";
        if (age > 59){
            age = TimeUnit.HOURS.convert(age, TimeUnit.MINUTES);
            unit = "hours old.";
            if (age > 23){
                age = TimeUnit.DAYS.convert(age, TimeUnit.HOURS);
                unit = "days old.";
            }
        }
        return "Omi is " + age + unit;
    }

    private OmiResponse startGuessingGame(OmiIntent intent){
        this.game = new GuessingGame();
        playing = true;
        return new OmiResponse("Omi is thinking of a number between 1 and 10. Can you guess which one?");
    }

    private OmiResponse playGuessingGame(OmiIntent intent){
        //if Omi isn't playing, response text
        String number = intent.getSlot("number");
        if (number == null){
            return new OmiResponse("Omi is thinking of a number between 1 and 10. Can you guess which one?");
        }
        else if (number.equals(Integer.toString(game.omiGuess))){
            playing = false;
            return new OmiResponse("Yay! That's my number!");
        }
        else if (Integer.parseInt(number) > game.omiGuess){
            return new OmiResponse("Nope, that's too high! Try again!");
        }
        else if (Integer.parseInt(number) < game.omiGuess){
            return new OmiResponse("Nope, that's too low! Try again!");
        }
        else {
            return new OmiResponse("You have to guess a number between 1 and 10!");
        }
    }

    void neglectOmi(){
        fullness = fullness - 1;
        happiness = happiness - 1;
    }

    private OmiResponse petOmi(){
        happiness++;
        return new OmiResponse("Oh thanks! I really like scratches behind my ear!");
    }

    private OmiResponse feedOmi(OmiIntent intent){
        String foodType = intent.getSlot("foodtype");
        if (foodType == null){
            return new OmiResponse("Do you want to feed Omi a snack or a meal?");
        }
        else if (foodType.equals("meal")){
            return feedOmiMeal();
        }
        else if (foodType.equals("snack")){
            return feedOmiSnack();
        }
        else {
            return new OmiResponse("Only snack or meal.");
        }
    }

    private OmiResponse feedOmiMeal(){
        fullness++;
        return new OmiResponse("Mmm, that was very filling!");

    }

    private OmiResponse feedOmiSnack(){
        happiness++;
        snackCounter++;
        return new OmiResponse("Those are my favorite treats! Yay!");
    }

    void giveOmiMedicine(){
        System.out.println("Uh oh! Omi isn't feeling too well. Would you like to give Omi medicine?");
        Scanner player = new Scanner(System.in);
        String response = player.nextLine();
        if (response.equalsIgnoreCase("yes")){
            if (snackCounter == 5){
                snackCounter = 0;
            }
            healthy = true;
            System.out.println("That medicine really helped Omi. She feels great again!");
        }
        else if (response.equalsIgnoreCase("no")){
            System.out.println("That's mean. You're mean.");
        }
    }

    public String toString(){


    }




}
