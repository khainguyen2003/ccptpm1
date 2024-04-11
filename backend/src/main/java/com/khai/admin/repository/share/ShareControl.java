package com.khai.admin.repository.share;

public interface ShareControl {
    // Chia sẻ bộ quản lý kết nối giữa các basic với nhau
    public ConnectionPool getCP();
    // Ra lệch các đối tượng phải trả lại kết nối
    public void releaseConnection();
}
