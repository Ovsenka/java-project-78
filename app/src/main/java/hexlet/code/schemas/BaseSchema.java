package hexlet.code.schemas;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class BaseSchema<T> {

    protected Map<String, ValidationStrategy<T>> strategies = new HashMap<>();

    public boolean isValid(T dataToValidate) {
        return validateData(dataToValidate, strategies);
    }

    private <R> boolean validateData(R dataToValidate, Map<String, ValidationStrategy<R>> strategiesForData) {
        if (dataToValidate == null) {
            return !strategiesForData.containsKey("required");
        }

        for (Map.Entry<String, ValidationStrategy<R>> entry : strategiesForData.entrySet()) {
            ValidationStrategy<R> validationStrategy = entry.getValue();
            if (!validationStrategy.validate(dataToValidate)) {
                return false;
            }
        }

        return true;
    }
}
