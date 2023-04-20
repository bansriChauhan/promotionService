package com.PromotionEngineService.promotionService.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="cart")
//@NamedQuery(name="findByProduct",query = "FROM Cart WHERE product=?1")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "name")
    private Product product;

    private Integer quantity;

}
