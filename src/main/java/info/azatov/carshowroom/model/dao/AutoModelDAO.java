package info.azatov.carshowroom.model.dao;

import java.util.List;

import info.azatov.carshowroom.model.entity.AutoModel;

public interface AutoModelDAO extends BaseDAO<AutoModel, Long> {

    List<AutoModel> getModelsByName(String name);
    List<AutoModel> getModelsByMake(String make);

}

