//package com.khai.admin.repository;
//
//import com.khai.admin.constants.USER_ADD_TYPE;
//import com.khai.admin.constants.USER_EDIT_TYPE;
//import com.khai.admin.entity.User;
//import com.khai.admin.exception.AlreadyExist;
//import com.khai.admin.exception.NoSuchElementException;
//import com.khai.admin.repository.share.ConnectionPool;
//import com.khai.admin.repository.share.ExeQueryImpl;
//import com.khai.admin.util.DateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//public class UserRepositoryImpl extends ExeQueryImpl implements UserRepository {
//    @Autowired
//    public UserRepositoryImpl(ConnectionPool cp) {
//        super(cp, "user");
//    }
//    public UserRepositoryImpl(ConnectionPool cp, String objName) {
//        super(cp, objName);
//    }
//
//    private boolean add(User user, USER_ADD_TYPE type) throws AlreadyExist, SQLException {
//        if(this.isExist(user)) {
//            throw new AlreadyExist("user", user.getUser_name());
//        }
//        StringBuilder sql = new StringBuilder()
//                .append("INSERT INTO (")
//                .append("user_name, user_pass, user_email, user_created_at, user_last_modified");
//
//        if(type.equals(USER_ADD_TYPE.ADMIN_ADD)) {
//            sql.append(", ");
//            sql.append("user_avatar, user_notes, user_gender, user_job, user_position, user_permission");
//        }
//        sql.append(") VALUE(?,?,?,?,?");
//        if(type.equals(USER_ADD_TYPE.ADMIN_ADD)) {
//            sql.append("?,?,?,?,?,?");
//        }
//        sql.append(")");
//
//        try {
//            PreparedStatement pre = this.con.prepareStatement(sql.toString());
//            pre.setString(1, user.getUser_name());
//            pre.setString(2, user.getUser_pass());
//            pre.setString(3, user.getUser_email());
//            pre.setString(4, DateService.getCurrentDate());
//            pre.setString(5, DateService.getCurrentDate());
//
//            if(type.equals(USER_ADD_TYPE.ADMIN_ADD)) {
//                pre.setString(6, user.getUser_avatar());
//                pre.setString(7, user.getUser_note());
//                pre.setString(8, user.getUser_gender());
//                pre.setString(9, user.getUser_job());
//                pre.setString(10, user.getUser_position());
//                pre.setInt(11, user.getUser_permission());
//            }
//
//            return this.add(pre);
//        } catch (SQLException e) {
//            this.con.rollback();
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean addUser(User user) throws AlreadyExist, SQLException {
//        return add(user, USER_ADD_TYPE.ADMIN_ADD);
//    }
//
//    @Override
//    public boolean registerUser(User user) throws AlreadyExist, SQLException {
//        return add(user, null);
//    }
//
//    @Override
//    public boolean edit(User user, USER_EDIT_TYPE uet) {
//        return false;
//    }
//
//    @Override
//    public boolean del(User user) {
//        return false;
//    }
//
//    private boolean isExist(User user) {
//        boolean flag = false;
//        String sql = "SELECT u FROM tbluser u WHERE (u.user_name="+user.getUser_name()+") OR (user_email="+user.getUser_email()+")";
//        ResultSet resultSet = this.gets(sql);
//        try {
//            if(resultSet.next()) {
//                flag = true;
//            }
//            resultSet.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    @Override
//    public User getUser(int id) {
//        String sql = "";
//
//        return null;
//    }
//
//    @Override
//    public User getUser(String username, String user_pass) {
//        return null;
//    }
//
//
//    @Override
//    public List<User> getUsers(User similar, short at, int total, String sortColumn) {
//        return null;
//    }
//}
