package com.example.MoneyMap.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Categories")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Embeddable
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;

    private String categoryName;
    private Boolean isIncome;
}
