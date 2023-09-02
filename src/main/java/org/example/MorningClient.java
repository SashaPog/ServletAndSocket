package org.example;

import jakarta.servlet.http.HttpServlet;
import lombok.SneakyThrows;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.stream.Stream;

public class MorningClient {
    @SneakyThrows
    public static void main(String[] args) {
        Socket clientSocket = new Socket(InetAddress.getLocalHost().getHostAddress(), 8080);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//        writer.println("GET /morning?name=Alex2 HTTP/1.1");
        writer.println("GET /morning HTTP/1.1");
        writer.println("Host: " + InetAddress.getLocalHost().getHostAddress());
        writer.println("X-Mood: Happy");
        // відправляємо імя навіть коли нема гет запит з(?name=Alex) за допомогою Cookie: JSESSIONID
        writer.println("Cookie: JSESSIONID=37F63E07CF5E078C4A741070E1C9C93C");// JSESSION беремо коли перший раз відправляємо гет запит з(?name=Alex)
        writer.println();
        writer.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        reader.lines().forEach(System.out::println);

    }
}
