package com.extra_tasks;

import java.util.Scanner;

//Next Dance Move
//        Time limit: 1000 ms
//        Memory limit: 256 MB
//
//        You've just learned a new dance, SalsaJS. It consists of 44 moves and goes like this:
//
//        1, 2, 3, 1, 2, 3, 1, 2, 3, 41,2,3,1,2,3,1,2,3,4
//
//        After that, you just keep repeating the sequence above.
//
//        What's the NthNth dance move that you need to do, if you're dancing SalsaJS indefinitely?
public class task_2 {
    public static void main(String[] args) {
        int[] results = new int[]{1, 2, 3, 1, 2, 3, 1, 2, 3, 4};
        int n = new Scanner(System.in).nextInt();
        System.out.println(results[(n - 1) % results.length]);
    }
}
