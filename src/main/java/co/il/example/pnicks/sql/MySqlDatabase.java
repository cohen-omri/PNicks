package co.il.parlagames.pnicks.sql;

import co.il.parlagames.pnicks.utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlDatabase {

    private void createTableIfNotExist() {

        final String sql = "CREATE TABLE IF NOT EXISTS players (uuid VARCHAR(64) NOT NULL, nick VARCHAR(16), isNicked BOOLEAN, lastupdated BIGINT)";


        /*try (final Connection connection = Utils.getPlugin().getHikari().getConnection();
             final Statement statement = connection.createStatement()) {


            statement.executeUpdate(sql);

        } catch (final SQLException e) {
            e.printStackTrace();
        }*/

    }

}
