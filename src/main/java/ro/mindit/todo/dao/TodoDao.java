package ro.mindit.todo.dao;

import ro.mindit.todo.model.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ro.mindit.todo.util.Constants.*;

public class TodoDao {
    private int idSeq = 1;
    private List<Todo> todos;

    private static Connection jdbcConnection;

    public TodoDao() {
        final Todo todo = new Todo() {{
            setId(idSeq++);
            setName("Todo 1");
            setOwner("Adi");
            setPriority("High");
        }};
        todos = new ArrayList<Todo>() {{
            add(todo);
        }};
    }

    public List<Todo> queryAll() {
        return todos;
    }

    public Todo getTodo(int id) {
        Todo result = null;
        for (Todo todo : todos) {
            if (todo.getId() == id) {
                result = todo;
                break;
            }
        }
        return result;
    }


    public static void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName(jdbcDriver);
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    public static void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public Todo findOne(int id) throws SQLException {
        Todo todo = null;
        String sql = "SELECT * FROM forum WHERE id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String owner = resultSet.getString("owner");
            String priority = resultSet.getString("priority");

            todo = new Todo(id, name, owner, priority);
        }

        resultSet.close();
        statement.close();

        return todo;
    }

    public static List<Todo> findAll() throws SQLException {
        List<Todo> todoList = new ArrayList<Todo>();

        String sql = "SELECT * FROM forum";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nume = resultSet.getString("name");
            String owner = resultSet.getString("owner");
            String priority = resultSet.getString("priority");
            Todo todo = new Todo(id, nume, owner, priority);
            todoList.add(todo);

        }

        resultSet.close();
        statement.close();

        disconnect();

        return todoList;
    }

    public void addTodo(String name, String owner, String priority) throws SQLException {

        String sql = "INSERT INTO forum (name, owner, priority) VALUES (?,?,?)";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, owner);
        statement.setString(3, priority);

        statement.executeUpdate();

        statement.close();
        disconnect();


    }

    public void updateTodo(int id, String name, String owner, String priority) throws SQLException {


        String sql = "UPDATE forum set name=?, owner=?, priority=? where id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, owner);
        statement.setString(3, priority);
        statement.setInt(4,id);

        statement.executeUpdate();

        statement.close();
        disconnect();


    }

    public void deleteTodo(int id) throws SQLException {

        String sql = "DELETE from forum where id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();

        statement.close();
        disconnect();
    }

    public static void main(String args[]) throws SQLException {


        TodoDao q = new TodoDao();
        //System.out.println(q.findAll());
        //q.addTodo("nume_test1","owner_test1","high1");
        //q.updateTable(2,"num_updt","own_updt","prio_updt");
        //q.deleteFromTable(1);


    }
}
