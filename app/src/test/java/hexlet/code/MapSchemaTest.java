package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.map.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public final class MapSchemaTest {
    private MapSchema<String, String> schema;
    private Validator validator;

    @BeforeEach
    void setUp() {
        schema = new MapSchema<>();
        validator = new Validator();
    }

    @Test
    public void testRequired() {
        boolean actual1 = schema.required().isValid(null);
        assertFalse(actual1);

        schema = new MapSchema<>();
        boolean actual2 = schema.required().isValid(new HashMap<>());
        assertTrue(actual2);
    }

    @Test
    public void testSizeof() {
        boolean actual1 = schema.sizeof(0).isValid(new HashMap<>());
        assertTrue(actual1);

        schema = new MapSchema<>();
        boolean actual2 = schema.sizeof(1).isValid(new HashMap<>());
        assertFalse(actual2);

        schema = new MapSchema<>();
        boolean actual3 = schema.sizeof(1).isValid(new HashMap<>(Map.of("abc", "foo")));
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
        schema.shape(schemas);

        // Проверяем объекты
        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(schema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2));

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(schema.isValid(human3));
    }
}
