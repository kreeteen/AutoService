package autoservice.jdbc;

import autoservice.domain.inmemory.*;
import autoservice.domain.repository.*;
import autoservice.jdbc.repository.*;
import autoservice.domain.inmemory.*;
import autoservice.domain.repository.*;
import autoservice.jdbc.repository.*;

public class RepositoryFactory {

    public enum StorageType {
        IN_MEMORY,
        JDBC
    }

    private static StorageType currentStorageType = StorageType.IN_MEMORY;

    public static void setStorageType(StorageType type) {
        currentStorageType = type;
        System.out.println("Тип хранилища установлен: " + type);
    }

    public static ClientRepository createClientRepository() {
        switch (currentStorageType) {
            case JDBC:
                return new JdbcClientRepository();
            case IN_MEMORY:
            default:
                return new InMemoryClientRepository();
        }
    }

    public static EmployeeRepository createEmployeeRepository() {
        switch (currentStorageType) {
            case JDBC:
                return new JdbcEmployeeRepository();
            case IN_MEMORY:
            default:
                return new InMemoryEmployeeRepository();
        }
    }

    public static CarRepository createCarRepository() {
        switch (currentStorageType) {
            case JDBC:
                return new JdbcCarRepository();
            case IN_MEMORY:
            default:
                return new InMemoryCarRepository();
        }
    }

    public static ServiceOrderRepository createServiceOrderRepository() {
        switch (currentStorageType) {
            case JDBC:
                return new JdbcServiceOrderRepository();
            case IN_MEMORY:
            default:
                return new InMemoryServiceOrderRepository();
        }
    }

    public static OrderEmployeeRepository createOrderEmployeeRepository() {
        switch (currentStorageType) {
            case JDBC:
                return new JdbcOrderEmployeeRepository();
            case IN_MEMORY:
            default:
                return new InMemoryOrderEmployeeRepository();
        }
    }

    public static PartReplacementRepository createPartReplacementRepository() {
        switch (currentStorageType) {
            case JDBC:
                return new JdbcPartReplacementRepository();
            case IN_MEMORY:
            default:
                return new InMemoryPartReplacementRepository();
        }
    }

    public static StorageType getStorageType() {
        return currentStorageType;
    }
}