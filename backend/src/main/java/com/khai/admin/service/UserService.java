package com.khai.admin.service;

import com.khai.admin.dto.JwtView;
import com.khai.admin.dto.user.UserCreateDto;
import com.khai.admin.dto.user.UserView;
import com.khai.admin.entity.User;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StorageService storageService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(UserRepository _userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, StorageService storageService) {
        this.userRepository = _userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.storageService = storageService;
    }

    public UserView getUserInfo(int id) {
        Optional<User> user = userRepository.findUserById(id);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Tên tài khoản không tồn tại");
        }
        UserView userView = new UserView();
        userView.loadFromUser(user.get());
        return userView;
    }

//    public UserView getUserInfo(String email) {
//        var user = userRepository.getUserByEmail(email);
//        UserView userView = new UserView();
//        userView.loadFromUser(user);
//
//        return userView;
//    }
    /**
     * Phương thức dùng cho đăng nhập
     * @param username
     * @param password
     * @return
     */
//    public User getUser(String username, String password) {
//
//        return null;
//    }

    /**
     * Phương thúc lấy thông tin của người dùng đang yêu cầu
     * @return
     *      email của người dùng đang đươc xác thưc
     */
    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        // Nếu người dùng đã đăng nhập thì lấy thông tin chi tiết
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            // Trường hợp người gửi request không đăng nhập
            // principal có thể là một đối tượng vô danh hoặc một đối tượng khác
            username = principal.toString();
        }

        return username;
    }

    public JwtView authenticate(User user) {
        try {
//            String passworEncoded = passwordEncoder.encode(user.getPass());
            System.out.println(user);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = jwtService.generateAccessToken(userDetails);
            UserView userView = new UserView();
            userView.loadFromUser(user);

            return new JwtView(accessToken, userView);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sai tên tài khoản hoặc mật khẩu");
        }
    }

    public JwtView refreshToken(JwtView jwtView) {
        boolean isExpiredAccessToken = jwtService.isExpired(jwtView.getAccessToken());
        String username = jwtService.extractUsernameFromToken(jwtView.getAccessToken());
        String newAccessToken = jwtView.getAccessToken();
        if (isExpiredAccessToken) {
            newAccessToken = jwtService.generateAccessToken(username);
        }

        JwtView res = JwtView.builder()
                .accessToken(newAccessToken)
                .build();

        return res;
    }

    public boolean create(UserCreateDto userCreateDto) {
        if(userRepository.findFirstByUsername(userCreateDto.getEmail()).isPresent() ) {
            throw new AlreadyExist("Email", userCreateDto.getEmail());
        } else {
            String passworEncoded = passwordEncoder.encode(userCreateDto.getPassword());
            User user = User.builder()
                    .firstname(userCreateDto.getFirstName())
                    .lastname(userCreateDto.getLastName())
                    .email(userCreateDto.getEmail())
                    .password(passworEncoded)
                    .phone(userCreateDto.getPhoneNumber())
                    .active(true)
                    .createdAt(new Date())
                    .role(userCreateDto.getUserRole())
                    .build();
            try {
                userRepository.save(user);
                String accessToken = jwtService.generateAccessToken(userCreateDto.getEmail());
                UserView userView = new UserView();
                userView.loadFromUser(user);
                return true;
            } catch (DataAccessException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }

        }
    }
    public List<User> searchUser(String search) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root r = query.from(User.class);

        Predicate predicate = builder.conjunction();
        params.stream().forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        List<User> result = entityManager.createQuery(query).getResultList();
        return result;
    }
}
