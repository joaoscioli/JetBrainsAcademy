package machine;

import java.util.Scanner;

import static machine.CoffeeMachine.input;

public class machineCoffe {
    int water;
    int milk;
    int coffee;
    int cups;
    int money;

    public void fill() {
        System.out.println();
        System.out.println("Write how many ml of water you want to add: ");
        setWater(getWater() + input.nextInt());
        System.out.println("Write how many ml of milk you want to add: ");
        setMilk(getMilk() + input.nextInt());
        System.out.println("Write how many grams of coffee beans you want to add: ");
        setCoffee(getCoffee() + input.nextInt());
        System.out.println("Write how many disposable cups you want to add: ");
        setCups(getCups() + input.nextInt());
        System.out.println();
    }
    public void take() {
        System.out.println();
        System.out.println("I gave you $" + getMoney() + "\n");
        setMoney(getMoney() * 0);
    }

    public void espresso() {
        if (getCups() >= ((getCups() - 1)*(-1))
                && getWater() >= ((getWater() - 250)*(-1))
                && getCoffee() >= ((getCoffee() - 16)*(-1))) {
            System.out.println("I have enough resources, making you a coffee!");
            setCups(getCups() - 1);
            setWater(getWater() - 250);
            setCoffee(getCoffee() - 16);
            setMoney(getMoney() + 4);
        } else if (getCups() < ((getCups() - 1)*(-1))) {
            System.out.println("Sorry, not enough cups!");
        } else if (getWater() < ((getWater() - 250)*(-1))) {
            System.out.println("Sorry, not enough water!");
        } else if (getCoffee() < ((getCoffee() - 16)*(-1))) {
            System.out.println("Sorry, not enough coffee beans!");
        }
    }

    public void latte() {
        if (getCups() >= ((getCups() - 1)*(-1))
                && getWater() >= ((getWater() - 350)*(-1))
                && getMilk() >= ((getMilk() - 75)*(-1))
                && getCoffee() >= ((getCoffee() - 20)*(-1))) {
            System.out.println("I have enough resources, making you a coffee!");
            setCups(getCups() - 1);
            setWater(getWater() - 350);
            setMilk(getMilk() - 75);
            setCoffee(getCoffee() - 20);
            setMoney(getMoney() + 7);
        } else if (getCups() < ((getCups() - 1)*(-1))) {
            System.out.println("Sorry, not enough cups!");
        } else if (getWater() < ((getWater() - 350)*(-1))) {
            System.out.println("Sorry, not enough water!");
        } else if (getMilk() < ((getMilk() - 75)*(-1))) {
            System.out.println("Sorry, not enough milk!");
        } else if (getCoffee() < ((getCoffee() - 20)*(-1))) {
            System.out.println("Sorry, not enough coffee beans!");
        }
    }

    public void cappuccino() {
        if (getCups() >= ((getCups() - 1)*(-1))
                && getWater() >= ((getWater() - 200)*(-1))
                && getMilk() >= ((getMilk() - 100)*(-1))
                && getCoffee() >= ((getCoffee() - 12)*(-1))) {
            System.out.println("I have enough resources, making you a coffee!");
            setCups(getCups() - 1);
            setWater(getWater() - 200);
            setMilk(getMilk() - 100);
            setCoffee(getCoffee() - 12);
            setMoney(getMoney() + 6);
        } else if (getCups() < ((getCups() - 1)*(-1))) {
            System.out.println("Sorry, not enough cups!");
        } else if (getWater() < ((getWater() - 200)*(-1))) {
            System.out.println("Sorry, not enough water!");
        } else if (getMilk() < ((getMilk() - 100)*(-1))) {
            System.out.println("Sorry, not enough milk!");
        } else if (getCoffee() < ((getCoffee() - 12)*(-1))) {
            System.out.println("Sorry, not enough coffee beans!");
        }
    }

    public void buy() {
        System.out.println();
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
        String escolha = input.next();
        switch (escolha) {
            case "1": espresso();
            break;
            case "2": latte();
            break;
            case "3": cappuccino();
            break;
            case "back": return;
        }
        System.out.println();
    }

    public machineCoffe(int water, int milk, int coffee, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
        this.cups = cups;
        this.money = money;
    }

    public void status() {
        System.out.println();
        String statusMachine = "The coffee machine has:\n" +
                +this.getWater() +" ml of water\n" +
                +this.getMilk()+" ml of milk\n" +
                +this.getCoffee()+" g of coffee beans\n" +
                +this.getCups()+" disposable cups\n" +
                "$"+this.getMoney()+" of money\n";
        System.out.println(statusMachine);
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public int getCoffee() {
        return coffee;
    }

    public void setCoffee(int coffee) {
        this.coffee = coffee;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
