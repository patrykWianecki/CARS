package com.app.service;

public class MenuService {

  private final CarService CAR_SERVICE;
  private final UserDataService USER_DATA_SERVICE;

  public MenuService(String filename) {
    CAR_SERVICE = new CarService(filename);
    USER_DATA_SERVICE = new UserDataService();
  }

  public void mainMenu() {
    do {
      try {
        showMenu();
        int option = USER_DATA_SERVICE.getInt("Choose option");

        switch (option) {
          case 1 -> displayCarsByChosenAttribute();
          case 2 -> displayCarsWithGreaterMileageThanGiven();
          case 3 -> displayNumberOfCarsWithParticularColor();
          case 4 -> displayCarsWithTheMostExpensiveModel();
          case 5 -> displayCarsStatistics();
          case 6 -> displayTheMostExpensiveCar();
          case 7 -> displayCarsWithSortedComponents();
          case 8 -> displayCarsWithParticularComponent();
          case 9 -> displayCarsFromGivenPriceRange();
          case 10 -> {
            USER_DATA_SERVICE.close();
            System.out.println("The end");
            return;
          }
          default -> System.out.println("Invalid command!");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } while (true);
  }

  private void showMenu() {
    System.out.println("1 - sort cars by chosen attribute");
    System.out.println("2 - show cars with greater mileage");
    System.out.println("3 - show numer of cars with particular color");
    System.out.println("4 - sort cars with the most expensive model");
    System.out.println("5 - show cars statistics");
    System.out.println("6 - show the most expensive car/cars");
    System.out.println("7 - show cars with sorted components");
    System.out.println("8 - sort cars with particular component");
    System.out.println("9 - sort cars by price range");
    System.out.println("10 - exit");
  }

  private void displayCarsByChosenAttribute() {
    CAR_SERVICE.sortCarsByArgument(USER_DATA_SERVICE.getSortType(), USER_DATA_SERVICE.isOrderDescending())
        .forEach(System.out::println);
  }

  private void displayCarsWithGreaterMileageThanGiven() {
    CAR_SERVICE.sortCarsWithGreaterMileage(USER_DATA_SERVICE.getMileage())
        .forEach(System.out::println);
  }

  private void displayNumberOfCarsWithParticularColor() {
    CAR_SERVICE.countCarsByColor()
        .forEach((key, value) -> System.out.println("\n" + key + " " + value + "\n"));
  }

  private void displayCarsWithTheMostExpensiveModel() {
    CAR_SERVICE.getTheMostExpensiveCarModels()
        .forEach((key, value) -> System.out.println("\n" + key + " " + value + "\n"));
  }

  private void displayCarsStatistics() {
    System.out.println(CAR_SERVICE.getCarsStatistics());
  }

  private void displayTheMostExpensiveCar() {
    CAR_SERVICE.findTheMostExpensiveCars()
        .forEach(System.out::println);
  }

  private void displayCarsWithSortedComponents() {
    System.out.println(CAR_SERVICE.getCarsWithSortedComponents());
  }

  private void displayCarsWithParticularComponent() {
    CAR_SERVICE.getCarsWithParticularComponent()
        .forEach((key, value) -> System.out.println("\n" + key + " " + value + "\n"));
  }

  private void displayCarsFromGivenPriceRange() {
    CAR_SERVICE.getCarsFromGivenPriceRange(USER_DATA_SERVICE.getFirstPrice(), USER_DATA_SERVICE.getSecondPrice())
        .forEach(System.out::println);
  }
}
