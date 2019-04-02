package com.app.service;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.model.Car;
import com.app.converter.CarsJsonConverter;
import com.app.service.enums.SortType;
import com.app.validator.CarValidator;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarService {
    private final Set<Car> cars;

    public CarService(String jsonFilename) {
        this.cars = initializeCars(jsonFilename);
    }

    private Set<Car> initializeCars(String filename) {
        CarValidator carValidator = new CarValidator();
        AtomicInteger atomicInteger = new AtomicInteger(1);
        return new CarsJsonConverter(filename)
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.CARS, "FROM JSON CONVERSION IN CAR_SERVICE EXCEPTION"))
                .stream()
                .filter(car -> {
                    Map<String, String> errors = carValidator.validate(car);
                    if (carValidator.hasErrors()) {
                        System.out.println("\n\n-----------------------------------------------");
                        System.out.println("Exceptions in car no. " + atomicInteger.get());
                        System.out.println(errors
                                .entrySet()
                                .stream()
                                .map(e -> e.getKey() + " " + e.getValue())
                                .collect(Collectors.joining("\n"))
                        );
                        System.out.println("-----------------------------------------------\n\n");
                    }
                    atomicInteger.incrementAndGet();
                    return !carValidator.hasErrors();
                })
                .collect(Collectors.toSet());
    }

    public List<Car> sortCarsByArgument(SortType sortType, boolean descending) {
        if (sortType == null) {
            throw new MyException(ExceptionCode.CARS, "Sort type is not valid");
        }
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
                .filter(car -> car.getMileage() >= mileage)
                .collect(Collectors.toList());
    }

    public Map<String, Long> countCarsByColor() {
        return groupAllColors()
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
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
                        car -> cars
                                .stream()
                                .filter(car1 -> car1.getModel().equals(car.getModel()))
                                .max(Comparator.comparing(Car::getPrice))
                                .orElseThrow(NullPointerException::new),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    public String getCarsStatistics() {
        return MessageFormat.format(
                "Average price= {0}\n" +
                        "Minimum price= {1}\n" +
                        "Maximum price= {2}\n" +
                        "Average mileage= {3}\n" +
                        "Minimum mileage= {4}\n" +
                        "Maximum mileage= {5}\n",
                priceStats().getAverage(),
                priceStats().getMin(),
                priceStats().getMax(),
                mileageStats().getAverage(),
                mileageStats().getMin(),
                mileageStats().getMax()
        );
    }

    private BigDecimalSummaryStatistics priceStats() {
        return cars
                .stream()
                .collect(Collectors2.summarizingBigDecimal(Car::getPrice));
    }

    private LongSummaryStatistics mileageStats() {
        return cars
                .stream()
                .collect(Collectors.summarizingLong(Car::getMileage));
    }

    public List<Car> getTheMostExpensiveCar() {
        return cars
                .stream()
                .filter(car -> car.getPrice().equals(highestPrice()))
                .collect(Collectors.toList());
    }

    private BigDecimal highestPrice() {
        return cars
                .stream()
                .map(Car::getPrice)
                .max(BigDecimal::compareTo)
                .orElseThrow(NullPointerException::new);
    }

    public List<Car> getCarsWithSortedComponents() {
        return cars.stream()
                .peek(car -> car.setComponents(car
                        .getComponents()
                        .stream()
                        .sorted()
                        .collect(Collectors.toCollection(LinkedHashSet::new))))
                .collect(Collectors.toList());
    }

    public Map<String, Set<Car>> getCarsWithParticularComponent() {
        return getAllComponents()
                .stream()
                .collect(Collectors.toMap(
                        component -> component,
                        component -> cars
                                .stream()
                                .filter(car -> car.getComponents().contains(component))
                                .collect(Collectors.toSet())
                        )
                )
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    private Set<String> getAllComponents() {
        return cars
                .stream()
                .flatMap(car -> car.getComponents().stream())
                .collect(Collectors.toSet());
    }

    public List<Car> getCarsFromGivenPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice.compareTo(maxPrice) >= 0) {
            throw new MyException(ExceptionCode.CARS, "MIN PRICE IS GREATER THAN MAX PRICE");
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
