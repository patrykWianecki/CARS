package com.app;

import com.app.model.Car;
import com.app.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AppTest {
    private CarService carService;

    @BeforeEach
    public void beforeEach() {
        carService = new CarService("CarsTest.json");
    }

    @Test
    @DisplayName("FIND ALL CARS WITH CORRECT PRICE RANGE")
    public void test2() {
        // given + when
        List<Car> cars = carService.getCarsFromGivenPriceRange(BigDecimal.valueOf(160), BigDecimal.valueOf(300));
        // then
        Assertions.assertEquals(cars.size(), 2, "FIND ALL CARS WITH CORRECT PRICE RANGE TEST FAILED");
    }

    @Test
    @DisplayName("FIND ALL CARS WITH PARTICULAR COMPONENT")
    public void test3() {
        // given + when
        Map<String, Set<Car>> cars = carService.getCarsWithParticularComponent();
        // then
        Assertions.assertEquals(cars.keySet().size(), 6, "NUMBER OF PARTICULAR COMPONENTS TEST FAILED");
        Assertions.assertEquals(cars.entrySet().stream().filter(component -> component.getKey().equals("ESP")).findFirst().orElse(null).getValue().size(), 1, "NUMBER OF CARS WITH PARTICULAR COMPONENT TEST FAILED");
    }

    @Test
    @DisplayName("FIND ALL CARS WITH SORTED COMPONENTS")
    public void test4() {
        // given + when
        List<Car> cars = carService.getCarsWithSortedComponents();
        // then
        Assertions.assertEquals(cars.get(0).getComponents().stream().findFirst().orElse("NO COMPONENT"), "ABS", "FIRST CAR SORTED COMPONENTS TEST FAILED");
        Assertions.assertEquals(cars.get(1).getComponents().stream().findFirst().orElse("NO COMPONENT"), "AIR CONDITIONING", "SECOND CAR SORTED COMPONENTS TEST FAILED");
        Assertions.assertEquals(cars.get(2).getComponents().stream().findFirst().orElse("NO COMPONENT"), "BLUETOOTH", "THIRD CAR SORTED COMPONENTS TEST FAILED");
    }

    @Test
    @DisplayName("FIND THE MOST EXPENSIVE CAR")
    public void test5() {
        // given + when
        List<Car> cars = carService.getTheMostExpensiveCar();
        // then
        Assertions.assertEquals(cars.size(), 2, "FIND THE MOST EXPENSIVE CAR TEST FAILED");
    }

    @Test
    @DisplayName("FIND THE MOST EXPENSIVE CAR MODEL")
    public void test6() {
        // given + when
        Map<String, Car> cars = carService.getTheMostExpensiveCarModels();
        // then
        Assertions.assertEquals(cars.values().stream().filter(c -> c.getModel().equals("MAZDA")).findFirst().orElse(null).getPrice(), BigDecimal.valueOf(160), "FIND THE MOST EXPENSIVE CAR MODEL TEST FAILED");
    }

    @Test
    @DisplayName("FIND CARS COUNTED BY COLOR")
    public void test7() {
        // given + when
        Map<String, Long> cars = carService.countCarsByColor();
        // then
        Assertions.assertEquals(cars.values().stream().findFirst().orElse(-1L), 2, "FIND CARS COUNTED BY COLOR TEST FAILED");
    }

    @Test
    @DisplayName("FIND ALL CARS WITH CORRECT MILEAGE")
    public void test8() {
        // given + when
        List<Car> cars = carService.sortCarsWithGreaterMileage(2000L);
        // then
        Assertions.assertEquals(cars.size(), 2, "FIND ALL CARS WITH CORRECT MILEAGE TEST FAILED");
    }
}
