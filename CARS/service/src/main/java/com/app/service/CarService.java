package com.app.service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import com.app.converter.CarsJsonConverter;
import com.app.model.Car;
import com.app.service.enums.SortType;
import com.app.validator.CarValidator;

public class CarService {

  private final Set<Car> cars;

  public CarService(String jsonFilename) {
    this.cars = initializeCars(jsonFilename);
  }

  private Set<Car> initializeCars(String filename) {
    return new CarsJsonConverter(filename)
        .fromJson()
        .orElseThrow(() -> new IllegalStateException("Unable to initialize cars from JSON file"))
        .stream()
        .filter(CarValidator::isCarValid)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  public List<Car> sortCarsByArgument(SortType sortType, boolean descending) {
    Optional.ofNullable(sortType).orElseThrow(() -> new NullPointerException("Sort type is not valid"));

    Stream<Car> carStream = null;
    switch (sortType) {
      case COLOR:
        carStream = cars.stream().sorted(Comparator.comparing(Car::getColor));
        break;
      case MODEL:
        carStream = cars.stream().sorted(Comparator.comparing(Car::getModel));
        break;
      case PRICE:
        carStream = cars.stream().sorted(Comparator.comparing(Car::getPrice));
        break;
      case MILEAGE:
        carStream = cars.stream().sorted(Comparator.comparing(Car::getMileage));
    }

    List<Car> sortedCars = carStream.collect(Collectors.toList());
    if (descending) {
      Collections.reverse(sortedCars);
    }

    return sortedCars;
  }

  public List<Car> sortCarsWithGreaterMileage(Long mileage) {
    return cars
        .stream()
        .filter(car -> mileage <= car.getMileage())
        .collect(Collectors.toList());
  }

  public Map<String, Long> countCarsByColor() {
    return groupAllColors()
        .stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  private List<String> groupAllColors() {
    return cars
        .stream()
        .map(car -> String.valueOf(car.getColor()))
        .collect(Collectors.toList());
  }

  public Map<String, Car> getTheMostExpensiveCarModels() {
    return cars
        .stream()
        .collect(Collectors.toMap(
            Car::getModel,
            this::findMostExpensiveCarModel,
            (v1, v2) -> v1,
            LinkedHashMap::new
        ));
  }

  private Car findMostExpensiveCarModel(Car mostExpensiveModel) {
    return cars
        .stream()
        .filter(car -> mostExpensiveModel.getModel().equals(car.getModel()))
        .max(Comparator.comparing(Car::getPrice))
        .orElseThrow(() -> new NullPointerException("Missing the most expensive car model"));
  }

  public String getCarsStatistics() {
    BigDecimalSummaryStatistics priceStats = createPriceStats();
    LongSummaryStatistics mileageStats = createMileageStats();

    return MessageFormat.format(
        "Average price= {0}\n" +
            "Minimum price= {1}\n" +
            "Maximum price= {2}\n" +
            "Average mileage= {3}\n" +
            "Minimum mileage= {4}\n" +
            "Maximum mileage= {5}\n",
        priceStats.getAverage(), priceStats.getMin(), priceStats.getMax(),
        mileageStats.getAverage(), mileageStats.getMin(), mileageStats.getMax()
    );
  }

  private BigDecimalSummaryStatistics createPriceStats() {
    return cars
        .stream()
        .collect(Collectors2.summarizingBigDecimal(Car::getPrice));
  }

  private LongSummaryStatistics createMileageStats() {
    return cars
        .stream()
        .collect(Collectors.summarizingLong(Car::getMileage));
  }

  public List<Car> findTheMostExpensiveCars() {
    return cars
        .stream()
        .filter(car -> car.getPrice().equals(findHighestPrice()))
        .collect(Collectors.toList());
  }

  private BigDecimal findHighestPrice() {
    return cars
        .stream()
        .map(Car::getPrice)
        .max(BigDecimal::compareTo)
        .orElseThrow(() -> new NullPointerException("Missing car with the biggest price"));
  }

  public List<Car> getCarsWithSortedComponents() {
    System.out.println(cars);
    return cars.stream()
        .peek(car -> car.setComponents(sortCarComponents(car)))
        .collect(Collectors.toList());
  }

  private static Set<String> sortCarComponents(Car car) {
    return car
        .getComponents()
        .stream()
        .sorted()
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  public Map<String, Set<Car>> getCarsWithParticularComponent() {
    return getAllComponents()
        .stream()
        .collect(Collectors.toMap(
            Function.identity(),
            this::collectCarsWithComponent));
  }

  private Set<Car> collectCarsWithComponent(String component) {
    return cars
        .stream()
        .filter(car -> car.getComponents().contains(component))
        .collect(Collectors.toSet());
  }

  private Set<String> getAllComponents() {
    return cars
        .stream()
        .flatMap(car -> car.getComponents().stream())
        .collect(Collectors.toSet());
  }

  public List<Car> getCarsFromGivenPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
    if (minPrice.compareTo(maxPrice) >= 0) {
      throw new IllegalArgumentException("Min price is greater than max price");
    }
    return cars
        .stream()
        .filter(car -> car.getPrice().compareTo(minPrice) >= 0 && car.getPrice().compareTo(maxPrice) <= 0)
        .sorted(Comparator.comparing(Car::getModel))
        .collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return String.valueOf(cars);
  }
}
