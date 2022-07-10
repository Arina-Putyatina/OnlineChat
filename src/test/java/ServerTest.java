import Settings.Settings;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerTest {

    @Test
    void serverCreatedCorrectly() {
        Settings settings = Settings.getSettings();
        int port = settings.getPort();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Assert.fail("IOException");
        }
    }
}
