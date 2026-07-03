package com.gurgel.ecommerce.bootstrap;

import com.gurgel.ecommerce.model.entity.Car;
import com.gurgel.ecommerce.model.enums.GurgelCategory;
import com.gurgel.ecommerce.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Popula o catálogo com modelos reais da Gurgel Motores caso o banco esteja
 * vazio. Ativo em todos os perfis exceto "test".
 *
 * <p>As imagens são servidas pelo frontend a partir de {@code /cars/<slug>.jpg}
 * (pasta {@code frontend/public/cars/}).
 */
@Component
@Profile("!test")
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final CarRepository carRepository;

    @Override
    public void run(String... args) {
        if (carRepository.count() > 0) {
            log.info("Catálogo já populado ({} carros). Seed ignorado.", carRepository.count());
            return;
        }

        List<Car> cars = List.of(
                build("BR-800", "br-800", "1988", GurgelCategory.CLASSIC,
                        new BigDecimal("38000.00"), 4, "800cc", "RWD", "Hatch", "1988-1991", 3200,
                        "Primeiro carro de passeio 100% projetado no Brasil, com carroceria de plástico Plasteel."),
                build("Supermini", "supermini", "1992", GurgelCategory.CLASSIC,
                        new BigDecimal("42000.00"), 3, "993cc", "RWD", "Hatch", "1992-1993", 1500,
                        "Evolução do BR-800 com motor mais potente; produzido até o fim da montadora."),
                build("Itaipu E400", "itaipu", "1981", GurgelCategory.ELECTRIC,
                        new BigDecimal("95000.00"), 1, "Elétrico", "RWD", "Utilitário", "1974-1982", 90,
                        "Um dos primeiros carros elétricos do mundo em produção, movido a baterias."),
                build("Xavante XT", "xavante", "1978", GurgelCategory.OFF_ROAD,
                        new BigDecimal("55000.00"), 2, "1600cc", "4x4", "Jipe", "1970-1980", 20000,
                        "Utilitário off-road sobre plataforma Volkswagen, ícone da produção Gurgel."),
                build("Tocantins", "tocantins", "1988", GurgelCategory.PICKUP,
                        new BigDecimal("47000.00"), 3, "1600cc", "RWD", "Picape", "1988-1991", 5000,
                        "Picape leve derivada da linha utilitária da Gurgel."),
                build("Carajás", "carajas", "1984", GurgelCategory.OFF_ROAD,
                        new BigDecimal("60000.00"), 2, "1600cc", "4x4", "Utilitário", "1984-1991", 4000,
                        "Utilitário robusto voltado ao campo e ao uso militar."),
                build("X-12", "x-12", "1973", GurgelCategory.UTILITY,
                        new BigDecimal("33000.00"), 5, "1600cc", "RWD", "Buggy", "1973-1982", 30000,
                        "Buggy de grande sucesso comercial, um dos modelos mais vendidos da marca.")
        );

        carRepository.saveAll(cars);
        log.info("Catálogo populado com {} carros Gurgel.", cars.size());
    }

    private Car build(String model, String imageSlug, String year, GurgelCategory category,
                      BigDecimal price, int stock, String engine, String drive, String vehicleType,
                      String period, int unitsProduced, String historicalFact) {
        Car car = new Car();
        car.setModel(model);
        car.setYear(year);
        car.setCategory(category);
        car.setPrice(price);
        car.setStock(stock);
        car.setEngineCapacity(engine);
        car.setDriveType(drive);
        car.setVehicleType(vehicleType);
        car.setProductionPeriod(period);
        car.setUnitsProduced(unitsProduced);
        car.setHistoricalFact(historicalFact);
        car.setDescription("Gurgel " + model + " (" + year + ") — " + historicalFact);
        car.setImageUrl("/cars/" + imageSlug + ".jpg");
        car.setActive(true);
        return car;
    }
}
