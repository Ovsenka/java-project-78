package hexlet.code.schemas;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class BaseSchema<T> {

    protected Map<String, ValidationStrategy<T>> strategies = new HashMap<>();

    public boolean isValid(T dataToValidate) {
        if (dataToValidate == null) {
            return !strategies.containsKey("required");
        }

        return strategies.entrySet()
                .stream()
                .allMatch(entry -> entry.getValue().validate(dataToValidate));
    }
}
