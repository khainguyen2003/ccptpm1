# Phân vùng database
## 1. Phân vùng theo thời gian
- Mỗi năm / tháng sẽ có một vùng dữ liệu khác nhau và khi truy suất thì tùy
theo thời gian đầu vào sẽ truy cập đến vùng dữ liệu phù hợp

C1: Tạo bằng `PARTITION BY RANGE VALUES (column1, column2, ...)`
-> Tạo ra các phân vùng vật lý của bảng không làm tăng tốc độ truy vấn 
```sql
PARTITION BY RANGE COLUMNS (order_date) ( -- partition by date range -> khó thay đổi và không làm tăng tốc độ truy vấn 
	PARTITION p2022 VALUES LESS THAN ('2022-01-01'),
	PARTITION p2023 VALUES LESS THAN ('2023-01-01'),
	PARTITION p2024 VALUES LESS THAN ('2024-01-01'),
	PARTITION pmax VALUES LESS THAN (MAXVALUE)
);
```

C2: Tạo bằng cách tạo tên bảng_thời gian phân vùng
-> Có thể tăng tốc truy vấn
```jql
CREATE TABLE orders_202309;
```