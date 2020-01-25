module converter {
  exports com.app.converter to service, validators;

  requires model;
  requires java.sql;
  requires gson;
}