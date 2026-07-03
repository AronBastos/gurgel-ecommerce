package com.gurgel.ecommerce.repository;

import com.gurgel.ecommerce.model.entity.Car;
import com.gurgel.ecommerce.model.enums.GurgelCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    // ============ FINDERS BÁSICOS ============

    List<Car> findByActiveTrue();

    List<Car> findByCategory(GurgelCategory category);

    List<Car> findByModelContainingIgnoreCase(String model);

    List<Car> findByYear(String year);

    List<Car> findByYearBetween(String startYear, String endYear);

    List<Car> findByStockGreaterThan(Integer quantity);

    List<Car> findByVehicleType(String vehicleType);

    List<Car> findByDriveType(String driveType);

    // ============ PREÇO ============

    List<Car> findByPriceLessThanEqual(BigDecimal maxPrice);

    List<Car> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // ============ CONSULTAS JPQL ============

    @Query("SELECT c FROM Car c WHERE c.unitsProduced IS NOT NULL AND c.unitsProduced < 100")
    List<Car> findCollectorCars();

    @Query("SELECT c FROM Car c WHERE LOWER(c.model) LIKE LOWER(CONCAT('%', :model, '%')) AND c.year = :year")
    List<Car> findByModelAndYear(@Param("model") String model, @Param("year") String year);

    @Query("SELECT c FROM Car c WHERE LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR LOWER(c.model) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Car> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT c FROM Car c WHERE c.stock > 0 AND c.stock < 3")
    List<Car> findWithCriticalStock();

    @Query("SELECT c.category, COUNT(c) FROM Car c GROUP BY c.category")
    List<Object[]> countByCategory();

    @Query("SELECT SUM(c.stock) FROM Car c")
    Optional<Integer> countTotalStock();

    @Query("SELECT SUM(c.price * c.stock) FROM Car c WHERE c.stock > 0")
    Optional<BigDecimal> calculateTotalStockValue();

    // ============ CONSULTAS NATIVAS ============

    @Query(value = "SELECT * FROM cars WHERE production_period LIKE CONCAT('%', :period, '%')",
            nativeQuery = true)
    List<Car> findByProductionPeriod(@Param("period") String period);

    @Query(value = "SELECT * FROM cars WHERE stock > 0 ORDER BY price ASC LIMIT 5",
            nativeQuery = true)
    List<Car> findTop5Cheapest();

    // ============ ORDENAÇÃO ============

    List<Car> findAllByOrderByPriceAsc();

    List<Car> findAllByOrderByPriceDesc();

    List<Car> findAllByOrderByYearDesc();

    List<Car> findAllByOrderByCreatedAtDesc();

    // ============ VERIFICAÇÕES ============

    boolean existsByModel(String model);
}
