package org.example.dbdatacreation.csventityreaders;

import java.util.List;

public interface GenericCSVReader<T> {

    List<T> readFromCSV(String filePath);
}
