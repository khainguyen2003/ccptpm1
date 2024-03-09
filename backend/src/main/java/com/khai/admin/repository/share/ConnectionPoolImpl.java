package com.khai.admin.repository.share;

import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.*;

public class ConnectionPoolImpl implements ConnectionPool{
    // Trình điều khiển làm việc với csdl
    private String driver;

    // Đường dẫn thực thi SQL
    @Value("${datasource.url}")
    private String url;

    // Tài khoản sử dụng sql
    @Value("${datasource.username}")
    private final String username;
    @Value("${datasource.password}")
    private final String userpass;

    // Đối tượng lưu trữ kết nối
    private Stack<Connection> pool;

    public ConnectionPoolImpl() {
        // Xác định trình điều khiển của MySQL
        this.driver = "com.mysql.jdbc.Driver";

        // Xác định đường dẫn thực thi của MySQL
        this.url = "jdbc:mysql://localhost:4000/jp210302_data?allowMultiQueries=true";

        // Xác định tài khoản làm việc
        this.username = "root";
        this.userpass = "Khai2003@";

        // Nạp trình điều khiển
        this.loadDriver();

        // Khơi tạo bộ nhớ đối tượng lưu trữ
        this.pool = new Stack<>();
    }

    /**
     * Phương thức nạp trình điều khiển
     */
    private void loadDriver() {
        try {
            /*
             * Tải lớp driver vào bộ nhớ và đăng ký nó với môi trường chạy Phương thức này
             * nhận một chuỗi tên lớp làm đối số và trả về một đối tượng kiểu Class tương
             * ứng với lớp đã được tải
             */
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            // in ra thông tin của lỗi như tên và dòng lỗi xuất hiện
            e.printStackTrace();
        }
    }

    // Phương thức tạo kết nối qua url, username, userpass
    @Override
    public Connection getConnection(String objectName) throws SQLException {
        // TODO Auto-generated method stub

        if (this.pool.isEmpty()) {
            // Khởi tạo kêt nối mới
            System.out.println(objectName + " have created a new Connection.");
            return DriverManager.getConnection(this.url, this.username, this.userpass);
        } else {
            // Lấy một kết nối đa lưu cho đối tượng
            System.out.println(objectName + " have popped the Connection.");
            // gọi ra kết nối mới nhất
            return this.pool.pop();
        }
    }

    // Phương thức đẩy vào kết nối
    @Override
    public void releaseConnection(Connection con, String objectName) throws SQLException {
        // TODO Auto-generated method stub

        // Yêu cầu các đối tượng trả về kết nối
        System.out.println(objectName + " have pushed the Connection");

        this.pool.push(con);
    }

    // phương thức đóng và thu dọn connection pool
    protected void finalize() throws Throwable {
        // Loại bỏ các đối tượng
        this.pool.clear();
        this.pool = null;

        // Thu gom rác
        System.gc();

        System.out.println("ConnectionPool is closed");
    }
}
