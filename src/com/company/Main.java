package com.company;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Omi omi = new Omi();
        Scanner player = new Scanner(System.in);
        OmiIntent intent;
        OmiResponse response;

        intent = new OmiIntent("Launch");
        response = omi.processIntent(intent);
        System.out.println(response.getResponse());

        while(omi.isHealthy()){
            System.out.println("Would you like to play with Omi, pet Omi, or feed Omi?\nYou can also check on how Omi is doing.");
            String choice = player.nextLine();

            if (choice.equalsIgnoreCase("play")){
                intent = new OmiIntent("StartGameIntent");
                response = omi.processIntent(intent);
                System.out.println(response.getResponse());
                while (omi.isPlaying()) {
                    String number = player.nextLine();
                    HashMap<String, String> slots = new HashMap<String, String>();
                    slots.put("number", number);
                    intent = new OmiIntent("PlayWithOmiIntent", slots);
                    response = omi.processIntent(intent);
                    System.out.println(response.getResponse());
                }

            }
            else if (choice.equalsIgnoreCase("pet")){
                intent = new OmiIntent("PetOmiIntent");
                response = omi.processIntent(intent);
                System.out.println(response.getResponse());

            }
            else if (choice.equalsIgnoreCase("feed")){
                intent = new OmiIntent("FeedOmiIntent");
                response = omi.processIntent(intent);
                System.out.println(response.getResponse());
                String food = player.nextLine();
                HashMap<String, String> slots = new HashMap<String, String>();
                slots.put("foodtype", food);
                intent = new OmiIntent("FeedOmiIntent", slots);
                response = omi.processIntent(intent);
                System.out.println(response.getResponse());

            }
            else if (choice.equalsIgnoreCase("check")){

            }
            omi.checkandsetHealthy();
            if (!omi.isHealthy()){
                omi.giveOmiMedicine();
            }
        }



    }
}
