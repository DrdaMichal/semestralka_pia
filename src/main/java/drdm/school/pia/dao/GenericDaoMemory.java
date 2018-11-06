package drdm.school.pia.dao;

import drdm.school.pia.domain.BaseObject;

import java.util.HashMap;
import java.util.Map;

public class GenericDaoMemory<T extends BaseObject> implements GenericDao<T> {

    protected final Map<Long, T> cache;

    public GenericDaoMemory() {
        this.cache = new HashMap<>();
    }

    @Override
    public T save(T value) {
        return this.cache.put(value.getId(), value);
    }

    @Override
    public T findOne(Long id) {
        return this.cache.get(id);
    }

    @Override
    public void remove(T toRemove) {
        this.cache.remove(toRemove.getId());
    }
}
