package hexlet.code.schemas.map;

import hexlet.code.schemas.BaseSchema;

import java.util.HashMap;
import java.util.Map;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    private final Map<K, BaseSchema<V>> valueStrategies = new HashMap<>();

    public MapSchema<K, V> required() {
        super.strategies.put(RequiredValidation.NAME, new RequiredValidation<>());
        return this;
    }

    public MapSchema<K, V> sizeof(int size) {
        super.strategies.put(SizeofValidation.NAME, new SizeofValidation<>(size));
        return this;
    }
    public void shape(Map<K, BaseSchema<V>> schemas) {
        this.valueStrategies.putAll(schemas);
    }

    @Override
    public boolean isValid(Map<K, V> mapToValidate) {
        boolean isValidMap = super.strategies.entrySet()
                .stream()
                .allMatch(entry -> entry.getValue().validate(mapToValidate));

        boolean isValidMapValues = this.valueStrategies.entrySet()
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
