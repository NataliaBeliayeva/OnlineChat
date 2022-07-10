import java.sql.*;
import java.util.Scanner;

public class Program {
    public static Connection connection;
    public static String nickName;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net/sql11503183",
                    "sql11503183", "H3bmPeiYt3");
            enterNickName();
            UpdateChatThread updateChatThread = new UpdateChatThread();
            updateChatThread.start();
            enterMessage();
        } catch (Exception e) {
            throw new RuntimeException();
        }


    }

    private static void enterMessage() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            String message = scanner.nextLine();
            if (!message.isBlank()){
                saveMessage(message);
            }
        }
    }

    private static void saveMessage(String message) {
        String query = "INSERT INTO `chat`(`nickname`, `time`, `message`) VALUES (?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nickName);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setString(3, message);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void enterNickName() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите ваш никнейм");
            String nick = scanner.nextLine();
            if (!nick.isBlank()){
                nickName = nick;
                break;
            }
            else{
                System.out.println("Ник не может быть пустым");
            }
        }

    }
}
