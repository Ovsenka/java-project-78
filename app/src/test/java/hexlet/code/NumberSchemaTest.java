package hexlet.code;

import hexlet.code.schemas.number.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public final class NumberSchemaTest {
    private NumberSchema schema;

    @BeforeEach
    public void init() {
        schema = new NumberSchema();
    }

    @Test
    public void testRequiredValidation() {
        boolean actual1 = schema.required().isValid(null);
        assertFalse(actual1);

        schema = new NumberSchema();
        boolean actual2 = schema.required().isValid(5);
        assertTrue(actual2);
    }

    @Test
    public void testPositiveValidation() {
        boolean actual1 = schema.positive().isValid(-5);
        assertFalse(actual1);

        boolean actual2 = schema.positive().isValid(0);
        schema = new NumberSchema();
        assertFalse(actual2);

        schema = new NumberSchema();
        boolean actual3 = schema.positive().isValid(100);
        assertTrue(actual3);
    }

    @Test
    public void testRangeValidation() {
        boolean actual1 = schema.range(-1000, 0).isValid(-60);
        assertTrue(actual1);

        boolean actual2 = schema.range(0, 0).isValid(0);
        schema = new NumberSchema();
        assertTrue(actual2);

        schema = new NumberSchema();
        boolean actual3 = schema.range(0, 80).isValid(32);
        assertTrue(actual3);

        schema = new NumberSchema();
        boolean actual4 = schema.range(0, 300).isValid(301);
        assertFalse(actual4);
    }
}
