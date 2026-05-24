package org.example;

import Service.UrlsService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);
        UrlsService urlsService = new UrlsService();

        server.createContext("/shorten-url", exchange -> {
            String method = exchange.getRequestMethod();
            System.out.println("get request");

            if(method.equals("POST")){
        try{
                InputStream inputStream = exchange.getRequestBody();
                String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
                String url = jsonObject.get("url").getAsString();

                String shortenUrl = urlsService.createShortUrl(url);

                // Set headers BEFORE sending response
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(201, shortenUrl.length());

                OutputStream os = exchange.getResponseBody();
                os.write(shortenUrl.getBytes());
                os.close();
            } catch (Exception e) {

            // 500 Internal Server Error
            e.printStackTrace(); // Log the error
            String error = "{\"error\": \"Internal server error\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(500, error.length());
            OutputStream os = exchange.getResponseBody();
            os.write(error.getBytes());
            os.close();
        }

            } else if(method.equals("GET")){
                try {
                    String path = exchange.getRequestURI().getPath();
                    String[] parts = path.split("/");

                    // Validation: Check if shortened code exists in URL
                    if(parts.length < 3 || parts[2].isEmpty()) {
                        // 400 Bad Request
                        String error = "{\"error\": \"Missing shortened code\"}";
                        exchange.getResponseHeaders().set("Content-Type", "application/json");
                        exchange.sendResponseHeaders(400, error.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(error.getBytes());
                        os.close();
                        return;
                    }

                    String shortenedCode = parts[2];
                    String originalUrl = urlsService.getOriginalUrl(shortenedCode);

                    // Check if URL was found
                    if(originalUrl.equals("Not found")) {
                        // 404 Not Found
                        String error = "{\"error\": \"URL not found\"}";
                        exchange.getResponseHeaders().set("Content-Type", "application/json");
                        exchange.sendResponseHeaders(404, error.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(error.getBytes());
                        os.close();
                        return;
                    }

                    // Success: 200 OK
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, originalUrl.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(originalUrl.getBytes());
                    os.close();

                } catch (Exception e) {
                    // 500 Internal Server Error
                    e.printStackTrace(); // Log the error
                    String error = "{\"error\": \"Internal server error\"}";
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(500, error.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(error.getBytes());
                    os.close();
                }
            }        });

        server.setExecutor(null);
        server.start();
    }
}
