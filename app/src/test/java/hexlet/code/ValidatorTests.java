package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTests {

    private static StringSchema stringSchema;

    private static NumberSchema numberSchema;

    private static MapSchema<String, String> mapSchema;
    private static Validator validator;

    @BeforeEach
    public void init() {
        stringSchema = new StringSchema();
        numberSchema = new NumberSchema();
        mapSchema = new MapSchema<>();
        validator = new Validator();
    }

    @Test
    public void testStringSchema() {
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

    @Test
    public void testRequiredValidation() {
        boolean actual1 = stringSchema.isValid("");
        assertTrue(actual1);

        stringSchema = new StringSchema();
        boolean actual2 = stringSchema.isValid(null);
        assertTrue(actual2);

        stringSchema = new StringSchema();
        boolean actual3 = stringSchema.required().isValid(null);
        assertFalse(actual3);

        stringSchema = new StringSchema();
        boolean actual4 = stringSchema.required().isValid("");
        assertFalse(actual4);

        stringSchema = new StringSchema();
        boolean actual5 = stringSchema.required().isValid("abc");
        assertTrue(actual5);
    }

    @Test
    public void testMinLengthValidation() {
        boolean actual1 = stringSchema.minLength(3).isValid("abc");
        assertTrue(actual1);

        stringSchema = new StringSchema();
        boolean actual2 = stringSchema.minLength(3).isValid("abcdf");
        assertTrue(actual2);

        stringSchema = new StringSchema();
        boolean actual3 = stringSchema.minLength(3).isValid("a");
        assertFalse(actual3);
    }

    @Test
    public void testContainsValidation() {
        boolean actual1 = stringSchema.contains("text").isValid("text123123text1231 text text1");
        assertTrue(actual1);

        stringSchema = new StringSchema();
        boolean actual2 = stringSchema.contains("None").isValid("Some text123123");
        assertFalse(actual2);
    }

    @Test
    public void testInteractionRules() {
        boolean actual1 = stringSchema.required().contains("xt").minLength(4).isValid("Text");
        assertTrue(actual1);

        stringSchema = new StringSchema();
        boolean actual2 = stringSchema.required().contains("text").minLength(10).isValid("Some");
        assertFalse(actual2);
    }

    @Test
    public void testOverwritingParameters() {
        boolean actual1 = stringSchema.required()
                .contains("gdfert")
                .minLength(100)
                .contains("abc")
                .minLength(5)
                .isValid("abcdefgh");
        assertTrue(actual1);
    }

    @Test
    public void testRequiredNumber() {
        boolean actual1 = numberSchema.required().isValid(null);
        assertFalse(actual1);

        numberSchema = new NumberSchema();
        boolean actual2 = numberSchema.required().isValid(5);
        assertTrue(actual2);
    }

    @Test
    public void testPositiveValidation() {
        boolean actual1 = numberSchema.positive().isValid(-5);
        assertFalse(actual1);

        boolean actual2 = numberSchema.positive().isValid(0);
        numberSchema = new NumberSchema();
        assertFalse(actual2);

        numberSchema = new NumberSchema();
        boolean actual3 = numberSchema.positive().isValid(100);
        assertTrue(actual3);
    }

    @Test
    public void testRangeValidation() {
        boolean actual1 = numberSchema.range(-1000, 0).isValid(-60);
        assertTrue(actual1);

        boolean actual2 = numberSchema.range(0, 0).isValid(0);
        numberSchema = new NumberSchema();
        assertTrue(actual2);

        numberSchema = new NumberSchema();
        boolean actual3 = numberSchema.range(0, 80).isValid(32);
        assertTrue(actual3);

        numberSchema = new NumberSchema();
        boolean actual4 = numberSchema.range(0, 300).isValid(301);
        assertFalse(actual4);
    }

    @Test
    public void testRequiredMap() {
        boolean actual1 = mapSchema.required().isValid(null);
        assertFalse(actual1);

        mapSchema = new MapSchema<>();
        boolean actual2 = mapSchema.required().isValid(new HashMap<>());
        assertTrue(actual2);
    }

    @Test
    public void testSizeof() {
        boolean actual1 = mapSchema.sizeof(0).isValid(new HashMap<>());
        assertTrue(actual1);

        mapSchema = new MapSchema<>();
        boolean actual2 = mapSchema.sizeof(1).isValid(new HashMap<>());
        assertFalse(actual2);

        mapSchema = new MapSchema<>();
        boolean actual3 = mapSchema.sizeof(1).isValid(new HashMap<>(Map.of("abc", "foo")));
        assertTrue(actual3);
    }

    @Test
    public void testShape() {
        // shape позволяет описывать валидацию для значений каждого ключа объекта Map
        // Создаем набор схем для проверки каждого ключа проверяемого объекта
        // Для значения каждого ключа - своя схема
        Map<String, BaseSchema<String>> schemas = new HashMap<>();

        // Определяем схемы валидации для значений свойств "firstName" и "lastName"
        // Имя должно быть строкой, обязательно для заполнения
        schemas.put("firstName", validator.string().required());
        // Фамилия обязательна для заполнения и должна содержать не менее 2 символов
        schemas.put("lastName", validator.string().required().minLength(2));

        // Настраиваем схему `MapSchema`
        // Передаем созданный набор схем в метод shape()
        mapSchema.shape(schemas);

        // Проверяем объекты
        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(mapSchema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(mapSchema.isValid(human2));

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(mapSchema.isValid(human3));
    }
}
