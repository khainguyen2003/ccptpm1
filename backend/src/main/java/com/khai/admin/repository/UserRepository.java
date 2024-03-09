package com.khai.admin.repository;

import com.khai.admin.constants.USER_EDIT_TYPE;
import com.khai.admin.dto.user.UserCreateDto;
import com.khai.admin.dto.user.UserEditDto;
import com.khai.admin.dto.user.UserView;
import com.khai.admin.entity.User;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.repository.share.ShareControl;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // -------------- Dùng khi tự custom repository
//    public boolean addUser(User user) throws AlreadyExist, SQLException;
//    public boolean registerUser(User user) throws AlreadyExist, SQLException;
//    public boolean edit(User user, USER_EDIT_TYPE uet);
//    public boolean del(User user);
//    public User getUser(int id);
//    public User getUser(String username, String user_pass);
//    public List<User> getUsers(User similar, short at, int total, String sortColumn);
    // --------------
    Optional<User> findUserById(int id);

    @Query("SELECT u FROM User u WHERE u.email=:username OR u.username=:username")
    Optional<User> findFirstByUsername(@Param("username") String username);
}
