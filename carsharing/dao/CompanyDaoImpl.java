package carsharing.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl {

    public static final String DB_NAME = "carsharing";
    public static final String CONNECTION_STRING = "jdbc:h2:./src/carsharing/db/" + DB_NAME;

    public static final String TABLE_COMPANY = "COMPANY";
    public static final String COLUMN_COMPANY_ID = "ID";
    public static final String COLUMN_COMPANY_NAME = "NAME";

    public static final String TABLE_CAR = "CAR";
    public static final String COLUMN_CAR_ID = "ID";
    public static final String COLUMN_CAR_NAME = "NAME";
    public static final String COLUMN_CAR_COMPANY_ID = "COMPANY_ID";

    public static final String TABLE_CUSTOMER = "CUSTOMER";
    public static final String COLUMN_CUSTOMER_ID = "ID";
    public static final String COLUMN_CUSTOMER_NAME = "NAME";
    public static final String COLUMN_CUSTOMER_RENTED_CAR_ID = "RENTED_CAR_ID";

//    public static final String DROP_TABLE_CUSTOMER =
//            "DROP TABLE IF EXISTS " + TABLE_CUSTOMER + ";";
//    public static final String DROP_TABLE_CAR =
//            "DROP TABLE IF EXISTS " + TABLE_CAR + ";";
//    public static final String DROP_TABLE_COMPANY =
//            "DROP TABLE IF EXISTS " + TABLE_COMPANY + ";";

    public static final String CREATE_COMPANY_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_COMPANY + " (" +
                    COLUMN_COMPANY_ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                    COLUMN_COMPANY_NAME + " VARCHAR(30) UNIQUE NOT NULL);";

    public static final String CREATE_CAR_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_CAR + " (" +
                    COLUMN_CAR_ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                    COLUMN_CAR_NAME + " VARCHAR(30) UNIQUE NOT NULL, " +
                    COLUMN_CAR_COMPANY_ID + " INT NOT NULL, " +
                    "FOREIGN KEY(" + COLUMN_CAR_COMPANY_ID + ") REFERENCES " + TABLE_COMPANY + ");";

    public static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_CUSTOMER + " (" +
                    COLUMN_CUSTOMER_ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                    COLUMN_CUSTOMER_NAME + " VARCHAR(30) UNIQUE NOT NULL, " +
                    COLUMN_CUSTOMER_RENTED_CAR_ID + " INT, " +
                    "FOREIGN KEY(" + COLUMN_CUSTOMER_RENTED_CAR_ID + ") REFERENCES " + TABLE_CAR + ");";

    public static final String INSERT_COMPANY =
            "INSERT INTO " + TABLE_COMPANY +
                    " (" + COLUMN_COMPANY_NAME + ") VALUES (?)";

    public static final String INSERT_CAR =
            "INSERT INTO " + TABLE_CAR +
                    " (" + COLUMN_COMPANY_NAME + ", " + COLUMN_CAR_COMPANY_ID + ") VALUES (?,?)";

    public static final String INSERT_CUSTOMER =
            "INSERT INTO " + TABLE_CUSTOMER +
                    " (" + COLUMN_CUSTOMER_NAME + ") VALUES (?)";


    public static final String QUERY_COMPANY_TABLE =
            "SELECT " + COLUMN_COMPANY_ID + ", " + COLUMN_COMPANY_NAME +
                    " FROM " + TABLE_COMPANY +
                    " ORDER BY " + COLUMN_COMPANY_ID + ";";

    public static final String QUERY_CAR_TABLE =
            "SELECT " + COLUMN_CAR_ID + ", " + COLUMN_CAR_NAME + ", " + COLUMN_CAR_COMPANY_ID +
                    " FROM " + TABLE_CAR +
                    " ORDER BY " + COLUMN_CAR_ID + ";";

    public static final String QUERY_CUSTOMER_TABLE =
            "SELECT " + COLUMN_CUSTOMER_ID + ", " + COLUMN_CUSTOMER_NAME + ", " + COLUMN_CUSTOMER_RENTED_CAR_ID +
                    " FROM " + TABLE_CUSTOMER +
                    " ORDER BY " + COLUMN_CUSTOMER_ID + ";";


    public static final String QUERY_COMPANY_TABLE_BY_ID =
            "SELECT " + COLUMN_COMPANY_ID + ", " + COLUMN_COMPANY_NAME +
                    " FROM " + TABLE_COMPANY +
                    " WHERE " + COLUMN_COMPANY_ID + "= ?;";

    public static final String QUERY_CAR_TABLE_BY_ID =
            "SELECT " + COLUMN_CAR_ID + ", " + COLUMN_CAR_NAME + ", " + COLUMN_CAR_COMPANY_ID +
                    " FROM " + TABLE_CAR +
                    " WHERE " + COLUMN_CAR_ID + "= ?;";

    public static final String QUERY_CUSTOMER_TABLE_BY_ID =
            "SELECT " + COLUMN_CUSTOMER_ID + ", " + COLUMN_CUSTOMER_NAME + ", " + COLUMN_CUSTOMER_RENTED_CAR_ID +
                    " FROM " + TABLE_CUSTOMER +
                    " WHERE " + COLUMN_CUSTOMER_ID + "= ?;";

    public static final String UPDATE_CUSTOMER_RENTAL_CAR_ID =
            "UPDATE " + TABLE_CUSTOMER +
                    " SET " + COLUMN_CUSTOMER_RENTED_CAR_ID + " = ?" +
                    " WHERE " + COLUMN_CUSTOMER_ID + " = ?;";

    public static final String UPDATE_CUSTOMER_RENTAL_CAR_ID_SET_NULL =
            "UPDATE " + TABLE_CUSTOMER +
                    " SET " + COLUMN_CUSTOMER_RENTED_CAR_ID + " = NULL" +
                    " WHERE " + COLUMN_CUSTOMER_ID + " = ?;";

    public static final String QUERY_CAR_TABLE_AVALIABLE_FOR_RENT =
            "SELECT " + TABLE_CAR + "." + COLUMN_CAR_ID + ", " +
                    TABLE_CAR + "." + COLUMN_CAR_NAME + ", " +
                    TABLE_CAR + "." + COLUMN_CAR_COMPANY_ID +
                    " FROM " + TABLE_CAR +
                    " LEFT JOIN " + TABLE_CUSTOMER + " ON " +
                    TABLE_CAR + "." + COLUMN_CAR_ID + " = " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_RENTED_CAR_ID +
                    " WHERE " + TABLE_CUSTOMER + "." + COLUMN_CUSTOMER_RENTED_CAR_ID + " IS NULL" +
                    " ORDER BY " + COLUMN_CAR_ID + " ;";

    private Connection conn;

    private PreparedStatement insertIntoCompany;
    private PreparedStatement insertIntoCar;
    private PreparedStatement insertIntoCustomer;

    private PreparedStatement QueryCompanyById;
    private PreparedStatement QueryCarById;
    private PreparedStatement QueryCustomerById;

    private PreparedStatement UpdateCustomerRentId;


    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (insertIntoCompany != null) insertIntoCompany.close();
            if (insertIntoCar != null) insertIntoCar.close();
            if (insertIntoCustomer != null) insertIntoCustomer.close();

            if (QueryCompanyById != null) QueryCompanyById.close();
            if (QueryCarById != null) QueryCarById.close();
            if (QueryCustomerById != null) QueryCustomerById.close();

            if (UpdateCustomerRentId != null) UpdateCustomerRentId.close();

            if (conn != null) conn.close();

        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    List<Company> companies;

    public void createEmptyTables() {
//        runQuery(DROP_TABLE_CUSTOMER);
//        runQuery(DROP_TABLE_CAR);
//        runQuery(DROP_TABLE_COMPANY);


        runQuery(CREATE_COMPANY_TABLE);
        runQuery(CREATE_CAR_TABLE);
        runQuery(CREATE_CUSTOMER_TABLE);
    }

    public void runQuery(String query) {
        open();
        StringBuilder sb = new StringBuilder(query);
        try (Statement statement = conn.createStatement()) {
            statement.execute(sb.toString());
        } catch (SQLException e) {
            System.out.println("Create View failed: " + e.getMessage());
        } finally {
            close();
        }
    }

    public void insertCompany(String companyName) {
        try {
            open();
            insertIntoCompany = conn.prepareStatement(INSERT_COMPANY);
            insertIntoCompany.setString(1, companyName);

            int affectedRows = insertIntoCompany.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("The company was created!");
            } else {
                throw new SQLException("The company insert failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void insertCar(String carName, int companyId) {
        try {
            open();
            insertIntoCar = conn.prepareStatement(INSERT_CAR);
            insertIntoCar.setString(1, carName);
            insertIntoCar.setInt(2, companyId);

            int affectedRows = insertIntoCar.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("The car was added!");
            } else {
                throw new SQLException("The car insert failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void insertCustomer(String CustomerName) {
        try {
            open();
            insertIntoCustomer = conn.prepareStatement(INSERT_CUSTOMER);
            insertIntoCustomer.setString(1, CustomerName);

            int affectedRows = insertIntoCustomer.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("The customer was added!");
            } else {
                throw new SQLException("The customer insert failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void updateCustomerRentId(int customerId, int rentCarId) {
        try {
            open();
            UpdateCustomerRentId = conn.prepareStatement(rentCarId != 0 ? UPDATE_CUSTOMER_RENTAL_CAR_ID : UPDATE_CUSTOMER_RENTAL_CAR_ID_SET_NULL);
            if (rentCarId != 0) {
                UpdateCustomerRentId.setInt(1, rentCarId);
                UpdateCustomerRentId.setInt(2, customerId);
            } else {
                UpdateCustomerRentId.setInt(1, customerId);
            }
            UpdateCustomerRentId.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public List<Company> getAllCompanies() {
        open();
        StringBuilder sb = new StringBuilder(QUERY_COMPANY_TABLE);
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<Company> companies = new ArrayList<>();
            while (results.next()) {
                Company company = new Company();
                company.setId(results.getInt(1));
                company.setName(results.getString(2));
                companies.add(company);
            }
            return companies;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        } finally {
            close();
        }
    }

    public List<Car> getAllCars() {
        open();
        StringBuilder sb = new StringBuilder(QUERY_CAR_TABLE);
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<Car> cars = new ArrayList<>();
            while (results.next()) {
                Car car = new Car();
                car.setId(results.getInt(1));
                car.setName(results.getString(2));
                car.setCompanyId(results.getInt(3));
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        } finally {
            close();
        }
    }

    public List<Car> getAllCarsForRent() {
        open();
        StringBuilder sb = new StringBuilder(QUERY_CAR_TABLE_AVALIABLE_FOR_RENT);
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<Car> cars = new ArrayList<>();
            while (results.next()) {
                Car car = new Car();
                car.setId(results.getInt(1));
                car.setName(results.getString(2));
                car.setCompanyId(results.getInt(3));
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        } finally {
            close();
        }
    }

    public List<Customer> getAllCustomers() {
        open();
        StringBuilder sb = new StringBuilder(QUERY_CUSTOMER_TABLE);
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<Customer> customers = new ArrayList<>();
            while (results.next()) {
                Customer customer = new Customer();
                customer.setId(results.getInt(1));
                customer.setName(results.getString(2));
                customer.setRentedCarId(results.getInt(3));
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        } finally {
            close();
        }
    }

    public Company getCompanyById(int companyId) {
        try {
            open();
            QueryCompanyById = conn.prepareStatement(QUERY_COMPANY_TABLE_BY_ID);
            QueryCompanyById.setInt(1, companyId);
            ResultSet result = QueryCompanyById.executeQuery();
            Company company = new Company();
            while (result.next()) {
                company.setId(result.getInt(1));
                company.setName(result.getString(2));
            }
            return company;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        } finally {
            close();
        }
    }

    public Car getCarById(int carId) {
        try {
            open();
            QueryCarById = conn.prepareStatement(QUERY_CAR_TABLE_BY_ID);
            QueryCarById.setInt(1, carId);
            ResultSet result = QueryCarById.executeQuery();
            Car car = new Car();
            while (result.next()) {
                car.setId(result.getInt(1));
                car.setName(result.getString(2));
                car.setCompanyId(result.getInt(3));
            }
            return car;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        } finally {
            close();
        }
    }

    public Customer getCustomerById(int customerId) {
        try {
            open();
            QueryCustomerById = conn.prepareStatement(QUERY_CUSTOMER_TABLE_BY_ID);
            QueryCustomerById.setInt(1, customerId);
            ResultSet result = QueryCustomerById.executeQuery();
            Customer customer = new Customer();
            while (result.next()) {
                customer.setId(result.getInt(1));
                customer.setName(result.getString(2));
                customer.setRentedCarId(result.getInt(3));
            }
            return customer;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        } finally {
            close();
        }
    }
}