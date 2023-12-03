package org.example;

import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = scanner.nextInt();

            TeamMessageSender messageSender = new TeamMessageSender();
            messageSender.sendMessage(choice);
        }
    }

    private static void printMenu() {
        System.out.println("Меню:");
        System.out.println("1. Додати нову команду Juventus");
        System.out.println("2. Видалити команду Real Madrid");
        System.out.println("3. Додати нового гравця Messi в команду Barcelona");
        System.out.println("4. Видалити гравця Sterling");
        System.out.println("5. Редагування відомостей про гравця Messi (поставити національність Spain)");
        System.out.println("6. Перевести гравця Mudryk в команду Juventus");
        System.out.println("7. Отримати список гравців з команди Barcelona");
        System.out.println("8. Отримати повний список команд");
        System.out.print("Ваш вибір: ");
    }
}
