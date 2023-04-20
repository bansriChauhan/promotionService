package com.PromotionEngineService.promotionService.Repository;

import com.PromotionEngineService.promotionService.Entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion,String> {

}
