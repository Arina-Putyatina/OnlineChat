package Settings;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Settings {

    protected int port;
    protected String server;
    protected String stopCommand;

    public Settings(int port, String server, String stopCommand) {
        this.port = port;
        this.server = server;
        this.stopCommand = stopCommand;
    }

    public int getPort() {
        return port;
    }

    public String getStopCommand() {
        return stopCommand;
    }

    public String getServer() {
        return server;
    }

    public static Settings getSettings() {
        String textSettings = readString("settings.txt");
        return parseSettings(textSettings);
    }

    protected static String readString(String fileName) {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static Settings parseSettings(String textSettings) {

        JsonParser parser = new JsonParser();
        JsonElement mJson = parser.parse(textSettings);
        Gson gson = new Gson();
        Settings settings = gson.fromJson(mJson, Settings.class);

        return settings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settings settings = (Settings) o;
        return port == settings.port && Objects.equals(server, settings.server) && Objects.equals(stopCommand, settings.stopCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, server, stopCommand);
    }

    @Override
    public String toString() {
        return "Settings{" +
                "port=" + port +
                ", server='" + server + '\'' +
                ", stopCommand='" + stopCommand + '\'' +
                '}';
    }
}
