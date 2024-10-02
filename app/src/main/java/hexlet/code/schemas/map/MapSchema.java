package hexlet.code.schemas.map;

import hexlet.code.schemas.BaseSchema;

import java.util.Map;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {

    public MapSchema<K, V> required() {
        super.strategies.put(RequiredValidation.NAME, new RequiredValidation<>());
        return this;
    }

    public MapSchema<K, V> sizeof(int size) {
        super.strategies.put(SizeofValidation.NAME, new SizeofValidation<>(size));
        return this;
    }

}