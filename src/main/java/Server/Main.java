package Server;

import Logg.ChatLogg;
import Settings.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static ArrayList<ClientChat> chats = new ArrayList<>();
    private static Logger logger = ChatLogg.createLogger("log/ChatLogServer", "Server");

    public static void main(String[] args) throws IOException {

        Settings settings = Settings.getSettings();

        int port = settings.getPort();
        String stopCommand = settings.getStopCommand();

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> chat(clientSocket, stopCommand)).start();
            } catch (IOException e) {
                logger.log(Level.WARNING,"Ошибка сервера" , e);
            }
        }
    }

    public static void chat(Socket clientSocket, String stopCommand) {
        logger.info("Подключился новый клиент");
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            ClientChat clientChat = new ClientChat(out, in, clientSocket);
            chats.add(clientChat);
            while (true) {
                String text = in.readLine();
                if (clientChat.getName().isEmpty()) {
                    clientChat.setName(text);
                    logger.info("Установлено имя чата " + text);
                    sendMessageToAllChats("Подключился новый участник " + text, clientChat);
                    continue;
                }
                String result = "Сообщение \'" + text + "\' принято от " + clientChat.getName();
                out.println(result);
                logger.info("Server -> " + clientChat.getName() + ": " + result);
                if (text.equals(stopCommand)) {
                    logger.info("Завершения чата с " + clientChat.getName());
                    chats.remove(clientChat);
                    break;
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING,"Ошибка сервера" , e);
        }

    }

    public static void sendMessageToAllChats(String msg, ClientChat clientChatException) {
        for (ClientChat chat : chats) {
            if (chat == clientChatException) continue;
            chat.getOut().println(msg);
            logger.info("Server -> " + chat.getName() + ": " + msg);
        }
    }
}
