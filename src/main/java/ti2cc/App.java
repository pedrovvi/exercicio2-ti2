package ti2cc;

import java.util.List;
import java.util.Scanner;

public class App {
    private static Scanner sc; 
    private static Dao dao;

    public static void main(String[] args) {
        App.dao = new Dao();        
        boolean connectionResult = dao.connect();

        if (connectionResult) System.out.println("Conexão efetuada com sucesso.");
        else System.err.println("Não foi possivel efetuar a conexão.");

        App.sc = new Scanner(System.in);
        int option;

        do {
            System.out.println("=".repeat(25));
            System.out.println("0 - Sair do programa;");
            System.out.println("1 - Registrar fruta;");
            System.out.println("2 - Buscar frutas pelo nome.");
            System.out.println("3 - Remover fruta pelo id.");
            System.out.println("=".repeat(25));
            System.out.println("> Escolha uma opção: ");

            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1: createFruit(); break;
                case 2: searchFruit(); break;
                case 3: deleteFruit(); break;
            }
        } while (option != 0);

        sc.close();
        dao.close();
    }

    private static void createFruit() {
        System.out.println("> Digite o nome da fruta: ");
        String name = sc.nextLine();

        System.out.println("> Digite o preço da fruta: ");
        float price = sc.nextFloat();

        boolean result = dao.createFruit(name, price);
        if (result) System.out.println("A fruta foi registrada com sucesso.");
        else System.err.println("Não foi possível registrar esta fruta.");
    }

    private static void searchFruit() {
        System.out.println("> Digite o nome da fruta: ");
        String name = sc.nextLine();

        List<Fruit> fruits = dao.getFruitsByName(name);

        if (fruits.size() > 0) { 
            fruits.forEach(fruit -> System.out.println(fruit.toString()));
            return;
        };

        System.err.println("Não foi possível encontrar esta fruta.");
    }

    private static void deleteFruit() {
        System.out.println("> Digite o id da fruta: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean result = dao.deleteFruitById(id);
        if (result) System.out.println("A fruta foi removida com sucesso.");
        else System.err.println("Não foi possível remover esta fruta.");
    }
}
