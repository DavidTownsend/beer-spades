package com.lol.beer_spades.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lol.beer_spades.game.GameActivity;
import com.lol.beer_spades.model.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by davidtownsend on 11/12/15.
 */
public class FileUtilities extends Activity {

    public static List<Player> retrieveDataFromFile() {

        File jsonFile = retrieveJsonFile();

        try {
            Type collectionType = new TypeToken<List<Player>>() {}.getType();
            InputStream dataUpdateStream = new FileInputStream(jsonFile);
            Reader dataUpdateReader = new BufferedReader(new InputStreamReader(dataUpdateStream));
            Gson gson = new GsonBuilder().setDateFormat("MM-dd-yyyy").create();
            List<Player> allPlayers = gson.fromJson(dataUpdateReader, collectionType);

            return allPlayers;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void sendDataToFile(List<Player> allPlayers){
        File jsonFile = retrieveJsonFile();
        Type collectionType = new TypeToken<List<Player>>() {}.getType();
        Gson gson = new Gson();

        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(allPlayers, collectionType);

        try {
            //write converted json data to a file named "file.json"
            FileWriter writer = new FileWriter(jsonFile, false);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File retrieveJsonFile(){
        File jsonFile = new File(LogginUtils.EXTERNAL_FILE_LOCATION + "data.json");
        if (!jsonFile.exists()) {
            try {
                jsonFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return jsonFile;
    }
}
