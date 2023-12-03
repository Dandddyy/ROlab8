import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            TeamService teamService = (TeamService) registry.lookup("TeamService");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                printMenu();
                int choice = scanner.nextInt();

                String serverResponse = teamService.processClientRequest(choice);
                System.out.println("Відповідь від сервера: " + serverResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
