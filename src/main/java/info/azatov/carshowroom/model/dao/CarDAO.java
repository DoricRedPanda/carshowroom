package info.azatov.carshowroom.model.dao;

import java.sql.Date;
import java.util.List;

import info.azatov.carshowroom.model.entity.Car;

public interface CarDAO extends BaseDAO<Car, Long> {

    List<Car> search(
            String registration_plate,
            Date from_service_date,
            Integer displacement,
            Double power,
            Double top_speed,
            Integer seat_count,
            String transmission_type,
            String devices,
            String color,
            String saloon,
            Double startPrice,
            Double finishPrice,
            String name,
            String make,
            Integer year
    );
}
