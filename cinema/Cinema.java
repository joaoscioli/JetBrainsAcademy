package cinema;

import java.util.Objects;
import java.util.Scanner;

public class Cinema {

    //Metodo Imprimir Cinema
    public static void imprimirCinema(int rows, int seats, String[][] cinema){
        System.out.println("Cinema:");
        System.out.print(" " + " ");
        for (int a = 1; a <= seats; a++ ){
            System.out.print( a + " ");
        }
        System.out.println();
        //imprimir array 2D
        int a = 1;
        for (int i = 0; i < rows; i++) {
            System.out.print(a++ + " ");
            for(int j = 0; j < seats; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    //Setter B cinema
    public static void setterCinemaB(int rows, int seats, String[][] cinema){
        for (int i = 0; i < cinema.length; ++i) {
            for(int j = 0; j < cinema[i].length; ++j) {
                cinema[i][j] = "S";
            }
        }
    }
    //Metodo pegar dados cinema ROWS
    public static int pegarDadosCinemaRows(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows: ");
        int rows = scanner.nextInt();
        return rows;
    }
    public static int pegarDadosCinemaSeats() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of seats in each row: ");
        int seats = scanner.nextInt();
        return seats;
    }
    //Metodo escolher cadeira
    public static int escolherRows() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a row number:");
        int rowEsc = scanner.nextInt();
        return rowEsc;
    }
    public static int escolherSeats() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a seat number in that row:");
        int seatEsc = scanner.nextInt();
        return seatEsc;
    }
    //Metodo Escolher funções
    public static void escolherFunction(){
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }
    //metodo saber preço
    public static int priceIngresso(int contagemRendaAtual,int rows, int rowEsc, int totalSala){
        int valorIngresso = 0;
        int valorPar = rows % 2;
        //Verifica Posição
        if (totalSala <= 60){
            valorIngresso = 10;
            contagemRendaAtual = valorIngresso;
            String dolar = "$"+valorIngresso;
            System.out.println("Ticket price: " + dolar);
        } else if(valorPar == 0 && rowEsc <= rows/2){
            valorIngresso = 10;
            contagemRendaAtual = valorIngresso;
            String dolar = "$"+valorIngresso;
            System.out.println("Ticket price: " + dolar);
        } else if (valorPar == 0 && rowEsc >= rows/2){
            valorIngresso = 8;
            contagemRendaAtual = valorIngresso;
            String dolar = "$"+valorIngresso;
            System.out.println("Ticket price: " + dolar);
        } else if (valorPar != 0 && rowEsc <= rows / 2){
            System.out.println(rowEsc);
            valorIngresso = 10;
            contagemRendaAtual = valorIngresso;
            String dolar = "$"+valorIngresso;
            System.out.println("Ticket price: " + dolar);
        } else if (valorPar != 0 && rowEsc >= ((rows / 2) + 1)){
            valorIngresso = 8;
            contagemRendaAtual = valorIngresso;
            String dolar = "$"+valorIngresso;
            System.out.println("Ticket price: " + dolar);
        }
        System.out.println();
        return contagemRendaAtual;
    }
    //Metodo Calcular total Sala
    public static int totalSala(int rows, int seats){
        return rows * seats;
    }
    //Metodo Calcular Quantidade Ingressos vendidos
    public static int ingressosComprados(String[][] cinema){
        int contagemIngressos = 0;
        for (int a = 0; a < cinema.length; a++){
            for (int b = 0; b < cinema.length; b++){
                if (cinema[a][b] == "B"){
                    contagemIngressos++;
                }
            }
        }
        return contagemIngressos;
    }
    //Metodo Calcular Porcentagem
    //Metodo Renda total
    public static int rendaTotalDolar(int totalcinema){
        int rendaTotal = 0;
        if (totalcinema <= 60){
            rendaTotal = totalcinema * 10;
        } else {
            rendaTotal = ((totalcinema / 2) * 10 + (totalcinema / 2) * 8);
        }
        return rendaTotal;
    }
    //Metodo Statisticas
    public static void statistics(int rendaTotal,int contagemRendaAtual, String[][] cinema,int totalSala){
        float porcentagem = (float) (ingressosComprados(cinema) * 100) / totalSala;
        System.out.println("Number of purchased tickets: " + ingressosComprados(cinema));
        System.out.printf("Percentage: %.2f%%" , porcentagem);
        System.out.println();
        System.out.println("Current income: " + "$" + contagemRendaAtual) ;
        System.out.println("Total income: " + "$" + rendaTotal);
        System.out.println();
    }
    //Metodo não comprar ingresso já comprado
    public static int verificarCompra(String[][] cinema, int rowsEsc, int seatEsc, int contagemRendaAtual, int rows, int totalSala,boolean parada){
        if (rowsEsc > cinema.length || seatEsc > cinema.length) {
            System.out.println("Wrong input!");
        }else if ("B" == cinema[--rowsEsc][--seatEsc]){
            System.out.println("That ticket has already been purchased!");
        } else {
            //contagemRendaAtual += priceIngresso(contagemRendaAtual,rows,rowsEsc,totalSala);
            contagemRendaAtual += priceIngresso(contagemRendaAtual,rows,rowsEsc,totalSala);
            cinema[rowsEsc][seatEsc] = "B";
            parada = false;
        }
        parada = parada;
        return contagemRendaAtual;
    }

        public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        final int finalPriceEight = 8;
        final int finalPriceTen = 10;
        //Entrada Num FILEIRAS E CADEIRAS
        int rows = pegarDadosCinemaRows();
        int seats = pegarDadosCinemaSeats();
        System.out.println();
        //Tamanho Sala Cinema
        int totalSala = totalSala(rows,seats);
        String[][] cinema = new String[rows][seats];

        int finalizar = 5;

        //começar a chamar os metodos
            int contagemRendaAtual = 0;
            int rendaTotal = rendaTotalDolar(totalSala);
            setterCinemaB(rows,seats,cinema);
        do {
            escolherFunction();
            int escolha =  scanner.nextInt();
            switch (escolha) {
                case 1:
                    imprimirCinema(rows, seats, cinema);
                    break;
                case 2:
                    boolean parada = true;
                    do {
                        int rowsEsc = escolherRows();
                        int seatEsc = escolherSeats();
                        if (rowsEsc > cinema.length || seatEsc > cinema.length){
                            System.out.println("Wrong input!");
                        } else if("B" == cinema[rowsEsc-1][seatEsc-1]){
                            System.out.println("That ticket has already been purchased!");
                        } else {
                            contagemRendaAtual += priceIngresso(contagemRendaAtual,rows,rowsEsc,totalSala);
                            cinema[--rowsEsc][--seatEsc] = "B";
                            parada = false;
                        }
                        //contagemRendaAtual = verificarCompra(cinema,rowsEsc,seatEsc,contagemRendaAtual,rows,totalSala, parada);
                    }while (parada);
                    //contagemRendaAtual += priceIngresso(contagemRendaAtual,rows,rowsEsc,totalSala);
                    //cinema[--rowsEsc][--seatEsc] = "B";
                    break;
                case 3:
                    statistics(rendaTotal,contagemRendaAtual,cinema,totalSala);
                    break;
                case 0:
                    finalizar = 0;
                    break;
            }
        }
        while (finalizar != 0);
    }
}