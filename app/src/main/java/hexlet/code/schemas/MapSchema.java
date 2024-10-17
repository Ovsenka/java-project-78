package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {

    public MapSchema<K, V> required() {
        addCheck("required", Objects::nonNull);
        return this;
    }

    public MapSchema<K, V> sizeof(int size) {
        addCheck("minLength", value -> value.size() == size);
        return this;
    }
    public <T> MapSchema shape(Map<String, BaseSchema<T>> schemas) {
        addCheck(
                "shape",
                map -> schemas.entrySet().stream().allMatch(e -> {
                    V val = map.get(e.getKey());
                    return e.getValue().isValid((T) val);
                }
                ));
        return this;
    }
}
