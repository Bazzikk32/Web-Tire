package com.example.Web_Koleso.servises;

import com.example.Web_Koleso.models.Tire;
import com.example.Web_Koleso.models.Warehouse;
import com.example.Web_Koleso.models.WarehouseTire;
import com.example.Web_Koleso.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Transactional
    @Modifying
    @Query("DELETE FROM Warehouse ") // Используйте JPQL
    public void deleteAll(){warehouseRepository.deleteAll();}

    /**
     * Сохраняет новый склад в базу данных.
     *
     * @param name Наименование склада.
     * @return Сохраненный склад.
     */
    @Transactional
    public Warehouse save(String name) {
        Warehouse newWarehouse = new Warehouse();
        newWarehouse.setName(name);
        return warehouseRepository.save(newWarehouse);
    }
    public List<Warehouse> findAllWarehouses() {return warehouseRepository.findAll();}

    /**
     * Находит шины по имени склада.
     *
     * @param name Наименование склада.
     * @return Список шин, связанных с указанным складом. Возвращает пустой список если склад не найден.
     */
    public List<Tire> findWarehouseByNameListTier(String name) {
        Warehouse warehouse = warehouseRepository.findByName(name);
        if (warehouse == null) {
            return List.of(); // Или выбросить исключение
        }
        List<WarehouseTire> tireList=warehouse.getWarehouseTire();
        List<Tire> tires=new ArrayList<>();
        for (WarehouseTire warehouseTire : tireList) {
            tires.add(warehouseTire.getTire());
        }
        return tires;
    }
}
