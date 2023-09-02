package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/message")
public class MessageServlet extends HttpServlet {
    private List<Message> messageList = Collections.synchronizedList(new ArrayList<>());
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var writer = resp.getWriter();

        messageList.forEach(
                m -> writer.printf("<h1> - %s [%s]</h1>", m.getMessage(), m.getName())
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var message = readMessageFrom(req);
        messageList.add(message);
    }


    @SneakyThrows
    private Message readMessageFrom(HttpServletRequest req) {
        var reader = req.getReader();
        var jsonStr = reader.lines().collect(Collectors.joining());
        return objectMapper.readValue(jsonStr, Message.class);
    }

}
