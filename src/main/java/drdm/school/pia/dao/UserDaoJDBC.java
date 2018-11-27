package drdm.school.pia.dao;

import drdm.school.pia.domain.User;

import java.sql.*;

public class UserDaoJDBC implements UserDao {

    private static final String SQL_INSERT = "INSERT INTO drdm_user (username, password, role, firstname, lastname, email, address, city, zip, birthid, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_FIND_BY_USERNAME = "SELECT * FROM drdm_user WHERE username = ?";

    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;

    public UserDaoJDBC(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.jdbcUser = user;
        this.jdbcPassword = password;
    }

    @Override
    public User save(User value) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement pstm = conn.prepareStatement(SQL_INSERT)){

            pstm.setString(1, value.getUsername());
            pstm.setString(2, value.getPassword());
            pstm.setString(3, value.getRole());
            pstm.setString(4, value.getFirstname());
            pstm.setString(5, value.getLastname());
            pstm.setString(6, value.getEmail());
            pstm.setString(7, value.getAddress());
            pstm.setString(8, value.getCity());
            pstm.setString(9, value.getZip());
            pstm.setString(10, value.getBirthid());
            pstm.setString(11, value.getGender());

            System.out.println("Created SELECT: " + pstm.toString());

            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return value;
    }

    @Override
    public User findOne(Long id) {
        throw new UnsupportedOperationException("Not implemented!");
    }

    @Override
    public void remove(User toRemove) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public User findByUsername(String username) {
        User user = null;

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             PreparedStatement pstm = conn.prepareStatement(SQL_FIND_BY_USERNAME)){
            pstm.setString(1, username);
            user = mapResult(pstm.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private User mapResult(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        while (resultSet.next()){
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            String firstname = resultSet.getString("password");
            String lastname = resultSet.getString("password");
            String email = resultSet.getString("password");
            String address = resultSet.getString("password");
            String city = resultSet.getString("password");
            String zip = resultSet.getString("password");
            String birthid = resultSet.getString("password");
            String gender = resultSet.getString("password");

            return new User(username, password, role, firstname, lastname, email, address, city, zip, birthid, gender);
        }
        return null;
    }


}
