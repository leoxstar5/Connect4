package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Direction
{
    public static int[] UpRight =  new int[]{ -1, +1 };
    public static int[] Right =    new int[]{ 0, +1 };
    public static int[] DownRight =    new int[]{ +1, +1 };
    public static int[] Down =         new int[]{ +1, 0 };
    public static int[] DownLeft =     new int[]{ +1, -1 };
    public static int[] Left =     new int[]{ 0, -1 };
    public static int[] UpLeft =   new int[]{ -1, -1 };
}

public final class Game
{
    // declare class variables
    private char[][] board;
    private final Scanner in;
    private boolean player1Turn;
    private int[] positionPlaced;
    private boolean gameOver;

    // constructor to set default values
    public Game(Scanner in)
    {
        // initialize class variables
        this.board = new char[6][7];
        this.in = in; // scanner from Main
        this.player1Turn = true;
        this.gameOver = false;

        // populate board with ' '
        for (char[] chars : board) {
            Arrays.fill(chars, ' ');
        }
    }

    private void displayBoard()
    {
        System.out.println("_____________________________");
        for (char[] column: board)
        {
            System.out.print('|');
            for (char cell: column)
            {
                // determine color
                String color = (cell == 'R' ? ConsoleColors.RED : cell == 'Y' ? ConsoleColors.YELLOW : null);

                // print cell in format
                System.out.print((color != null ? color : "") + " " + cell + ConsoleColors.RESET +  " |");
            }
            System.out.println();
        }

        // visual divider
        System.out.println("|===========================|");

        // print column numbers
        int counter = 1;
        System.out.print('|');
        for (char ignored : board[0]) // ignored since we don't need the value just the iterations.
        {
            System.out.print(" " + counter + " |");
            counter++;
        }
        System.out.println();
    }

    public void gameLoop()
    {
        // game loop
        while(!gameOver)
        {
            // display board
            displayBoard();

            boolean validColumn = false;
            while(!validColumn)
            {
                // get input from player
                int columnChosen = getPlayerInput();

                if(placePiece(columnChosen))
                {
                    validColumn = true;
                } else {
                    System.out.println("Choose a different column!");
                }
            }

            // check if player won
            if(checkWinner())
            {
                displayBoard();
                System.out.println(
                    "Player: " + (player1Turn ? "1": "2") +
                    "(" + ((player1Turn ? (ConsoleColors.YELLOW + "Y") : (ConsoleColors.RED + 'R'))) +
                    ConsoleColors.RESET + ") WON!!! Woo Hoo!!!");

                gameOver = true;
            } else {
                // next player turn
                player1Turn = !player1Turn;
            }
        }
    }

    private boolean checkWinner() {
        // list of directions paired for each axis
        int[][] directions = new int[][]{
            Direction.Right,
            Direction.Left,
            Direction.UpRight,
            Direction.DownLeft,
            Direction.UpLeft,
            Direction.DownRight
        };

        ArrayList<Integer> countList = new ArrayList<Integer>(); // for each axis
        int increment = 0; // check if even (complete of axis (every 2 iterations))
        int count = 1; // add for each axis
        for(int[] direction: directions)
        {
            int result = check(direction, 0, positionPlaced);

            count += result;

            if (increment % 2 == 1)
            {
                countList.add(count);

                // reset count for next axis
                count = 1;
            }
            increment++;
        }

        // attempt to check down
        if (positionPlaced[0] <= 2) // placed piece is placed 4 or higher
        {
            int result = check(Direction.Down, 1, positionPlaced);
            countList.add(result);
        }

        // check if results have 4 or more
        for(int item: countList)
        {
            if (item >= 4) {
                return true;
            }
        }

        return false;
    }

    private boolean inBounds(int[] position)
    {
        return (position[0] >= 0 && position[0] <= 5 && position[1] >= 0 && position[1] <= 6);
    }

    // recursive function to check how many in a row
    private int check(int[] direction, int count, int[] currentPos) {
        // increment in direction
        int[] checkPos = new int[]{currentPos[0] + direction[0], currentPos[1] + direction[1]};
        if(inBounds(checkPos))
        {
            // if the same piece as placed piece
            if (board[checkPos[0]][checkPos[1]] == (player1Turn ? 'Y' : 'R'))
            {
                return check(direction, count + 1, checkPos); // recursive call to check next position
            } else return count;
        } else return count; // return if out of bounds
    }

    private boolean placePiece(int columnChosen) {
        // check if column is in bounds
        if (columnChosen <= 0 || columnChosen >= 8)
            return false;

        int[] placePos = {-1, -1}; // [row, column]
        int actualColumn = columnChosen -1;

        // loop through column from bottom find first open spot
        for(int i = 0; i < board.length; i++)
        {
            if(board[i][actualColumn] == ' ')
            {
                placePos[0] = i; // c
                placePos[1] = actualColumn; // r
            }
        }

        if (!Arrays.equals(placePos, new int[]{-1, -1})) {
            board[placePos[0]][placePos[1]] = (player1Turn ? 'Y' : 'R');
            positionPlaced = placePos;
            return true;
        } else {
            return false; // couldn't place piece
        }
    }

    public static boolean isNumeric(String string) {

        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

    private int getPlayerInput() {
        System.out.print(
            (player1Turn ? "Player 1 (" + ConsoleColors.YELLOW + "Y" :
            "Player 2 (" + ConsoleColors.RED + "R") +
            ConsoleColors.RESET +  ") enter the column number: ");

        while (true)
        {
            String input = in.next();

            // check if type int
            if (isNumeric(input))
            {
                return Integer.parseInt(input);
            } else {
                System.out.print("Input is not a valid number!\n: ");
            }
        }

    }
}
/*
_____________________________
| Y | Y | Y | Y | Y | Y | Y |
| Y | Y | Y | Y | Y | Y | Y |
| Y | Y | Y | Y | Y | Y | Y |
| Y | Y | Y | Y | Y | Y | Y |
| Y | Y | Y | Y | Y | Y | Y |
| Y | Y | Y | Y | Y | Y | Y |
|===========================|
| 1 | 2 | 3 | 4 | 5 | 6 | 7 |

*/