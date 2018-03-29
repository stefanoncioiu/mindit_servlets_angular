/**
 *
 */
package ro.mindit.todo.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.mindit.todo.dao.TodoDao;
import ro.mindit.todo.model.Todo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


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

    }

    @Override
    public void destroy() {
    }

    private String getTodoFromDb(HttpServletRequest request) throws JsonProcessingException {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();

        String id1 = request.getQueryString();

        try {
            // Connect to the database
            todoDao.connect();

            if (id1 != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                Todo todo = todoDao.findOne(id);
                //todoDao.delete_from_table(id);
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

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            // Connect to the database
            todoDao.connect();


                int id = Integer.parseInt(request.getParameter("id"));
                todoDao.deleteTodo(id);


            // Disconnect from the database
            todoDao.disconnect();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String json="";
        ObjectMapper objectMapper = new ObjectMapper();
        ServletInputStream reqInputStream = request.getInputStream();
        Scanner sc = new Scanner(reqInputStream, "UTF-8").useDelimiter("\\A");

        json = sc.next();
        Todo value = objectMapper.readValue(json , Todo.class);

        String name= value.getName();
        String owner = value.getOwner();
        String priority = value.getPriority();


        try {

            todoDao.updateTodo(id,name,owner,priority);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }





    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        String json="";
        ObjectMapper objectMapper = new ObjectMapper();
        ServletInputStream reqInputStream = request.getInputStream();
        Scanner sc = new Scanner(reqInputStream, "UTF-8").useDelimiter("\\A");

        json = sc.next();
        Todo value = objectMapper.readValue(json , Todo.class);

        String name= value.getName();
        String owner = value.getOwner();
        String priority = value.getPriority();


        try {
            //response.sendRedirect("http://www.google.com");
            todoDao.addTodo(name,owner,priority);
            RequestDispatcher dispatcher = request.getRequestDispatcher("list.html");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }
}