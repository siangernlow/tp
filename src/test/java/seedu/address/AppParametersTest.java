package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import javafx.application.Application;

public class AppParametersTest {

    private final ParametersStub parametersStub = new ParametersStub();
    private final AppParameters expected = new AppParameters();

    @Test
    public void parse_validConfigPath_success() {
        parametersStub.namedParameters.put("config", "config.json");
        expected.setConfigPath(Paths.get("config.json"));
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_nullConfigPath_success() {
        parametersStub.namedParameters.put("config", null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_invalidConfigPath_success() {
        parametersStub.namedParameters.put("config", "a\0");
        expected.setConfigPath(null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void equals() {
        AppParameters appParameters = new AppParameters();

        // same object -> returns true
        assertEquals(appParameters, appParameters);

        //  same values -> returns true
        AppParameters appParametersCopy = new AppParameters();
        assertEquals(appParameters, appParametersCopy);

        // different config path -> returns false
        AppParameters appParametersDifferentPath = new AppParameters();
        appParametersDifferentPath.setConfigPath(Path.of("differentPath"));
        assertNotEquals(appParameters, appParametersDifferentPath);

        // different types -> returns false
        assertNotEquals(appParameters, 1);

        // null -> returns false
        assertNotEquals(appParameters, null);
    }

    @Test
    public void hashCode_success() {
        Path configPath = Paths.get("config.json");
        expected.setConfigPath(Paths.get("config.json"));

        assertEquals(configPath.hashCode(), expected.hashCode());
    }

    private static class ParametersStub extends Application.Parameters {
        private Map<String, String> namedParameters = new HashMap<>();

        @Override
        public List<String> getRaw() {
            throw new AssertionError("should not be called");
        }

        @Override
        public List<String> getUnnamed() {
            throw new AssertionError("should not be called");
        }

        @Override
        public Map<String, String> getNamed() {
            return Collections.unmodifiableMap(namedParameters);
        }
    }
}
