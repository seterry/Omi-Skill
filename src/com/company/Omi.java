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
            return new OmiResponse("Hello, I am Omi! I am your virtual pet! It is nice to meet you! You can interact with me by playing with me, petting me, or feeding me. You can also check on how I'm doing! What would you like to do?");
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
        else if (name.equals("CheckOmiStatusIntent")){
            return this.checkOmiStatus();
        }
        else if (name.equals("CheckOmiAgeIntent")){
            return this.checkOmiAge();
        }
        else if (name.equals("CheckOmiFullIntent")){
            return this.checkOmiFullness();
        }
        else if (name.equals("CheckOmiHappyIntent")){
            return this.checkOmiHappiness();
        }
        else if (name.equals("CheckOmiHealthIntent")){
            return this.checkOmiHealth();
        }
        else if (name.equals("GiveMedicineIntent")){
            return this.giveMedicine();
        }
        else if (name.equals("ClarifyIntent")){
            return this.clarifyOptions();
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

    String getStringHappiness(){
        if (happiness == 0){
            return "Omi doesn't seem very happy.";
        }
        else if (happiness == 1){
            return "Omi is a little bit happy.";
        }
        else if (happiness == 2){
            return "Omi is pretty happy.";
        }
        else if (happiness == 3){
            return "Omi is happy!";
        }
        else if (happiness == 4){
            return "Omi is so happy!";
        }
        else if (happiness >= 5){
            return "This is the happiest Omi has ever been!";
        }
        else {
            return "Omi isn't sure.";
        }
    }

    void setFullness(int f){
        fullness = f;
    }

    int getFullness(){
        return fullness;
    }

    String getStringFullness(){
        if (fullness == 0){
            return "Omi is starving!";
        }
        else if (fullness == 1){
            return "Omi is still pretty hungry.";
        }
        else if (fullness == 2){
            return "Omi is a little bit hungry.";
        }
        else if (fullness == 3){
            return "Omi is feeling full!";
        }
        else if (fullness == 4){
            return "Omi is really full!";
        }
        else if (fullness == 5){
            return "Omi is so full, she feels like she's going to pop!";
        }
        else {
            return "Omi isn't sure.";
        }
    }

    boolean isHealthy(){
        return healthy;
    }

    String healthyString(){
        if (healthy){
            return "Omi is healthy!";
        }
        else {
            return "Omi isn't feeling very well.";
        }
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
        if (age == 1) {
            unit = " minute old.";
        }
        else unit = " minutes old.";
        if (age > 59){
            age = TimeUnit.HOURS.convert(age, TimeUnit.MINUTES);
            if (age == 1){
                unit = " hour old.";
            }
            else unit = " hours old.";
            if (age > 23){
                age = TimeUnit.DAYS.convert(age, TimeUnit.HOURS);
                if (age == 1){
                    unit = " day old.";
                }
                else unit = " days old.";
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
        if (!playing){
            return new OmiResponse("Hmm, doesn't look like you've started a game yet! Say 'play with Omi' to start playing!");
        }
        else {
            String number = intent.getSlot("number");
            if (number == null) {
                return new OmiResponse("Omi is thinking of a number between 1 and 10. Can you guess which one?");
            } else if (number.equals(Integer.toString(game.omiGuess))) {
                playing = false;
                return new OmiResponse("Yay! That's my number!");
            } else if (Integer.parseInt(number) > game.omiGuess) {
                return new OmiResponse("Nope, that's too high! Try again!");
            } else if (Integer.parseInt(number) < game.omiGuess) {
                return new OmiResponse("Nope, that's too low! Try again!");
            } else {
                return new OmiResponse("You have to guess a number between 1 and 10!");
            }
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
        if (fullness > 5){
            healthy = false;
            return new OmiResponse("Uh oh, that's a lot of food. I don't feel that well...");
        }
        return new OmiResponse("Mmm, that was very filling!");

    }

    private OmiResponse feedOmiSnack(){
        happiness++;
        snackCounter++;
        if (snackCounter >= 5){
            healthy = false;
            happiness--;
            return new OmiResponse("Uh oh, that was a lot of treats. I don't feel very well...");
        }
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
        return "So let's see how Omi is doing!\n" + this.healthyString() + " " + this.getStringFullness() + " " + this.getStringHappiness() + " " + this.getAge();

    }

    private OmiResponse checkOmiStatus(){
        return new OmiResponse(this.toString());
    }

    private OmiResponse checkOmiAge(){
        return new OmiResponse(this.getAge());
    }

    private OmiResponse checkOmiFullness(){
        return new OmiResponse(this.getStringFullness());
    }

    private OmiResponse checkOmiHappiness(){
        return new OmiResponse(this.getStringHappiness());
    }

    private OmiResponse checkOmiHealth(){
        return new OmiResponse(this.healthyString());
    }

    private OmiResponse giveMedicine(){
        if (!healthy){
            healthy = true;
            if (snackCounter >= 5){
                snackCounter = 0;
            }
            if (fullness > 5){
                fullness = 1;
            }
            return new OmiResponse("Omi is feeling a lot better now! Thanks!");
        }
        else {
            return new OmiResponse("Omi doesn't need any medicine right now.");
        }
    }

    private OmiResponse clarifyOptions(){
        return new OmiResponse("You can play a game with Omi, pet Omi, feed Omi, or check on how Omi is doing!");
    }




}
