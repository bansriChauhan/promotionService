package com.PromotionEngineService.promotionService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NamedQuery;

@Getter@Setter
@Entity@Table(name="promotion")
//@NamedQuery(name="findByProduct",query = "FROM Promotion WHERE product=?1")
public class Promotion {
    @Id
    private String product;

    private int count;

    private int discount_price;
}
