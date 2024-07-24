package com.khai.admin.constants;

public class MessResConstants {
    public static final String CREATE_ERR = "Tạo không thành công";
    public static final String AUTH_ERROR = "Lỗi xác thực";
    public static final String INTERNAL_SERVER_ERROR = "Lỗi server";
    public static final String CREATED_SUCCESS = "Tạo thành công";
    public static final String EXISTED_ERROR = "đã tồn tại";
    public static final String NOT_FOUND_ERROR = "không tồn tại";
    public static final String BLANK_REQUIRED_VALUES = "không được bỏ trống";
    public static class AuthMess {
        public static final String EMAIL_OR_PASS_ERROR = "Thông tin tài khoản hoặc mật khẩu không hợp lệ";
        public static final String REGISTER_ERROR = "Đăng ký không thành công";
        public static final String REGISTER_SUCCESS = "Đăng ký thành công";
        public static final String ACCOUNT_EXISTED_ERROR = "Email " + EXISTED_ERROR;
        public static final String ACCOUNT_NOT_ERROR = "Tên đăng nhập hoặc mật khẩu không đúng ";
        public static final String LOGIN_ERROR = "Đăng NHẬP không thành công";
    }

    public static class ProductMess {
        public static final String BLANK_NAME = "Tên sản phẩm " + BLANK_REQUIRED_VALUES;
    }
}
