package org.example.dao;

import org.example.DB.DBUtil;
import org.example.Sql.SecSql;

import java.sql.Connection;

public class MemberDao {

    public boolean isLoginIdDup(Connection conn, String loginId) {
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `member`");
        sql.append("WHERE loginId = ?;", loginId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }
}