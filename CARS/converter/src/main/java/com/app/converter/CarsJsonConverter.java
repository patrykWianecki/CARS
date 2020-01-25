package com.app.converter;

import java.util.List;

import com.app.model.Car;

public class CarsJsonConverter extends JsonConverter<List<Car>> {

  public CarsJsonConverter(String jsonFilename) {
    super(jsonFilename);
  }
}
