import java.io.FileNotFoundException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
class Main {

    public static void main(String[] args) {
      userInterface();
    }

    public static void userInterface() {
        System.out.println("                                                      \033[34mWelcome user!\033[0m");
        System.out.println("                                                   \033[91mREAD BEFORE PLAYING\033[0m");
        System.out.println("This is a program that generates all the solutions of the N-Queen problem");
        System.out.println("You can either input a number to determine the number of queens and size of board and show all possible solutions \n" +
                "or you can choose the other option to see how many solutions and how much time does it take to compute for each value of N(1 to 14)");
        System.out.println();
        while(true) {
            try {
                int i;
                Scanner scanner3 = new Scanner(System.in);
                System.out.println("Press 1 if you want to do calculations or 2 if you want to end the program");
                i = Integer.parseInt(scanner3.nextLine());
                if (!(i == 1 || i == 2)) {
                    throw new IllegalArgumentException("Type 1 or 2");
                }
                else if (i == 2){
                    System.out.println("Goodbye!");
                    break;
                }
                try {
                    Scanner scanner = new Scanner(System.in);
                    int choice;
                    System.out.println("Type 1 if you want to input N");
                    System.out.println("Type 2 if you want to see statistics");
                    choice = Integer.parseInt(scanner.nextLine());

                    if (choice == 1 || choice == 2) {
                        if (choice == 1) {
                            int n;
                            while (true) {
                                try {
                                    System.out.println("Input N from 1 to 14");
                                    n = Integer.parseInt(scanner.nextLine());
                                    if (n <= 14 && n >= 1) {
                                        break;
                                    } else {
                                        throw new IllegalArgumentException("N should be between 1 and 14");
                                    }
                                } catch (NumberFormatException ex) {
                                    System.err.println("Invalid input format: " + ex.getMessage());
                                } catch (IllegalArgumentException ex) {
                                    System.err.println(ex.getMessage());
                                }
                            }
                            int[][] board = new int[n][n];
                            List<int[][]> solutions = solveNQueens(board, 0, n);
                            if (!solutions.isEmpty()) {
                                System.out.println("All solutions:");
                                for (int[][] sol : solutions) {
                                    printBoard(sol);
                                    System.out.println();
                                    System.out.println();
                                    System.out.println();
                                }
                                System.out.println("The # of solutions: " + solutions.size());
                            } else {
                                System.out.println("No solution found.");
                            }
                        } else {
                            HashMap<Integer, String> time = readFromFile("C:\\Users\\kleij\\IdeaProjects\\N-Queen Problem\\src\\time.txt");
                            int key;
                            while (true) {
                                try {
                                    System.out.println("Type a number which you want to get the statistics from");
                                    key = Integer.parseInt(scanner.nextLine());
                                    if (key <= 14 && key >= 1) {
                                        break;
                                    } else {
                                        throw new IllegalArgumentException("N should be between 1 and 14");
                                    }
                                } catch (NumberFormatException ex) {
                                    System.err.println("Invalid input format: " + ex.getMessage());
                                } catch (IllegalArgumentException ex) {
                                    System.err.println(ex.getMessage());
                                }
                            }
                            System.out.println(time.get(key));
                        }
                    } else {
                        throw new IllegalArgumentException("Type 1 or 2!");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid input format: " + ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    System.err.println(ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                System.err.println("Invalid input format: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }



    public static List<int[][]> solveNQueens(int[][] board, int col, int n) {
        List<int[][]> solutions = new ArrayList<>();
        if (col >= n) {
            solutions.add(copyBoard(board));
        } else {
            for (int row = 0; row < n; row++) {
                if (isSafe(board, row, col, n)) {
                    board[row][col] = 1;
                    solutions.addAll(solveNQueens(board, col+1, n));
                    board[row][col] = 0;
                }
            }
        }

        return solutions;
    }

    public static boolean isSafe(int[][] board, int row, int col, int n) {
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        for (int i = row, j = col; i < n && j >= 0; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        return true;
    }

    public static int[][] copyBoard(int[][] board) {
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[i].length);
        }
        return copy;
    }

    public static void printBoard(int[][] board) {
        for (int[] ints : board) {
            for (int j = 0; j < ints.length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }
    public static HashMap<Integer, String> readFromFile(String fileName) {
        HashMap<Integer, String> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int key = 1;
            while ((line = reader.readLine()) != null) {
                map.put(key, line);
                key++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }

        return map;
    }
}
