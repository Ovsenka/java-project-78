package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    private final Map<K, BaseSchema<V>> valueSchemas = new HashMap<>();

    public MapSchema<K, V> required() {
        addCheck("required", Objects::nonNull);
        return this;
    }

    public MapSchema<K, V> sizeof(int size) {
        addCheck("minLength", value -> value.size() == size);
        return this;
    }
    public void shape(Map<K, BaseSchema<V>> schemas) {
        this.valueSchemas.putAll(schemas);
    }

    @Override
    public boolean isValid(Map<K, V> mapToValidate) {
        boolean isValidMap = super.getChecks().entrySet()
                .stream()
                .allMatch(entry -> entry.getValue().test(mapToValidate));

        boolean isValidMapValues = this.valueSchemas.entrySet()
                .stream()
                .allMatch((entry) -> {
                    K key = entry.getKey();
                    BaseSchema<V> schemaForValue = entry.getValue();
                    if (!mapToValidate.containsKey(key)) {
                        return false;
                    } else {
                        return schemaForValue.isValid(mapToValidate.get(key));
                    }
                });
        return isValidMap && isValidMapValues;
    }
}
