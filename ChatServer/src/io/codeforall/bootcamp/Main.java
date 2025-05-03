package io.codeforall.bootcamp;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(9090);
        server.listen();
    }
}
