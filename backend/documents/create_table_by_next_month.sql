CREATE DEFINER=`root`@`%` PROCEDURE `create_table_auto_month`()
BEGIN
	-- dùng để ghi lại tháng tiếp theo dài bao nhiêu
	DECLARE nextMonth varchar(20);
    -- câu lệnh SQL dùng để ghi lại việc tạo bảng
    DECLARE createTblSQL varchar(5210);
    -- Sau khi thực hiện câu lệnh SQL tạo bảng, lấy số lượng bảng
    DECLARE tableCount INT;
    -- Dùng để ghi lại tên bảng cần tạo
    DECLARE tableName VARCHAR(20);
    -- Tiền tố được sử dụng cho bảng ghi
    DECLARE table_prefix VARCHAR(20);
    
    -- Lấy ngày của thàng tiếp theo và gán nó cho biến nextMonth
    SELECT SUBSTR(
		replace(
			DATE_ADD(CURDATE(), INTERVAL 1 MONTH), 
		'-', ''),
	1, 6) INTO @nextMonth; -- 2024-05-28 -> 202405
    
	-- đặt giá trị biến  tiền tố bảng thành like this
    set @table_prefix = 'orders_'; -- orders_202405
    
    -- Xác định tên bảng = tiền tố bảng + tháng, tức là orders_202405
    SET @tableName = CONCAT(@table_prefix, @nextMonth);
    -- Xác định câu lệnh SQL để tạo bảng
    SET @createTblSQL = CONCAT("CREATE TABLE IF NOT EXISTS ", @tableName, "
		(
			order_id INT auto_increment, -- key hoa don
			order_date DATE NOT NULL, -- ngay tao hoa don
			total_amount DECIMAL(10, 2) NOT NULL, -- tien tra
			PRIMARY KEY (order_id, order_date)
        )
    ");
    
    -- Sử dụng prepare để tạo phần thân SQL được chuẩn bị sẵn sàng để thực thi
    PREPARE create_stmt FROM @createTableSQL;
    -- Sử dụng từ khoa EXECUTE để thực thi phần thân SQL đã chẩn bị ở trên
    EXECUTE create_stmt;
    
    -- Giải phóng phần thân
    DEALLOCATE PREPARE create_stmt;
    
    -- Sau khi tạo thì truy vấn số bảng và lưu vào biến tableCount
    SELECT COUNT(1) INTO @tableCount FROM information_schema.`TABLES`
    WHERE TABLE_NAME=@tableName;
    
    -- Kiểm tra count đã tồn tại chưa
    SELECT @tableCount 'tableCount'; 
    
END