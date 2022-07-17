package Client;

import Logg.ChatLogg;
import Settings.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static Logger logger = ChatLogg.createLogger("log/ChatLogClient", "Client");
    private static String name = "";
    private static PrintWriter out;
    private static BufferedReader in;
    private static Scanner scanner;

    public static void main(String[] args) throws IOException {

        Settings settings = Settings.getSettings();

        int port = settings.getPort();
        String server = settings.getServer();
        String stopCommand = settings.getStopCommand();

        Socket socket = new Socket(server, port);
        logger.info("Создан сокет");

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            scanner = new Scanner(System.in);

            System.out.println("Введите имя чата");
            name = scanner.nextLine();
            logger.info("Задано имя клиента: " + name);
            out.println(name);

            System.out.println("Можно начинать общение!");
            new Thread(() -> toServer(stopCommand)).start();
            new Thread(() -> fromServer(stopCommand)).start();

        } catch (IOException ex) {
            logger.log(Level.WARNING, "Ошибка клиента", ex);
            ex.printStackTrace();
        }
    }

    public static void toServer(String stopCommand) {
        while (true) {
            String result = scanner.nextLine();
            logger.info(name + " -> server: " + result);
            out.println(result);
            if (result.equals(stopCommand)) {
                logger.info(name + " завершил чат");
                break;
            }
        }
    }

    public static void fromServer(String stopCommand) {
        String msg;
        try {
            while (true) {
                msg = in.readLine();
                if (msg == null || msg.equals(stopCommand)) {
                    break;
                }
                logger.info(msg);
                System.out.println(msg);
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Ошибка клиента", ex);
            ex.printStackTrace();
        }
    }
}

