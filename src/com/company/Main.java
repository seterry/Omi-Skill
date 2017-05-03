package com.company;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;

import com.google.gson.Gson;

import java.util.Arrays;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

       // createDatabase(dynamoDB);

        //Omi omi = new Omi();
        Omi omi = readDatabase(dynamoDB, "1234");
        Scanner player = new Scanner(System.in);
        OmiIntent intent;
        OmiResponse response;

        intent = new OmiIntent("Launch");
        response = omi.processIntent(intent);
        System.out.println(response.getResponse());

        while(true){
            System.out.println("Enter your choice: ");
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
                intent = new OmiIntent("CheckOmiStatusIntent");
                response = omi.processIntent(intent);
                System.out.println(response.getResponse());
            }

            else if (choice.equalsIgnoreCase("check health")){
                intent = new OmiIntent("CheckOmiHealthIntent");
                response = omi.processIntent(intent);
                System.out.println(response.getResponse());
            }

            else if (choice.equalsIgnoreCase("how old")){
                intent = new OmiIntent("CheckOmiAgeIntent");
                response = omi.processIntent(intent);
                System.out.println(response.getResponse());
            }

            else if (choice.equalsIgnoreCase("check happiness")){
                intent = new OmiIntent("CheckOmiHappyIntent");
                response = omi.processIntent(intent);
                System.out.println(response.getResponse());
            }

            else if (choice.equalsIgnoreCase("check fullness")){
                intent = new OmiIntent("CheckOmiFullIntent");
                response = omi.processIntent(intent);
                System.out.println(response.getResponse());
            }

            else if (choice.equalsIgnoreCase("medicine")){
                intent = new OmiIntent("GiveMedicineIntent");
                response = omi.processIntent(intent);
                System.out.println(response.getResponse());
            }

            else if (choice.equalsIgnoreCase("what")){
                intent = new OmiIntent("ClarifyIntent");
                response = omi.processIntent(intent);
                System.out.println(response.getResponse());
            }

            //writeDatabase(dynamoDB, "1234", omi);

            /*Gson gson = new Gson();
            String json = gson.toJson(omi);
            System.out.println(json);

            omi = gson.fromJson(json, Omi.class);*/


        }



    }

    public static void createDatabase(DynamoDB dynamoDB){
        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable("OmiPets", Arrays.asList(new KeySchemaElement("userID", KeyType.HASH)),
                    Arrays.asList(new AttributeDefinition("userID", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        }
        catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
    }

    public static void writeDatabase(DynamoDB dynamoDB, String userID, Omi omi) {

        Gson gson = new Gson();
        String json = gson.toJson(omi);

        try{
            Table table = dynamoDB.getTable("OmiPets");
            table.putItem(new Item().withPrimaryKey("userID", userID).withString("Data", json));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }


    public static Omi readDatabase(DynamoDB dynamoDB, String userID){
        Table table = dynamoDB.getTable("OmiPets");
        Item item = table.getItem(new PrimaryKey("userID", userID));
        if (item != null){
            String json = item.getString("Data");
            Gson gson = new Gson();
            return gson.fromJson(json, Omi.class);
        }
        return null;
    }



}
