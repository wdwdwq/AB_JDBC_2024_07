package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private Scanner sc;

    public App(Scanner sc) {
        this.sc = sc;
    }

    public void run() {
        System.out.println("==Article Start==");

        int lastId = 0;

        List<Article> articles = new ArrayList<>();

        while (true) {
            System.out.print("command ) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                System.out.println("==Article exit==");
                break;

            } else if (cmd.length() == 0) {
                System.out.println("명령어 입력해");
                continue;
            }
            if (cmd.equals("article write")) {
                System.out.println("==글쓰기==");
                int id = lastId + 1;
                System.out.print("title : ");
                String title = sc.nextLine();
                System.out.print("body : ");
                String body = sc.nextLine();

                Article article = new Article(id, title, body);

                articles.add(article);

                System.out.println(article);
                lastId++;
            } else if (cmd.equals("article list")) {
                System.out.println("==Article List==");
                if (articles.size() == 0) {
                    System.out.println("게시물 없음");
                    continue;
                }
                System.out.println("    id      //      title       ");
                for (Article article : articles) {
                    System.out.printf("    %d      //      %s        \n", article.getId(), article.getTitle());
                }

            }
        }
        System.out.println("==프로그램 종료==");

    }

}

