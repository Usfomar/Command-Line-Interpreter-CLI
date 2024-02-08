/*
* This is Operating System Project that we want to simulate a simple command of linux operating system in java
* By Omar Yousef
* */
import java.io.IOException;


//The main class that drive the program
public class Main {
    public static void main(String[] args) throws IOException {
        Terminal terminal = new Terminal();
        terminal.CLI();
    }
}