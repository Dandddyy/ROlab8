import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) { // Замените "localhost" на адрес вашего сервера
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);

            while (true) {
                printMenu();
                int choice = scanner.nextInt();

                String serverResponse = sendRequestToServer(choice, out, in);
                System.out.println("Відповідь від сервера: " + serverResponse);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String sendRequestToServer(int choice, ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
        out.writeInt(choice);
        out.flush();

        String serverResponse = (String) in.readObject();

        return serverResponse;
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