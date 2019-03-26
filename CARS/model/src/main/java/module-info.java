module model {
    exports com.app.model to service, converter, validators;
    exports com.app.model.enums to service, converter, validators;

    opens com.app.model;
}