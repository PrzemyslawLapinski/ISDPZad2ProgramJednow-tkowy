package pl.lodz.p.it.isdp;

import java.sql.*;
import java.util.Properties;

public class JDBCConnection {
    public static void saveSortTable(String databaseName, String login, String password, SortTabNumbers sortExample) {
        final String driver = "jdbc:derby:";
        String url = driver.concat(databaseName); //jdbc:derby:ISDP
        Properties properties = new Properties();
        properties.setProperty("user", login);           //admin
        properties.setProperty("password", password); //admin1234

        int generatedKey = writeToSortTable(url, properties);

        writeToArrayElements(url, properties, generatedKey, sortExample);
    }

    private static int writeToSortTable(String url, Properties properties) {
        String sqlSortTable = "INSERT INTO SORTTABLE (DATEANDTIME) VALUES(CURRENT_TIMESTAMP)";
        int generatedKey = -1;
        try (Connection connection = DriverManager.getConnection(url, properties);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sqlSortTable, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQLExeption w writeToSortTable: " + e.getSQLState());
        } catch (Exception e) {
            System.out.println("Exception w writeToSortTable: " + e.getMessage());
        } finally {
            return generatedKey;
        }
    }

    private static void writeToArrayElements(String url, Properties properties, int generatedKey, SortTabNumbers sortExample) {
        String sqlSortTable = "INSERT INTO ARRAYELEMENTS (SORTTABLEID,VALUE) VALUES(?,?)";

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSortTable)
        ) {
            for (long element : sortExample.getTab()) {
                preparedStatement.setInt(1, generatedKey);
                preparedStatement.setLong(2, element);
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            System.out.println("SQLExeption w writeToArrayElements: " + e.getSQLState());
        } catch (Exception e) {
            System.out.println("Exception w writeToArrayElements: " + e.getMessage());
        }
    }


}
