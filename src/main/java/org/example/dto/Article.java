package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private int id;
    private String regDate;
    private String updateDate;
    private int memberId;
    private String title;
    private String body;
    private String name;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", regDate='" + regDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", memberId=" + memberId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Article(Map<String, Object> articleMap) {
        this.id = (int) articleMap.get("id");
        this.regDate = (String) articleMap.get("regDate");
        this.updateDate = (String) articleMap.get("updateDate");
        this.memberId = (int) articleMap.get("memberId");
        this.title = (String) articleMap.get("title");
        this.body = (String) articleMap.get("body");
        this.name = (String) articleMap.get("name");
    }


}