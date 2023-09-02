package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@WebServlet("/morning")
public class MorningServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printClientMood(req);
        printClientCookies(req);
        printMorning(req, resp);
        resp.addCookie(new Cookie("SUPER_ID", UUID.randomUUID().toString()));


    }

    private void printClientCookies(HttpServletRequest req) {
        Stream.of(req.getCookies())
                .forEach(c -> System.out.println(c.getName() + " " + c.getValue()));
    }

    @SneakyThrows
    private void printMorning(HttpServletRequest req, HttpServletResponse resp) {// створення сесії
        var session = req.getSession();
        var sessionName = Optional.ofNullable((String)session.getAttribute("name"));
        var requestName = Optional.ofNullable(req.getParameter("name"));
        requestName
                .filter(name -> sessionName.isEmpty())
                .ifPresent(name -> session.setAttribute("name", name));

        var writer = resp.getWriter();
        var name = requestName
                .or(() -> sessionName)
                .orElse("my friend");
        writer.println("Good Morning, " + name);
    }

    @SneakyThrows
    private void simpleMorning(HttpServletRequest req, HttpServletResponse resp) {
        var writer = resp.getWriter();
        var name = Optional.ofNullable(req.getParameter("name"))
                .orElse("my friend");
        writer.println("Good Morning, " + name);
    }

    private void printClientMood(HttpServletRequest req) {
        var name = Optional.ofNullable(req.getParameter("name"))
                        .orElse(req.getRemoteAddr());
        Optional.ofNullable(req.getHeader("X-Mood"))
                .ifPresent(mood-> System.out.println(name + " " + "is feeling " + mood));
    }
}
