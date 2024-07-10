package org.example.system;

import org.example.entity.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private Scanner sc;
    int lastId;

    public App(Scanner sc) {
        this.sc = sc;
        lastId = 0;
    }

    public void run() {
        System.out.println("==Article Start==");


        while (true) {
            System.out.print("command ) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                break;

            } else if (cmd.length() == 0) {
                System.out.println("명령어 입력해");
                continue;
            }
            if (cmd.equals("article write")) {
                System.out.println("==글쓰기==");
                int id = lastId + 1;
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();

                lastId++;
                System.out.println(id + "번 글이 작성되었습니다");
/////////////////////////////////////////////////////////////////////////////////////
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
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else if (cmd.equals("article list")) {
                System.out.println("==목록==");

                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;

                List<Article> articles = new ArrayList<>();

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");
                    System.out.println("연결 성공!");

                    String sql = "SELECT * ";
                    sql += "FROM article ";
                    sql += "ORDER BY id DESC";

                    System.out.println(sql);
                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery(sql);

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String regDate = rs.getString("regDate");
                        String updateDate = rs.getString("updateDate");
                        String title = rs.getString("title");
                        String body = rs.getString("body");
                        Article article = new Article(id, regDate, updateDate, title, body);
                        articles.add(article);
                    }

                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (rs != null && !rs.isClosed()) {
                            rs.close();
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
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (articles.size() == 0) {
                    System.out.println("게시글이 없습니다");
                    continue;
                }
                System.out.println("   번호    /    제목     ");
                for (Article article : articles) {
                    System.out.printf("     %d      /    %s    \n", article.getId(), article.getTitle());
                }

            } else if (cmd.startsWith("article modify")) {

                int id = 0;

                try {
                    id = Integer.parseInt(cmd.split(" ")[2]);
                } catch (Exception e) {
                    System.out.println("정수 입력");
                    continue;
                }

                System.out.println("==글 수정==");
                System.out.print("새 제목 : ");
                String title = sc.nextLine();
                System.out.print("새 내용 : ");
                String body = sc.nextLine();

                //////////////////////////////

                Connection conn = null;
                PreparedStatement pstmt = null;

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");
                    System.out.println("연결 성공!");

                    String sql = "UPDATE article ";
                    sql += "SET updateDate = NOW()";
                    if (title.length() > 0) {
                        sql += " , title = '" + title + "'";
                    }
                    if (body.length() > 0) {
                        sql += " , `body` = '" + body + "'";
                    }
                    sql += " WHERE id = " + id + ";";

                    System.out.println(sql);

                    pstmt = conn.prepareStatement(sql);

                    pstmt.executeUpdate();


                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(id + "번 글이 수정되었습니다.");
            }
        }
    }
}
