package com.extra_tasks;

import java.util.Scanner;

//Spring Love
//        Time limit: 1280 ms
//        Memory limit: 264 MB
//
//        There's an old game called He loves me... he loves me not. The person picks petals from the flower thinking about a special someone. At the end if the number of picked petals is odd the special someone is in love. You're about find out the outcome of the game. You know there's a garden with NN flowers, flower ii having P_iP
//        ​i
//        ​​  petals. At the end of the game all petals from all flowers will be picked. Find out is the special someone is in love or not.
public class task_1 {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        int n = reader.nextInt();

        int s = 0;
        for (int i = 0; i < n; i++) {
            s += reader.nextInt();
        }

        System.out.println(s % 2);
    }
}
