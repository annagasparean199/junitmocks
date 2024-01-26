package org.example.dbdatacreation.csvdatavalidation;

public interface GenericDataValidator<T> {

    boolean isValid(T entity);
}
