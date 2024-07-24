-- Partition database

DROP TABLE IF EXISTS orders;

create table orders (
	order_id INT auto_increment, -- key hoa don
    order_date DATE NOT NULL, -- ngay tao hoa don
    total_amount DECIMAL(10, 2) NOT NULL, -- tien tra
    PRIMARY KEY (order_id, order_date)
	
)
PARTITION BY RANGE COLUMNS (order_date) ( -- partition by date range -> khó thay đổi và không làm tăng tốc độ truy vấn 
	PARTITION p2022 VALUES LESS THAN ('2022-01-01'),
	PARTITION p2023 VALUES LESS THAN ('2023-01-01'),
	PARTITION p2024 VALUES LESS THAN ('2024-01-01'),
	PARTITION pmax VALUES LESS THAN (MAXVALUE)
);

-- select data
EXPLAIN SELECT * FROM orders;

INSERT INTO orders VALUES
	(1, '2021-05-29', 100000.00),
    (2, '2022-05-29', 100000.00),
    (3, '2023-05-29', 100000.00),
    (4, '2024-05-29', 100000.00);
    
EXPLAIN SELECT * FROM orders WHERE order_date >= '2023-01-01' AND order_date < '2025-01-01';

CALL create_table_auto_month();

SELECT now();

SHOW EVENTS;

-- Tạo event cron
CREATE EVENT `create_table_auto_month_event`
ON SCHEDULE EVERY
	1 MONTH -- cron job mỗi tháng một lần
STARTS '2024-05-29 23:03:19'
ON COMPLETION PRESERVE ENABLE -- Không xóa bỏ count thời gian khi thực hiện xong
DO
	CALL create_table_auto_month();