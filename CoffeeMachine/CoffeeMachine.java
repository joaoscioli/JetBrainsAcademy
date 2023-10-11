package machine;

import java.util.*;

public class CoffeeMachine {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
       machineCoffe machineCoffe = new machineCoffe(400,540,120,2,550);
        String escolha;
        do {
            System.out.println("Write action (buy, fill, take, remaining, exit): ");
            escolha = input.next();
            switch (escolha) {
               case "buy": machineCoffe.buy();
                   break;
               case "fill": machineCoffe.fill();
                   break;
               case "take": machineCoffe.take();
                   break;
               case "remaining": machineCoffe.status();
                   break;
           }
       } while(!escolha.equals("exit"));
    }
}
