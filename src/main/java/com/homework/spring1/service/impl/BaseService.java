package com.homework.spring1.service.impl;

import com.homework.spring1.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public abstract class BaseService<T, ID> {
    protected final Map<ID, T> items = new ConcurrentHashMap<>();
    protected final AtomicLong idGenerator = new AtomicLong(1);

    protected abstract T createNewItem(ID id, T item);
    protected abstract String getEntityName();

    public List<T> getAll() {
        log.info("Получение списка всех {}", getEntityName());
        return new ArrayList<>(items.values());
    }

    public T getById(ID id) {
        log.info("Получение {} по id: {}", getEntityName(), id);
        return items.get(id);
    }

    public T findById(ID id) {
        return Optional.ofNullable(getById(id))
                .orElseThrow(() -> new ResourceNotFoundException(getEntityName(), "id", id));
    }

    public T create(T item) {
        log.info("Создание нового {}: {}", getEntityName(), item);
        ID id = (ID) Long.valueOf(idGenerator.getAndIncrement());
        T newItem = createNewItem(id, item);
        items.put(id, newItem);
        return newItem;
    }

    public T update(ID id, T item) {
        log.info("Обновление {} с id {}: {}", getEntityName(), id, item);
        findById(id);
        T updatedItem = createNewItem(id, item);
        items.put(id, updatedItem);
        return updatedItem;
    }

    public void delete(ID id) {
        log.info("Удаление {} с id: {}", getEntityName(), id);
        findById(id);
        items.remove(id);
    }
}