package com.khai.admin.repository.share;

import java.sql.*;

public interface ConnectionPool {
    /**
     * Phương thức cung ccaaps kết nối khi một đối tượng cần
     * @param objectName
     * @return
     * @throws SQLException
     */
    public Connection getConnection(String objectName) throws SQLException;

    /**
     * Phương thức đẩy kết nối vào connection pool
     * @param con
     * @param objectName
     * @throws SQLException
     */
    public void releaseConnection(Connection con, String objectName) throws SQLException;
}
