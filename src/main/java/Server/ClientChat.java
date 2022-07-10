package Server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ClientChat {

    protected PrintWriter out;
    protected BufferedReader in;
    protected Socket clientSocket;
    protected String name = "";

    public ClientChat(PrintWriter out, BufferedReader in, Socket clientSocket) {
        this.out = out;
        this.in = in;
        this.clientSocket = clientSocket;
    }

    public String getName() {
        return name;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientChat that = (ClientChat) o;
        return Objects.equals(out, that.out) && Objects.equals(in, that.in) && Objects.equals(clientSocket, that.clientSocket) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(out, in, clientSocket, name);
    }
}
