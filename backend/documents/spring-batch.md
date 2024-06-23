# Làm việc với spring batch

## Mô hình hoạt động của batch
![Batch operation model](https://spring.io/img/extra/batch-config-diagram.svg)

## Batch stereotypes (Các khuôn mẫu của batch)
![Batch stereotypes](https://spring.io/img/extra/diagram-batch.svg)

## Các vấn đề cần xử lý khi làm việc với batch
1. `Conflict dữ liệu`: Nếu nhiều người dùng cùng cập nhật một dòng dữ liệu tại cùng thời điểm sẽ gây ra conflict.
 Ví dụ, có 2 người cùng ấn mua hàng với cùng một sản phẩm khi số hàng chỉ còn 1 sẽ bị conflict

=> `Giải pháp:` Sử dụng `optimistic lock` hoặc `pessimitive lock`
- `optimistic lock:` Tạo một trường trong bảng lưu dấu thời gian giữa. hệ thống không khóa tài nguyên khi đọc hoặc 
truy cập nó. Thay vào đó, nó kiểm tra xem liệu tài nguyên đã được thay đổi bởi bất kỳ ai khác trong thời gian giữa 
khi đọc và khi ghi dữ liệu. Nếu không có sự xung đột, thì ghi dữ liệu được thực hiện như dự định. Nếu có sự xung đột, 
hệ thống cần xử lý xung đột này theo cách phù hợp (thông qua việc thông báo cho người dùng hoặc thực hiện quy trình tái thử). 

=> Phù hợp với on-line app, ít sử dụng vì chậm.
Cách triển khai: Khai báo lockMode là optimistic ở các phương thức muốn lock trong repository
```java
@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
List<ProductBarcode> findByIdIn(UUID[] ids);
```

- `pessimitive lock:` Khi một ứng dụng truy xuất hàng để cập nhật, nó sẽ đặt cờ trong cột khóa. Với cờ đã có, các ứng dụng 
khác cố gắng truy xuất cùng một hàng sẽ không thành công. Khi ứng dụng đặt cờ cập nhật hàng, nó cũng xóa cờ, cho phép các 
ứng dụng khác truy xuất hàng đó. Lưu ý rằng tính toàn vẹn của dữ liệu cũng phải được duy trì giữa lần tìm nạp ban đầu và 
cài đặt cờ — ví dụ: bằng cách sử dụng khóa cơ sở dữ liệu (chẳng hạn như SELECT FOR UPDATE). Cũng lưu ý rằng phương pháp 
này có nhược điểm tương tự như khóa vật lý ngoại trừ việc quản lý việc xây dựng cơ chế hết thời gian để khóa được giải
phóng nếu người dùng đi ăn trưa trong khi bản ghi bị khóa sẽ dễ dàng hơn một chút.

=> Phù hợp với batch app.
Cách triển khai: Thêm trường version vào các entity
```java
@Version
private Long version;
```

2. Thay đổi lượng lớn dữ liệu không mong muốn

=> `Giai pháp:` Cho phép người dùng chọn `thông báo lỗi` hoặc `cập nhật` khi trùng trường dữ liệu nhất định

3. Yêu cầu nhiều tài nguyên bộ nhớ hoặc cpu nên cần đảm bảo rằng các tài nguyên này được quản lý hiệu quả để tránh quá tải hệ thống.

## Các tùy chọn xử lý với batch điển hình
- Chạy ngoại tuyến trong một của sổ
- Đồng thời xử lý hàng loạt hoặc trực tuyến: người dùng trực tuyến yêu cầu xử lý đồng thời trong một vài giây
- Xử lý song song nhiều đợt chạy hoặc công việc khác nhau cùng một lúc. 
- Phân vùng (xử lý nhiều phiên bản của cùng một công việc cùng một lúc).
- Sự kết hợp của các tùy chọn trước đó.

## Ứng dụng của batch
- Conversion Applications(Ứng dụng chuyển đổi): Chuyển đổi file dữ liệu thành dữ liệu định dạng chuẩn để xử
lý
- `Validation applications (Ứng dụng xác thực):` đảm bảo đầu vào và đầu ra đều chính xác và nhất quán. Việc xác thực thường dựa trên tiêu đề và đoạn giới thiệu tệp, tổng kiểm tra và thuật toán xác thực cũng như kiểm tra chéo cấp bản ghi.
- `Ứng dụng trích xuất (extract app):` Ứng dụng giải nén đọc một tập hợp các bản ghi từ cơ sở dữ liệu hoặc tệp đầu vào, chọn các bản ghi dựa trên các quy tắc được xác định trước và ghi các bản ghi vào tệp đầu ra.
- `Ứng dụng trích xuất/cập nhật (Processing/updating app):` Ứng dụng trích xuất/cập nhật đọc các bản ghi từ cơ sở dữ liệu hoặc tệp đầu vào và thực hiện các thay đổi đối với cơ sở dữ liệu hoặc tệp đầu ra, được điều khiển bởi dữ liệu tìm thấy trong mỗi bản ghi đầu vào.
- `Xử lý và cập nhật ứng dụng (proccessing and updating app):` Ứng dụng xử lý và cập nhật thực hiện xử lý các giao dịch đầu vào từ một ứng dụng trích xuất hoặc xác thực. Quá trình xử lý thường bao gồm việc đọc cơ sở dữ liệu để lấy dữ liệu cần thiết để xử lý, có khả năng cập nhật cơ sở dữ liệu và tạo bản ghi để xử lý đầu ra.
- `Ứng dụng định dạng đầu ra (output/format app):` Ứng dụng đầu ra/định dạng đọc tệp đầu vào, cấu trúc lại dữ liệu từ bản ghi này theo định dạng chuẩn và tạo tệp đầu ra để in hoặc truyền sang chương trình hoặc hệ thống khác.

## Spring batch với jpa
* __Lưu ý__
1. Nếu sử dụng trường primary key là indentity thì hibernate tự tắt tính năng batch
nên phải dùng UUID hoặc SEQUENCE

## Tham khảo
[Tìm hiểu sâu về spring batch](https://docs.spring.io/spring-batch/reference/spring-batch-architecture.html)
[Spring batch with jpa](https://www.baeldung.com/jpa-hibernate-batch-insert-update)
