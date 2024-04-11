# sl4j
- Một phương pháp đáng tin cậy để gỡ lỗi phần mềm trong vòng đời phát triển 
của nó đòi hỏi phải chèn các câu lệnh nhật ký (log statement) vào mã. 
Log4j là một trong những thư viện ghi nhật ký như vậy dành cho Java. 
Một phương pháp đáng tin cậy để gỡ lỗi phần mềm trong vòng đời phát triển 
của nó đòi hỏi phải chèn các câu lệnh nhật ký (log statement) vào mã. 
Log4j là một trong những thư viện ghi nhật ký như vậy dành cho Java.

- Logging rất quan trọng trong phát triển phần mềm vì nó cho biết trạng thái
cua hệ thống khi chạy

Có 3 thành phần cơ bản
  - `Logger`: Chịu trách nhiệm thu thập thông tin log
  - `appenders`: Chịu trách nhiệm ghi log tới các vị trí đã được cấu hình (file, console).
  - `layout`: Chịu trách nhiệm định dạng (format) kết quả log

__Level Log__
- ALL
- TRACE
- INFO
- WARNING
- ERROR
- FATAL

## Lỗ hổng log4j
- Các hệ thống có thể dễ dàng bị xâm nhập từ bên ngoài

## Phòng tránh lỗ hổng Log4j
- Dùng phiên bản 2.15 trở lên
- Đặt tường lửa ứng dụng (Web application Firewall - WAF): bằng cách nhận biết
và chặn các chuỗi nguy hiểm trên các thiết bị upstream như WAF
- Đặt cảnh báo cho các cuộc thăm dò hoặc tấn công trên các thiết bị chạy log4j