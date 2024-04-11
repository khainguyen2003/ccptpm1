# Các cách bảo mật ứng dụng spring boot
[Tham khảo: https://tubean.github.io/2018/11/spring-boot-10-ways-security/](https://tubean.github.io/2018/11/spring-boot-10-ways-security/)
## 1. Sử dụng HTTPS trong môi trường production
- Cách bật chế độ HTTPs
```java
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

 @Override
 protected void configure(HttpSecurity http) throws Exception {
   http.requiresChannel().requiresSecure();
 }
}
```
Nếu bạn sử dụng Heroku, Cloud Foundry hay bất kì một nhà cung cấp dịch vụ cloud nào khác, các chứng chỉ TLS sẽ được tự động cung cấp cho người dùng
```java
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requiresChannel()
            .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
            .requiresSecure();
    }
}
```
## 2. Kiểm tra dependencies bằng snyk
- _Snyk_ là một công cụ giúp kiểm tra ứng dụng của chúng ta có sử dụng dependencies nào nguy hại không. Nó sẽ liệt kê hết các nguy cơ, lỗ hổng tồn tại trong những packages mà chúng ta sử dụng lên một dashboard.

- Ngoài ra nó còn giúp đưa ra các giải pháp ngăn chặn bằng cách tạo tạo một pull request lên source code của bạn. Nó cũng giúp kiểm tra và đảm bảo tất cả các pull request đều không gặp phải các lỗ hổng bảo mật đã biết.

- Thêm nữa là nó cũng có sẵn giao diện người dùng như CLI, vì vậy bạn có thể tích hợp nó với môi trường CI của mình, đảm bảo hệ thống sẽ an toàn ngay từ lúc build.

## 3. Ngăn chặn CRSF
- Nếu bạn sử dụng Spring MVC và dùng tag form:form hoặc Thymeleaf và sử dụng annotation @EnableWebSecurity thì CSRF token sẽ tự động được thêm vào các input field ẩn. 
- Nếu bạn sử dụng Javascript framework như Angular hay React thì bạn cần cấu hình CookieCsrfTokenRepository để JavaScript có thể đọc token trong cookie.

```java
// config for angularjs
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
```

## 4. Sử dụng Content Security Policy để ngăn chặn tấn công XSS
- Content Security Policy (CSP) là một lớp bảo mật giúp giảm thiểu tấn công XSS(cross-site cripting) và tấn công data injection
- Để kích hoạt chế độ này, bạn cần cấu hình app để trả về Content-Security-Policy header. Bạn cũng có thể dùng thẻ tag meta http-equiv="Content-Security-Policty"> trong trang HTML.
- Spring Security không kích hoạt CSP một cách mặc định. Muốn kích hoạt CSP header, bạn cấu hình ứng dụng như sau:
```java
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers()
            .contentSecurityPolicy("script-src 'self' https://trustedscripts.example.com; object-src https://trustedplugins.example.com; report-uri /csp-report-endpoint/");
    }
}
```
## 5. Lưu trữ các thông tin nhạy cảm một cách bảo mật
- Các thông tin nhạy cảm như mật khẩu, token,… cần được xử lý cẩn thận để tránh bị lộ. Bạn không thể lưu chúng dưới dạng plain text hoặc bất kì một dạng có thể đoán được nếu giữ chúng ở ổ cứng cá nhân. Chúng ta thường không đủ cẩn thận khi lưu trữ các thông tin nhạy cảm. Dĩ nhiên bạn có thể mã hoá chúng giống như mã hoá mật khẩu. Nhưng khi mật khẩu an toàn, bạn lại có thêm một cái khác cần lưu trữ, đó chính là chìa khoá để giải mã. (Thực tế thì chúng ta thường dùng các hàm hash một chiều để mã hoá mật khẩu, tức là không thể dịch ngược lại một khi đã mã hoá. Nhưng trong trường hợp này là các tài liệu nhạy cảm cần đọc được chứ không chỉ đơn giản là để so sánh).

- Spring Vault được coi là một giải pháp an toàn giúp chúng ta lưu trữ các thông tin nhạy cảm, cung cấp cách truy cập và cả tự động tạo thông tin đăng nhập cho chúng ta sử dụng.
## 6. Kiểm tra ứng dụng với OWASP ZAP
- Công cụ bảo mật OWASP ZAP là một proxy thực hiện việc kiểm tra bằng cách cố gắng xâm nhập ứng dụng tại lúc runtime. Hai phương pháp OWASP ZAP sử dụng để tìm kiếm các lỗ hổng là Spider và Active Scan.
- Công cụ Spider sẽ bắt đầu bằng một chuỗi các URL, nó sẽ truy cập và phân tích cú pháp thông qua các response, xác định các hyperlink và thêm chúng vào một danh sách. Sau đó nó sẽ cố gắng tạo ra một bản đồ URLs dành riêng cho ứng dụng của bạn.
- Công cụ Active Scan sẽ tự động kiểm tra và cung cấp cho bạn một báo cáo về những nơi ứng dụng của bạn có thể bị khai thác cũng như chi tiết về các lỗ hổng.
