package com.example.Web_Koleso.servises;

import com.example.Web_Koleso.dto.TireWarehouseDTO;
import com.example.Web_Koleso.models.Tire;
import com.example.Web_Koleso.models.WarehouseTire;
import com.example.Web_Koleso.repositories.TireRepository;
import com.example.Web_Koleso.repositories.WarehouseTireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TireService {

    @Autowired
    private WarehouseTireRepository warehouseTireRepository;
    @Autowired
    private TireRepository tireRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<TireWarehouseDTO> findAllTiresWithQuantities(Long tireArtikle) {
        List<WarehouseTire> warehouseTires = warehouseTireRepository.findAll();
        List<TireWarehouseDTO> tireWarehouseDTOs = new ArrayList<>();

        for (WarehouseTire warehouseTire : warehouseTires) {
            if (warehouseTire.getTire().getArticle()==tireArtikle) {
                TireWarehouseDTO dto = new TireWarehouseDTO();
                dto.setTireId(warehouseTire.getTire().getId());
                dto.setTireName(warehouseTire.getTire().getName());
                dto.setWarehouseName(warehouseTire.getWarehouse().getName());
                dto.setAvailableNow(warehouseTire.getAvailableNow());
                dto.setAvailableTotal(warehouseTire.getAvailableTotal());
                dto.setProvidedTotal(warehouseTire.getProvidedTotal());
                dto.setDeficitTotal(warehouseTire.getDeficitTotal());
                dto.setSurplusTotal(warehouseTire.getSurplusTotal());
                dto.setUploadDate(warehouseTire.getTire().getUploadDate());
                tireWarehouseDTOs.add(dto);
            }
        }
        return tireWarehouseDTOs;
    }
    public List<TireWarehouseDTO> findAllTiresWithName(String name) {
        List<WarehouseTire> warehouseTires = warehouseTireRepository.findAll();
        List<TireWarehouseDTO> tireWarehouseDTOs = new ArrayList<>();
        for (WarehouseTire warehouseTire : warehouseTires) {
            //изменил equals на contains
            if (warehouseTire.getTire().getName().contains(name)) {
                TireWarehouseDTO dto = new TireWarehouseDTO();
                dto.setTireId(warehouseTire.getTire().getId());
                dto.setTireName(warehouseTire.getTire().getName());
                dto.setWarehouseName(warehouseTire.getWarehouse().getName());
                dto.setAvailableNow(warehouseTire.getAvailableNow());
                dto.setAvailableTotal(warehouseTire.getAvailableTotal());
                dto.setProvidedTotal(warehouseTire.getProvidedTotal());
                dto.setDeficitTotal(warehouseTire.getDeficitTotal());
                dto.setSurplusTotal(warehouseTire.getSurplusTotal());
                dto.setUploadDate(warehouseTire.getTire().getUploadDate());
                tireWarehouseDTOs.add(dto);
            }
        }
        return tireWarehouseDTOs;
    }

    public List<Tire> findAllTires() {return tireRepository.findAll();}

    public Optional<Tire> findByArticle(Long article){
        return tireRepository.findByArticle(article);
    }
    @Transactional
    public void save(Tire tire){tireRepository.save(tire);}

    @Transactional
    @Modifying
    @Query("DELETE FROM Tire ")
    public void deleteAll(){
        tireRepository.deleteAll();
        resetAutoIncrement("tire_id_seq");
    }

    /**
     * метод resetAutoIncrement, который сбрасывает счётчик автоинкремента.
     * @param sequenceName  имя последовательности находится в папке sequences
     */
    private void resetAutoIncrement(String sequenceName) {
        String sql = "ALTER SEQUENCE " + sequenceName + " RESTART WITH 1";
        jdbcTemplate.execute(sql);
    }

}
