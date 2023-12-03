import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(12345);

            System.out.println("Waiting for client connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");
            handleClient(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            List<Team> teams = DatabaseManager.readTeamsFromDatabase();

            while(true) {
                int clientRequest = in.readInt();

                String serverResponse = processClientRequest(clientRequest, teams);

                out.writeObject(serverResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processClientRequest(int clientRequest, List<Team> teams) {
        switch (clientRequest) {
            case 1:
                teams.add(new Team());
                teams.get(3).setId(4);
                teams.get(3).setPlayers(new ArrayList<Player>());
                teams.get(3).setName("Juventus");
                teams.get(3).setCountry("Italy");
                break;
            case 2:
                teams.remove(2);
                break;
            case 3:
                teams.get(1).getPlayers().add(new Player());
                teams.get(1).getPlayers().get(1).setId(2);
                teams.get(1).getPlayers().get(1).setName("Messi");
                teams.get(1).getPlayers().get(1).setCountry("Argentina");
                teams.get(1).getPlayers().get(1).setPosition("RW");
                break;
            case 4:
                teams.get(0).getPlayers().remove(2);
                break;
            case 5:
                teams.get(1).getPlayers().get(1).setCountry("Spain");
                break;
            case 6:
                teams.get(2).getPlayers().add(new Player());
                teams.get(2).getPlayers().set(0, teams.get(0).getPlayers().get(0));
                teams.get(0).getPlayers().remove(0);
                break;
            case 7:
                String str = "Список гравців Barcelona:";
                for(int i = 0; i < teams.get(1).getPlayers().size(); i++){
                    str += "\nID: " + teams.get(1).getPlayers().get(i).getId();
                    str += "\nName: " + teams.get(1).getPlayers().get(i).getName();
                    str += "\nCountry: " + teams.get(1).getPlayers().get(i).getCountry();
                    str += "\nPosition: " + teams.get(1).getPlayers().get(i).getPosition();
                    str += "\n---------------";
                }
                return str;
            case 8:
                String str1 = "Список команд:";
                for(int i = 0; i < teams.size(); i++){
                    str1 += "\nID: " + teams.get(i).getId();
                    str1 += "\nName: " + teams.get(i).getName();
                    str1 += "\nCountry: " + teams.get(i).getCountry();
                    str1 += "\n---------------";
                }
                return str1;
            default:
                return "Невірний код запиту";
        }
        DatabaseManager.writeTeamsToDatabase(teams);
        return "Операція успішно виповнена";
    }
}
