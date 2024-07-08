package org.example.system;

import org.example.entity.Article;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                System.out.println(id + "번 글이 작성 되었습니다");

                Connection conn = null;
                PreparedStatement pstmt = null;

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");
                    System.out.println("연결 성공!");

                    String sql = "INSERT INTO article ";
                    sql += "SET regDate = NOW(),";
                    sql += "updateDate = NOW(),";
                    sql += "title = '" + title + "',";
                    sql += "`body` = '" + body + "';";

                    System.out.println(sql);

                    pstmt = conn.prepareStatement(sql);

                    int affectedRows = pstmt.executeUpdate();

                    System.out.println("affected rows: " + affectedRows);

                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                //
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