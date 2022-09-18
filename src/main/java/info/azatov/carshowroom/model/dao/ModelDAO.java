package info.azatov.carshowroom.model.dao;

import java.util.List;

import info.azatov.carshowroom.model.entity.Model;

public interface ModelDAO extends BaseDAO<Model, Long> {

    List<Model> getModelsByName(String name);
    List<Model> getModelsByMake(String make);

}

