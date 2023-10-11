package carsharing.ui;

import carsharing.dao.Car;
import carsharing.dao.Company;
import carsharing.dao.CompanyDaoImpl;
import carsharing.dao.Customer;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    CompanyDaoImpl datasource = new CompanyDaoImpl();
    private final Scanner scanner = new Scanner(System.in);
    private boolean closeApp = false;

    int selectedCompanyId;
    String selectedCompanyName;
    int selectedCarId;
    int selectedCustomerId;


    public void uiMenu() {
        datasource.createEmptyTables();

        while (!closeApp) {
            selectedCompanyId = -1;
            selectedCarId = -1;
            selectedCustomerId = -1;
            selectedCompanyName = "";

            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");

            uiMenuOptions(uiGetInput());
        }
    }

    private int uiGetInput() {
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private void uiMenuOptions(int input) {
        switch (input) {
            case 0:
                closeApp = true;
                break;
            case 1:
                uiCompanyMenu();
                break;
            case 2:
                uiShowCustomerList("Customer list:", true);
                if (selectedCustomerId != -1) {
                    uiCustomerMenu();
                } else {
                    uiMenu();
                }

                break;
            case 3:
                String newCustomerName = uiGetName("Enter the customer name:");
                datasource.insertCustomer(newCustomerName);
                break;
            default:
                System.out.println("Incorrect input, please try again.\n");
                break;
        }
    }

    private void uiCompanyMenu() {
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");

        uiCompanyOptions(uiGetInput());
    }

    private void uiCompanyOptions(int input) {
        switch (input) {
            case 1:
                uiShowCompanyList("Choose the company:", true);
                if (selectedCompanyId != -1) {
                    System.out.println("'" + selectedCompanyName + "' company");
                    uiCarMenu();
                } else {
                    uiCompanyMenu();
                }

                break;
            case 2:
                String newCompanyName = uiGetName("Enter the company name:");
                datasource.insertCompany(newCompanyName);
                uiCompanyMenu();
                break;
            case 0:
                break;
            default:
                System.out.println("Incorrect input, please try again.\n");
                break;
        }
    }

    private void uiCarMenu() {
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");

        uiCarOptions(uiGetInput());
    }

    private void uiCarOptions(int input) {
        switch (input) {
            case 1:
                uiShowCarList("Car list:", false, false);
                uiCarMenu();
                break;
            case 2:
                String newCarName = uiGetName("Enter the car name:");
                datasource.insertCar(newCarName, selectedCompanyId);
                uiCarMenu();
                break;
            case 0:
                uiCompanyMenu();
                break;
            default:
                System.out.println("Incorrect input, please try again.\n");
                break;
        }
    }

    private void uiCustomerMenu() {
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");

        uiCustomerOptions(uiGetInput());
    }

    private void uiCustomerOptions(int input) {
        switch (input) {
            case 1:
                uiRentCar();
                uiCustomerMenu();
                break;
            case 2:
                uiReturnRentedCar();
                uiCustomerMenu();
                break;
            case 3:
                uiShowRentedCarInfo();
                uiCustomerMenu();
                break;
            case 0:
                break;
            default:
                System.out.println("Incorrect input, please try again.\n");
                break;
        }
    }

    private String uiGetName(String message) {
        String name;
        do {
            System.out.println(message);
            name = scanner.nextLine().trim();
        } while (name.length() == 0);
        return name;
    }


    private boolean uiShowCompanyList(String message, boolean isNeedToSelect) {
        List<Company> companies = datasource.getAllCompanies();
        List<Integer> companyIdInRow = new ArrayList<>();
        List<String> companyNameInRow = new ArrayList<>();

        if (companies == null || companies.isEmpty()) {
            System.out.println("\nThe company list is empty!\n");
            return false;
        }
        System.out.println(message);
        int row = 1;
        for (Company company : companies) {
            companyIdInRow.add(company.getId());
            companyNameInRow.add(company.getName());
            System.out.println((row++) + ". " + company.getName());
        }
        if (isNeedToSelect) {
            System.out.println("0. Back");
            int selectedIdx = selectedItem(companyIdInRow);
            if (selectedIdx >= 0) {
                selectedCompanyId = companyIdInRow.get(selectedIdx);
                selectedCompanyName = companyNameInRow.get(selectedIdx);
            } else {
                selectedCompanyId = -1;
                selectedCompanyName = "";
            }

        }
        System.out.println();
        return true;
    }

    private boolean uiShowCarList(String message, boolean isNeedToSelect, boolean forRent) {
        List<Car> cars;
        if (forRent) {
            cars = datasource.getAllCarsForRent();
        } else {
            cars = datasource.getAllCars();
        }
        List<Integer> carIdInRow = new ArrayList<>();

        if (cars == null || cars.isEmpty()) {
            System.out.println(forRent ? "No available cars in the '" + selectedCompanyName + "' company" : "\nThe car list is empty!\n");
            return false;
        }
        System.out.println(message);
        int row = 1;
        for (Car car : cars) {
            if (selectedCompanyId == -1) {
                carIdInRow.add(car.getId());
                System.out.println((row++) + ". " + car.getName());
            } else if (selectedCompanyId == car.getCompanyId()) {
                carIdInRow.add(car.getId());
                System.out.println((row++) + ". " + car.getName());
            }
        }
        if (carIdInRow == null || carIdInRow.isEmpty()) {
            System.out.println(forRent ? "No available cars in the '" + selectedCompanyName + "' company" : "\nThe car list is empty!\n");
            return false;
        }

        if (isNeedToSelect) {
            System.out.println("0. Back");
            int selectedIdx = selectedItem(carIdInRow);
            if (selectedIdx >= 0) {
                selectedCarId = carIdInRow.get(selectedIdx);
            } else {
                selectedCarId = -1;
            }
        }
        System.out.println();
        return true;
    }

    private boolean uiShowCustomerList(String message, boolean isNeedToSelect) {
        List<Customer> customers = datasource.getAllCustomers();
        List<Integer> customerIdInRow = new ArrayList<>();

        if (customers == null || customers.isEmpty()) {
            System.out.println("\nThe customer list is empty!\n");
            return false;
        }
        System.out.println(message);
        int row = 1;
        for (Customer customer : customers) {
            customerIdInRow.add(customer.getId());
            System.out.println((row++) + ". " + customer.getName());
        }

        if (isNeedToSelect) {
            System.out.println("0. Back");
            int selectedIdx = selectedItem(customerIdInRow);
            if (selectedIdx >= 0) {
                selectedCustomerId = customerIdInRow.get(selectedIdx);
            } else {
                selectedCustomerId = -1;
            }
        }
        System.out.println();
        return true;
    }

    private int selectedItem(List<Integer> choose) {
        int selected = uiGetInput();
        if (selected >= 0 & selected <= choose.size()) {
            return selected - 1;
        } else {
            return -1;
        }
    }

    private void uiShowRentedCarInfo() {
        Customer customer = datasource.getCustomerById(selectedCustomerId);

        if (customer.getRentedCarId() == 0) {
            System.out.println("\nYou didn't rent a car!\n");
        } else {
            Car car = datasource.getCarById(customer.getRentedCarId());
            Company company = datasource.getCompanyById(car.getCompanyId());

            System.out.println("\nYour rented car:\n" +
                    car.getName() +
                    "\nCompany:\n" +
                    company.getName() + "\n");
        }
    }

    private void uiRentCar() {
        Customer customer = datasource.getCustomerById(selectedCustomerId);

        if (customer.getRentedCarId() == 0) {
            uiShowCompanyList("Choose the company:", true);
            if (selectedCompanyId != -1) {
                uiShowCarList("Choose a car:", true, true);
                if (selectedCarId != -1) {
                    datasource.updateCustomerRentId(selectedCustomerId, selectedCarId);
                    Car car = datasource.getCarById(selectedCarId);
                    System.out.println("\nYou rented '" + car.getName() + "'\n");
                } else {
                    selectedCompanyId = -1;
                    selectedCompanyName = "";
                    uiCustomerMenu();
                }
            } else {
                uiCustomerMenu();
            }
        } else {
            System.out.println("\nYou've already rented a car!\n");
        }
    }

    private void uiReturnRentedCar() {
        Customer customer = datasource.getCustomerById(selectedCustomerId);

        if (customer.getRentedCarId() == 0) {
            System.out.println("\nYou didn't rent a car!\n");
        } else {
            datasource.updateCustomerRentId(selectedCustomerId, 0);

            System.out.println("\nYou've returned a rented car!\n");
        }
    }
}