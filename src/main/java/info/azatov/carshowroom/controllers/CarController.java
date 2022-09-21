package info.azatov.carshowroom.controllers;

import info.azatov.carshowroom.model.dao.AutoModelDAO;
import info.azatov.carshowroom.model.dao.CarDAO;
import info.azatov.carshowroom.model.dao.impl.AutoModelDAOImpl;
import info.azatov.carshowroom.model.dao.impl.CarDAOImpl;
import info.azatov.carshowroom.model.entity.AutoModel;
import info.azatov.carshowroom.model.entity.Car;
import info.azatov.carshowroom.model.entity.Client;
import info.azatov.carshowroom.model.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Controller
public class CarController {
    @Autowired
    private final CarDAO carDAO = new CarDAOImpl();
    @Autowired
    private final AutoModelDAO modelDAO = new AutoModelDAOImpl();

    @GetMapping("/addCar")
    public String addCarPage(Model model) {
        model.addAttribute("modelDAO", modelDAO);
        return "addCar";
    }

    @GetMapping("/cars")
    public String carListPage(Model model) {
        List<Car> cars = (List<Car>)carDAO.getAll();
        model.addAttribute("cars", cars);
        model.addAttribute("carDAO", carDAO);
        return "cars";
    }

    @GetMapping("/car")
    public String carPage(@RequestParam(name = "vin") Long vin, Model model) {
        Car car = carDAO.getById(vin);
        if (car == null) {
            model.addAttribute("error_message", String.format("Машины с номером %d нет в салоне", vin));
            return "errorPage";
        }
        model.addAttribute("car", car);
        model.addAttribute("carDAO", carDAO);
        return "car";
    }

    @PostMapping("/insertCar")
    public String insertCar(@RequestParam(name = "model_name") String model_name,
                            @RequestParam(name = "registration_plate") String registration_plate,
                            @RequestParam(name = "price") Double price,
                            @RequestParam(name = "kilometers") Double kilometers,
                            @RequestParam(name = "service_date") Date service_date,
                            @RequestParam(name = "displacement") Integer displacement,
                            @RequestParam(name = "power") Double power,
                            @RequestParam(name = "top_speed") Double top_speed,
                            @RequestParam(name = "seat_count") Integer seat_count,
                            @RequestParam(name = "transmission_type") String transmission_type,
                            @RequestParam(name = "devices") String devices,
                            @RequestParam(name = "color") String color,
                            @RequestParam(name = "saloon") String saloon,
                            Model model) {
        List<AutoModel> auto_model = modelDAO.getModelsByName(model_name);
        if (auto_model.size() == 0) {
            model.addAttribute("error_message", String.format("Модель %s отсутствует в базе", model_name));
            return "errorPage";
        }
        carDAO.insert(new Car(
                null,
                auto_model.get(0),
                registration_plate,
                price,
                kilometers,
                service_date,
                displacement,
                power,
                top_speed,
                seat_count,
                transmission_type,
                devices,
                color,
                saloon));
        return "redirect:/cars";
    }

    @PostMapping("/updateCar")
    public String updateCar(@RequestParam(name = "vin") Long vin,
                            @RequestParam(name = "model_name") String model_name,
                            @RequestParam(name = "registration_plate") String registration_plate,
                            @RequestParam(name = "price") Double price,
                            @RequestParam(name = "kilometers") Double kilometers,
                            @RequestParam(name = "service_date") Date service_date,
                            @RequestParam(name = "displacement") Integer displacement,
                            @RequestParam(name = "power") Double power,
                            @RequestParam(name = "top_speed") Double top_speed,
                            @RequestParam(name = "seat_count") Integer seat_count,
                            @RequestParam(name = "transmission_type") String transmission_type,
                            @RequestParam(name = "devices") String devices,
                            @RequestParam(name = "color") String color,
                            @RequestParam(name = "saloon") String saloon,
                            Model model) {
        Car car = carDAO.getById(vin);
        if (car == null) {
            model.addAttribute("error_message", String.format("Машины с номером %d нет в салоне", vin));
            return "errorPage";
        }
        car.setColor(color);
        List<AutoModel> car_model = modelDAO.getModelsByName(model_name);
        if (car_model.size() == 0) {
            model.addAttribute("error_message", String.format("Нет модели %s", model_name));
            return "errorPage";
        }
        car.setModel(car_model.get(0));
            car.setRegistration_plate(registration_plate);
            car.setPrice(price);
            car.setKilometers(kilometers);
            car.setService_date(service_date);
            car.setDisplacement(displacement);
            car.setPower(power);
            car.setTop_speed(top_speed);
            car.setSeat_count(seat_count);
            car.setTransmission_type(transmission_type);
            car.setDevices(devices);
            car.setColor(color);
            car.setSaloon(saloon);
        carDAO.update(car);
        return String.format("redirect:/car?vin=%d", vin);
    }

    @GetMapping("/searchCar")
    public String searchCar(
            @RequestParam(name = "registration_plate") String registration_plate,
            @RequestParam(name = "startPrice") Double startPrice,
            @RequestParam(name = "finishPrice") Double finishPrice,
            @RequestParam(name = "service_date") Date service_date,
            @RequestParam(name = "displacement") Integer displacement,
            @RequestParam(name = "power") Double power,
            @RequestParam(name = "top_speed") Double top_speed,
            @RequestParam(name = "seat_count") Integer seat_count,
            @RequestParam(name = "transmission_type") String transmission_type,
            @RequestParam(name = "devices") String devices,
            @RequestParam(name = "color") String color,
            @RequestParam(name = "saloon") String saloon,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "make") String make,
            @RequestParam(name = "year") Integer year,

            Model model
    ) {
        Collection<Car> cars = carDAO.search(
                registration_plate,
                service_date,
                displacement,
                power,
                top_speed,
                seat_count,
                transmission_type,
                devices,
                color,
                saloon,
                startPrice,
                finishPrice,
                name,
                make,
                year
                );
        if (cars.size() == 1) {
            return "redirect:/car?vin=" + cars.iterator().next().getId();
        }
        if (cars.size() < 1) {
            model.addAttribute(
                    "error_message", "Не нашлось автомобилей, удовлетворяющих запросу.");
            return "errorPage";
        }
        model.addAttribute("cars", cars);
        model.addAttribute("carDAO", carDAO);
        model.addAttribute("modelDAO", modelDAO);
        return "cars";
    }

    @PostMapping("/deleteCar")
        public String deleteCar(@RequestParam(name = "vin") Long vin,
                            Model model) {
        Car car = carDAO.getById(vin);
        if (car == null) {
            model.addAttribute("error_message", String.format("Машины с номером %d нет в салоне", vin));
            return "errorPage";
        }
        carDAO.delete(car);
        return "redirect:/cars";
    }
}
