package info.azatov.carshowroom.controllers;

import info.azatov.carshowroom.model.dao.AutoModelDAO;
import info.azatov.carshowroom.model.dao.impl.AutoModelDAOImpl;
import info.azatov.carshowroom.model.entity.AutoModel;
import info.azatov.carshowroom.model.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AutoModelController {
    @Autowired
    private final AutoModelDAO modelDAO = new AutoModelDAOImpl();

    @GetMapping("/addModel")
    public String addModelPage(Model model) {
        model.addAttribute("modelDAO", modelDAO);
        return "addModel";
    }

    @GetMapping("/models")
    public String modelListPage(Model model) {
        List<AutoModel> models = (List<AutoModel>)modelDAO.getAll();
        model.addAttribute("models", models);
        model.addAttribute("modelDAO", modelDAO);
        return "models";
    }

    @GetMapping("/model")
    public String modelPage(@RequestParam(name = "model_id") Long model_id, Model model) {
        AutoModel autoModel = modelDAO.getById(model_id);
        if (autoModel == null) {
            model.addAttribute("error_message", String.format("Модели автомобиля с номером %d нет", model_id));
            return "errorPage";
        }
        model.addAttribute("model", autoModel);
        model.addAttribute("modelDAO", modelDAO);
        return "model";
    }

    @PostMapping("/insertModel")
    public String insertModel(@RequestParam(name = "name") String name,
                            @RequestParam(name = "make") String make,
                            @RequestParam(name = "year") Integer year,
                            Model model) {
        modelDAO.insert(new AutoModel(null, name, make, year));
        return "redirect:/models";
    }

    @PostMapping("/updateModel")
    public String updateModel(@RequestParam(name = "model_id") Long model_id,
                            @RequestParam(name = "name") String name,
                            @RequestParam(name = "make") String make,
                            @RequestParam(name = "year") Integer year,
                            Model model) {
        AutoModel autoModel = modelDAO.getById(model_id);
        if (autoModel == null) {
            model.addAttribute("error_message", String.format("Модели автомобиля с номером %d нет", model_id));
            return "errorPage";
        }
        autoModel.setName(name);
        autoModel.setMake(make);
        autoModel.setYear(year);
        modelDAO.update(autoModel);
        return "redirect:/models";
    }

    @PostMapping("/deleteModel")
        public String deleteModel(@RequestParam(name = "model_id") Long model_id,
                            Model model) {
        AutoModel autoModel = modelDAO.getById(model_id);
        if (autoModel == null) {
            model.addAttribute("error_message", String.format("Модели автомобиля с номером %d нет", model_id));
            return "errorPage";
        }
        modelDAO.delete(autoModel);
        return "redirect:/models";
    }
}
