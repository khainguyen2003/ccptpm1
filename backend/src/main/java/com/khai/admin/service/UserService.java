package com.khai.admin.service;

import com.khai.admin.dto.JwtView;
import com.khai.admin.dto.user.UserCreateDto;
import com.khai.admin.dto.user.UserProfileDto;
import com.khai.admin.entity.User;
import com.khai.admin.entity.security.KeyStore;
import com.khai.admin.exception.AlreadyExist;
import com.khai.admin.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JwtServiceV2 jwtServiceV2;
    private final AuthenticationManager authenticationManager;
    private final FileService fileService;
    private final KeyTokenService keyTokenService;

    private HttpSession session;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(
            UserRepository _userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            JwtServiceV2 jwtServiceV2,
            AuthenticationManager authenticationManager,
            FileService fileService,
            HttpSession session,
            KeyTokenService keyTokenService
    ) {
        this.userRepository = _userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtServiceV2 = jwtServiceV2;
        this.authenticationManager = authenticationManager;
        this.fileService = fileService;
        this.session = session;
        this.keyTokenService = keyTokenService;
        this.jwtService = jwtService;
    }

    public UserProfileDto getUserInfo(UUID id) {
        Optional<User> user = userRepository.findUserById(id);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Tên tài khoản không tồn tại");
        }
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.loadFromUser(user.get());
        return userProfileDto;
    }
    public User getUserById(UUID id) {
        Optional<User> user = userRepository.findUserById(id);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Tên tài khoản không tồn tại");
        }
        return user.get();
    }

    public UserProfileDto getUserInfoByEmail(String email) {
        Optional<User> user = userRepository.findFirstByEmail(email);
        if(user.isPresent()) {
            UserProfileDto userProfileDto = new UserProfileDto();
            userProfileDto.loadFromUser(user.get());
            return userProfileDto;
        } else {
            throw new UsernameNotFoundException("Không tồn tại người dùng có email " + email);
        }

    }
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
     *      email của người dùng đã đươc xác thưc
     */
    public String getCurrentUserEmail() {
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

    public User getCurrentLogin() {
        String email = getCurrentUserEmail();
        if(email == null) {
            return null;
        }
        return userRepository.findFirstByEmail(email).get();
    }

    // chuyển lại bằng cách bỏ signinKey và chuyển thành jwtService


    /**
     * Các bước login
     * 1. check email
     * 2. match password
     * 3. Tạo AT và RT và lừu
     * 4. Generate token
     * 5. get data return login
     * @param user
     * @return
     */
//    public JwtView login(User user) {
//        try {
////            String passworEncoded = passwordEncoder.encode(user.getPass());
//            System.out.println(user);
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
//            );
//
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String accessToken = jwtService.generateAccessToken(userDetails);
//            UserView userView = new UserView();
//            userView.loadFromUser(user);
//
//            return new JwtView(accessToken, userView);
//        } catch (AuthenticationException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sai tên tài khoản hoặc mật khẩu");
//        }
//    }

    /**
     * Các bước login
     * 1. check email
     * 2. match password
     * 3. Tạo AT và RT và lừu
     * 4. Generate token
     * 5. get data return login
     * @param request
     * @return
     */
    public JwtView loginV2(UserCreateDto request) {

        try {
            // vertify user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findFirstByEmail(request.getEmail()).get();
            // update security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // generate AT and RT
            KeyPair keyPair = keyTokenService.generateKeyPair();
            String accessToken = jwtServiceV2.generateAccessToken(user, keyPair.getPrivate());
            String refreshToken = jwtServiceV2.generateRefreshToken(user, keyPair.getPrivate());
            // save to dbs
            KeyStore keyStore = new KeyStore();
            keyStore.setPrivateKey(keyPair.getPrivate().getEncoded());
            keyStore.setPublicKey(keyPair.getPublic().getEncoded());
            keyStore.setUser(user);
            keyStore.setRefreshToken(refreshToken);
            keyTokenService.save(keyStore);
            // convert to response
            UserProfileDto userProfileDto = new UserProfileDto();
            userProfileDto.loadFromUser(user);

            return new JwtView(accessToken, refreshToken, userProfileDto);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sai tên tài khoản hoặc mật khẩu");
        }
    }

    /**
     * Các bước kiểm tra
     * <br/>1. Kiểm tra userId có tồn tại trong header request không
     * <br/>2. Kiểm ra userId có tồn tại trong bảng keyStore (certificate center) không
     * <br/>3. Kiểm tra accessToken có tồn tại trong request không
     * <br/>4. decode payload và kiểm tra userId có trùng với userId trong payload khôgn
     * <br/>5. Nếu pass thì trả kết quả
     * @param user
     * @return
     */
    public JwtView authenticate(Map<String, String> headers, User user) {
        UUID userId = UUID.fromString(headers.get("userId"));
        if(userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lỗi xác thực");
        }

        try {
            KeyStore keyStore = keyTokenService.getKeyStoreByUserId(userId);

            String accessToken = headers.get("x-access-token");
            if(accessToken == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lỗi xác thực");
            }

            Claims claims = jwtServiceV2.parseToken(accessToken, keyTokenService.decodePublicKey(keyStore.getPublicKey()));
            if(userId != UUID.fromString(claims.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lỗi xác thực");
            }
            KeyPair keyPair = keyTokenService.generateKeyPair();
            String accessKey = jwtServiceV2.generateAccessToken(user, keyPair.getPrivate());
            String refreshToken = jwtServiceV2.generateRefreshToken(user, keyPair.getPrivate());
            // save to dbs
            keyStore.setPrivateKey(keyPair.getPrivate().getEncoded());
            keyStore.setPublicKey(keyPair.getPublic().getEncoded());
            keyTokenService.save(keyStore);
            // convert to response
            UserProfileDto userProfileDto = new UserProfileDto();
            userProfileDto.loadFromUser(user);

            return new JwtView(accessKey, refreshToken);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sai tên tài khoản hoặc mật khẩu");
        } catch (InvalidKeySpecException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "public key không hợp lệ");
        }
    }

    public JwtView refreshToken(JwtView jwtView, PublicKey publicKey, PrivateKey privateKey) {
        boolean isExpiredAccessToken = jwtServiceV2.isExpired(jwtView.getAccessToken(), publicKey);
        Claims claims = jwtServiceV2.parseToken(jwtView.getAccessToken(), publicKey);
        String newAccessToken = jwtView.getAccessToken();
        if (isExpiredAccessToken) {
            String username = claims.getSubject();
            User user = userRepository.findFirstByEmail(username).get();
            newAccessToken = jwtServiceV2.generateAccessToken(user, privateKey);
        }

        JwtView res = JwtView.builder()
                .accessToken(newAccessToken)
                .build();

        return res;
    }

//    public UserProfileDto create(UserCreateDto userCreateDto) {
//        if(userRepository.findFirstByEmail(userCreateDto.getEmail()).isPresent() ) {
//            throw new AlreadyExist("Email", userCreateDto.getEmail());
//        } else {
//            String passworEncoded = passwordEncoder.encode(userCreateDto.getPassword());
//            User user = User.builder()
//                    .username(userCreateDto.getEmail())
//                    .firstname(userCreateDto.getFirstName())
//                    .lastname(userCreateDto.getLastName())
//                    .email(userCreateDto.getEmail())
//                    .password(passworEncoded)
//                    .phone(userCreateDto.getPhoneNumber())
//                    .active(true)
//                    .createdAt(new Date())
//                    .role(userCreateDto.getUserRole())
//                    .build();
//            try {
//                userRepository.save(user);
//                String accessToken = jwtServiceV2.generateAccessToken(userCreateDto.getEmail());
//                UserProfileDto userView = new UserProfileDto();
//                userView.loadFromUser(user);
//                return userView;
//            } catch (DataAccessException e) {
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//            } catch (Exception e) {
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//            }
//
//        }
//    }

    public void logout(UUID userId) {
        keyTokenService.deleteTokenById(userId);
    }

    public UserProfileDto createV2(UserCreateDto userCreateDto) {
        if(userRepository.findFirstByEmail(userCreateDto.getEmail()).isPresent() ) {
            throw new AlreadyExist("Email", userCreateDto.getEmail());
        } else {
            Date now = new Date();
            String passworEncoded = passwordEncoder.encode(userCreateDto.getPassword());
            User user = User.builder()
                    .username(userCreateDto.getEmail())
                    .firstname(userCreateDto.getFirstName())
                    .lastname(userCreateDto.getLastName())
                    .email(userCreateDto.getEmail())
                    .password(passworEncoded)
                    .phone(userCreateDto.getPhoneNumber())
                    .active(true)
                    .createdAt(now)
                    .lastModified(now)
                    .role(userCreateDto.getUserRole())
                    .build();
            try {
                userRepository.save(user);
                UserProfileDto userView = new UserProfileDto();
                userView.loadFromUser(user);
                return userView;
            } catch (DataAccessException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }

        }
    }
//    public List<User> searchUser(String search) {
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<User> query = builder.createQuery(User.class);
//        Root r = query.from(User.class);
//
//        Predicate predicate = builder.conjunction();
//        params.stream().forEach(searchConsumer);
//        predicate = searchConsumer.getPredicate();
//        query.where(predicate);
//
//        List<User> result = entityManager.createQuery(query).getResultList();
//        return result;
//    }
}
