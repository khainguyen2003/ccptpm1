package com.khai.admin.repository.jpa;

import com.khai.admin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // -------------- Dùng khi tự custom repository
//    public boolean addUser(User user) throws AlreadyExist, SQLException;
//    public boolean registerUser(User user) throws AlreadyExist, SQLException;
//    public boolean edit(User user, USER_EDIT_TYPE uet);
//    public boolean del(User user);
//    public User getUser(int id);
//    public User getUser(String username, String user_pass);
//    public List<User> getUsers(User similar, short at, int total, String sortColumn);
    // --------------
    Optional<User> findUserById(UUID id);

    @Query("SELECT u FROM User u WHERE u.email=:email")
    Optional<User> findFirstByEmail(@Param("email") String email);
}
