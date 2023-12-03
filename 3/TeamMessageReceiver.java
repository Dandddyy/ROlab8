package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

public class TeamMessageReceiver implements MessageListener {

    public void startListening() {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("TeamQueue");

            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(this);

            System.out.println("Listening for messages...");

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {

                List<Team> teams = DatabaseManager.readTeamsFromDatabase();

                ObjectMessage objectMessage = (ObjectMessage) message;
                int clientRequest = objectMessage.getIntProperty("clientRequest");

                String serverResponse = processClientRequest(clientRequest, teams);
                System.out.println("Ответ от сервера: " + serverResponse);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private String processClientRequest(int clientRequest, List<Team> teams) {
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
        return "Операція успішно виконана";
    }
}
