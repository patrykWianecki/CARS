module service {
    exports com.app.service to validators, model, application;
    exports com.app.service.enums to model;

    requires converter;
    requires exceptions;
    requires model;
    requires validators;
    requires org.eclipse.collections.impl;
    requires org.eclipse.collections.api;
}