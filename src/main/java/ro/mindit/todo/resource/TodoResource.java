/**
 *
 */
package ro.mindit.todo.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.mindit.todo.dao.TodoDao;
import ro.mindit.todo.model.Todo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class TodoResource extends HttpServlet {

    private TodoDao todoDao;

    @Override
    public void init() throws ServletException {
        todoDao = new TodoDao();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // set response content type
        response.setContentType("application/json");

//        String json = getTodoFromMemory(request);
		String json = getTodoFromDb(request);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    @Override
    public void destroy() {
    }

    private String getTodoFromDb(HttpServletRequest request) throws JsonProcessingException {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();

        String id = request.getQueryString();
        try {
            // Connect to the database
            todoDao.connect();

            if (id != null) {
                Todo todo = todoDao.findOne(1);
                json = objectMapper.writeValueAsString(todo);
            } else {
                List<Todo> todos = todoDao.findAll();
                json = objectMapper.writeValueAsString(todos);
            }

            // Disconnect from the database
            todoDao.disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return json;
    }

    private String getTodoFromMemory(HttpServletRequest request) throws JsonProcessingException {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();

        String id = request.getQueryString();
        if (id != null) {
            Todo todo = todoDao.getTodo(1);
            json = objectMapper.writeValueAsString(todo);
        } else {
            List<Todo> todos = todoDao.queryAll();
            json = objectMapper.writeValueAsString(todos);
        }
        return json;
    }
}