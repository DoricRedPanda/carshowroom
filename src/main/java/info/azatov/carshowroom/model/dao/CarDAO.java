package info.azatov.carshowroom.model.dao;

import java.util.List;

import info.azatov.carshowroom.model.entity.Car;

public interface CarDAO extends BaseDAO<Car, Long> {

    List<Car> search(Car filter, Double startPrice, Double finishPrice, String name, String make);
}