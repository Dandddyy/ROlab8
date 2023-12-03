import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TeamService extends Remote {
    String processClientRequest(int clientRequest) throws RemoteException;
}
