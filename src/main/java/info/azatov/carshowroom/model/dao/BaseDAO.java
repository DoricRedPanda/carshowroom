package info.azatov.carshowroom.model.dao;

import info.azatov.carshowroom.model.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collection;

/**
 * Унифицированный интерфейс управления персистентным состоянием объектов
 * @param <T> тип объекта персистенции
 * @param <ID> тип первичного ключа
 */
public interface BaseDAO<T extends BaseEntity<ID>, ID extends Serializable> {
    /** Возвращает объект соответствующий записи с первичным ключом id или null */
    T getById(ID id);

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    Collection<T> getAll();

    /** Создает новую запись, соответствующую объекту object */
    void insert(T entity);

    void insertCollection(Collection<T> entities);

    /** Удаляет запись об объект из базы данных */
    void delete(T entity);
    /** Удаляет запись об объект из базы данных по id */
    void deleteById(ID id);

    /** Сохраняет состояние объекта group в базе данных */
    void update(T entity);
}