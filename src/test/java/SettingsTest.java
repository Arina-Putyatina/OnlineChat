import Settings.Settings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsTest {

    @Test
    void correctSettings() {
        Settings expected = new Settings(25555, "127.0.0.1", "exit");
        Settings result = Settings.getSettings();
        assertEquals(expected, result);
    }


}
