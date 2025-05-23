import java.util.Scanner;

public class id_220041102_lab1_task1 {
    public static void main(String[] ars) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        System.out.println("Q1: What is 5+5?");
        int ans = scanner.nextInt();
        if (ans == 10)score++;
        System.out.println("Q2: What is 5*3?");
        ans = scanner.nextInt();
        if (ans == 15)score++;
        System.out.println("Q3: What is 5-3?");
        ans = scanner.nextInt();
        if (ans == 2)score++;
        System.out.println("Q4: what is 10/5?");
        ans = scanner.nextInt();
        if (ans == 2)score++;
        System.out.println("Q5: WHat is 10%4?");
        ans = scanner.nextInt();
        if (ans == 2)score++;
        System.out.println("Your score is " + score);
        scanner.close();
    }
}
