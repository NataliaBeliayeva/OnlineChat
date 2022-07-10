import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UpdateChatThread extends Thread{

    int lastMessageId = -1;
    @Override
    public void run() {
        String query = "SELECT * FROM chat WHERE id > ? AND nickname <> ?";
        while (true) {
            try {
                PreparedStatement ps = Program.connection.prepareStatement(query);
                ps.setInt(1, lastMessageId);
                ps.setString(2, Program.nickName);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nick = rs.getString("nickname");
                    Timestamp time = rs.getTimestamp("time");
                    String message = rs.getString("message");
                    System.out.println(nick + " [" + time + "]: " + message);
                    if (lastMessageId < id) {
                        lastMessageId = id;
                    }

                }
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
