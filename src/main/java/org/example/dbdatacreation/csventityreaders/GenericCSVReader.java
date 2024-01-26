package org.example.dbdatacreation.csventityreaders;

import org.example.entities.Delivery;

import java.util.List;

public interface GenericCSVReader<T> {

    List<T> readFromCSV(String filePath);
}
