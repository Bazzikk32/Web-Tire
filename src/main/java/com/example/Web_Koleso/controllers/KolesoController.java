package com.example.Web_Koleso.controllers;

import com.example.Web_Koleso.dto.TireWarehouseDTO;
import com.example.Web_Koleso.models.Tire;
import com.example.Web_Koleso.servises.TireService;
import com.example.Web_Koleso.servises.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller для обработки запросов связанных с колесами и складами.
 * Обеспечивает обработку запросов и отображение данных.
 */
@Controller
@RequestMapping()
public class KolesoController {

    /**
     * Сервис для работы с шинами.
     */
    @Autowired
    private TireService tireService;
    /**
     * Сервис для работы со складами.
     */
    @Autowired
    private WarehouseService warehouseService;

    /**
     * Отображает список всех шин.
     *
     * @param model Модель для передачи данных в представление.
     * @return Название представления для списка шин.
     */
    @GetMapping("/tire")
    public String SpisokAll(Model model) {
        model.addAttribute("tire", tireService.findAllTires());
        return "tire-spisok";
    }

    /**
     * Отображает список всех складов.
     *
     * @param model Модель для передачи данных в представление.
     * @return Название представления для списка складов.
     */
    @GetMapping("/ware_house")
    public String Spisok(Model model) {
        model.addAttribute("wareHouse", warehouseService.findAllWarehouses());
        return "warehouse-spisok";
    }
    /**
     * Отображает домашнюю страницу.
     *
     * @return Название представления для домашней страницы.
     */
    @GetMapping("/home")
    public String Home() {
        return "home";
    }
    /**
     * Обрабатывает запрос по артикулу.
     *
     * @param article Артикул для поиска.
     * @param model   Модель для передачи данных в представление.
     * @return Название представления для отображения результатов поиска по артикулу.
     */
    @PostMapping("/submit-article")
    public String handleArticleRequest(@RequestParam("article") String article, Model model) {
        //Логика обработки введеных значений в поле.
        if (article.isEmpty()) {
            model.addAttribute("errorArticle", article = "Поле не может быть пустым");
            return "home";
        }
        if (article.length() > 10) {
            model.addAttribute("errorArticle", article = "Поле не может быть более 10ти символов");
            return "home";
        }
        // Добавьте логику для обработки артикула
        model.addAttribute("article", article);

        // Получите список шин и добавьте его в модель
        List<TireWarehouseDTO> tireWarehouseDTOList = tireService.findAllTiresWithQuantities(Long.valueOf(article));
        model.addAttribute("tireWarehouseDTOList", tireWarehouseDTOList);

        return "article-submit"; // Убедитесь, что это название шаблона верно
    }
    /**
     * Обрабатывает запрос по наименованию.
     *
     * @param name  Наименование для поиска.
     * @param model Модель для передачи данных в представление.
     * @return Название представления для отображения результатов поиска по наименованию.
     */
    @PostMapping("/submit-name")
    public String handleNameRequest(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        //Логика обработки введеных значений в поле.
        if (name.isEmpty()) {
            model.addAttribute("errorName", name = "Поле не может быть пустым");
            return "home";
        }
        if (name.length() > 12) {
            model.addAttribute("errorName", name = "Поле не может быть более 12ти символов");
            return "home";
        }
        // Добавьте логику для обработки наименования
        List<TireWarehouseDTO> tireWarehouseDTOList = tireService.findAllTiresWithName(name);
        model.addAttribute("tireWarehouseDTOList", tireWarehouseDTOList);

        return "article-submit";
    }
    /**
     * Обрабатывает запрос по складу.
     *
     * @param warehouse Наименование склада для поиска.
     * @param model   Модель для передачи данных в представление.
     * @return Название представления для отображения результатов поиска по складу.
     */
    @PostMapping("/submit-warehouse")
    public String handleWarehouseRequest(@RequestParam("name") String warehouse, Model model) {
        // Добавьте логику для обработки склада
        model.addAttribute("warehouse", warehouse);

        // Получите список складов и добавьте его в модель
        List<Tire> tireList = warehouseService.findWarehouseByNameListTier(warehouse);
        model.addAttribute("tire", tireList);
        return "warehouse-submit"; // Убедитесь, что это название шаблона верно
    }
}