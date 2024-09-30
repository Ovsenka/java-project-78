package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTests {

    @Test
    public void testStringSchema() {
        var validator = new Validator();
        var schema = validator.string();
        var schema1 = validator.string();

        assertTrue(schema.isValid("")); // true
        assertTrue(schema.isValid(null)); // true
        schema.required();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("what does the fox say")); // true
        assertTrue(schema.isValid("hexlet")); // true

        assertTrue(schema.contains("wh").isValid("what does the fox say")); // true
        assertTrue(schema.contains("what").isValid("what does the fox say")); // true
        assertFalse(schema.contains("whatthe").isValid("what does the fox say")); // false

        assertFalse(schema.isValid("what does the fox say")); // false
        assertTrue(schema1.minLength(10).minLength(4).isValid("Hexlet")); // true
    }


}
