package hexlet.code.schemas;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Getter
public abstract class BaseSchema<T> {

    private final Map<String, Predicate<T>> checks = new HashMap<>();

    public void addCheck(String name, Predicate<T> predicate) {
        checks.put(name, predicate);
    }

    public boolean isValid(T dataToValidate) {
        if (dataToValidate == null) {
            return !checks.containsKey("required");
        }

        return checks.entrySet()
                .stream()
                .allMatch(entry -> entry.getValue().test(dataToValidate));
    }
}
