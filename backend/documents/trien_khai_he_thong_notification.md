# Triển khai hệ thống notification 
## Các vấn đề cần giải quyết
1. Làm sao để hạn chế vấn đề tin nhắn thông báo bị mất dữ liệu
2. Làm sao chống tin nhắn trùng lặp
3. Làm thế nào để đảm bảo thứ tự của tin nhắn thông báo khi làm việc với message queue. Vì các tin nhắn có thể không đúng
thứ tự khi ra khỏi message queue
4. Làm sao để thiết kế cấu trúc dữ liệu hợp lý để phân tải cho các message queuee

## Kiến trúc notification
### 1. với message queue
![Kiến trúc notification](D:\learning\ki6\ccptpm\anh_tham_khao\archinotification_architecture.png "Kiến trúc notification")
- Nếu có quá nhiều người dùng cần thông báo thì có thể gây quá tải cho hệ thống message queue. Vì vậy, cần chia
nhỏ các thông báo cho nhiều message queue
![chia notification](D:\learning\ki6\ccptpm\anh_tham_khao\chia_notification_cho_cac_message_queue.png)

## Cách gửi thông báo cho người dùng
1. Hệ thống dùng cron job, cứ mỗi N phút thì cập nhật thông báo cho người dùng (Push)
-> Có thể gây quá tải cho server nếu lượng thông báo cần gửi lớn và các thông báo có thể bị trễ vì chỉ gửi thông
báo sau N phút
2. Người dùng khi đăng nhập sẽ gửi yêu cầu pull thông báo, yêu cầu này có thể thực hiện sau khi đã tải xong các
dữ liệu quan trọng (Pull)

Có thể tạo một hệ thống đánh giá người dùng để quyết định ưu tiên gửi cho ai trước. Ví dụ user1 là khách hàng
thường xuyên đăng nhập hệ thống, user2 đang follow những danh sách sản phẩm được giảm giá thì phải ưu tiên gửi
thông báo cho những khách hàng nhiều tiềm năng này. Lúc này có thể dùng push cho những khách hàng này

![](D:\learning\ki6\ccptpm\anh_tham_khao\push_va_pull.png)