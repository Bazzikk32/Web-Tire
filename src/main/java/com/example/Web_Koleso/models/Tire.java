package com.example.Web_Koleso.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Table(name = "tire", schema = "public")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "warehouseTire")
public class Tire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")


    private long id;
    private long article;
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  // <span style="color: blue;">Для форматирования даты.</span>
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @PrePersist
    protected void onCreate() {
        uploadDate = LocalDateTime.now();   // <span style="color: blue;">Сохранение текущей даты при создании шины.</span>
    }

    @OneToMany(mappedBy = "tire",orphanRemoval = true)
    private List<WarehouseTire> warehouseTire;
}
