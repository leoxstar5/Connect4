package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Create the UI
        new UIApp();

        // declare scanner for input
        Scanner in = new Scanner(System.in);

        // variables
        boolean shouldPlay = true;

        while (shouldPlay) {
            // welcome to game
            System.out.println(
                """
                *****************************
                *   Welcome to Connect 4!   *
                *****************************
                """
            );

            // create game instance and run game loop
            new Game(in).gameLoop();

            // if already played then ask if should play again
            System.out.print("Would you like to play again? ");

            // get response
            String response = in.next();
            if (response.toLowerCase().contains("no"))
                shouldPlay = false;
            else System.out.println();
        }
    }
}
