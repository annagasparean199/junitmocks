package org.example.dbdatacreation.databaseinsertioncsv;

public interface GenericDbInserter {

    void processCreatedDataAndInsertIntoDB();

    int getPriorityOfInsertionInDatabase();
}