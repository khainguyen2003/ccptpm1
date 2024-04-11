-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: test
-- ------------------------------------------------------
-- Server version	5.7.40-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tblaction`
--

DROP TABLE IF EXISTS `tblaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblaction` (
  `action_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `action_created_date` varchar(14) NOT NULL,
  `action_creator_id` int(11) unsigned NOT NULL DEFAULT '0',
  `action_target_table` int(11) unsigned NOT NULL,
  `action_target_column` int(11) unsigned NOT NULL DEFAULT '0',
  `action_method` int(1) unsigned NOT NULL,
  PRIMARY KEY (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblaction`
--

LOCK TABLES `tblaction` WRITE;
/*!40000 ALTER TABLE `tblaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblbill`
--

DROP TABLE IF EXISTS `tblbill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblbill` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID hóa đơn',
  `bill_status` smallint(1) DEFAULT '1' COMMENT '1-Hóa đơn chưa thanh toán; 2-Hóa đơn đã được thanh toán',
  `bill_created_date` varchar(15) DEFAULT NULL COMMENT 'Ngày tạo hóa đơn',
  `bill_last_modified_date` varchar(15) DEFAULT NULL COMMENT 'Ngày chỉnh sửa lần cuối',
  `bill_last_modified_id` int(11) DEFAULT '0' COMMENT 'Người chỉnh sửa lần cuối',
  `bill_creator_id` int(11) DEFAULT '0' COMMENT 'Người tạo hóa đơn',
  `bill_transporter_id` int(11) DEFAULT '0' COMMENT 'Người vận chuyển, Đơn vị vận chuyển',
  `bill_type` smallint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'Loại hóa đơn: 1= import, 2 = export, 3 = transfer',
  `bill_import_target_workplace_id` int(11) DEFAULT '0' COMMENT 'ID kho hoặc cửa hàng được nhập hàng',
  `bill_import_provider_id` int(11) DEFAULT '0' COMMENT 'ID Nhà cung cấp',
  `bill_export_customer_id` int(11) DEFAULT '0' COMMENT 'Người nhận hàng',
  `bill_export_current_workplace_id` int(11) DEFAULT '0' COMMENT 'ID Kho xuất hàng',
  `bill_export_product_discount` decimal(5,3) DEFAULT '0.000' COMMENT '% Giảm giá',
  `bill_export_target_address` varchar(100) DEFAULT NULL COMMENT 'Địa chỉ nhận hàng',
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblbill`
--

LOCK TABLES `tblbill` WRITE;
/*!40000 ALTER TABLE `tblbill` DISABLE KEYS */;
INSERT INTO `tblbill` VALUES (1,1,'1/1/2023','1/7/2023',1,1,1,1,0,0,1,1,30.000,'8 Hùng Vương, Điện Biên, Ba Đình, Hà Nội'),(2,1,'22/3/2023','2/7/2023',2,2,2,1,0,0,2,2,25.500,'458 Minh Khai, P.Vĩnh Tuy, Q.Hai Bà Trưng, Hà Nội'),(3,1,'30/4/2023','3/9/2023',3,3,3,1,0,0,3,3,15.500,'72 Nguyễn Trãi, Thanh Xuân, Hà Nội'),(4,1,'14/1/2023','4/8/2023',4,4,4,1,0,0,4,4,5.500,'12 Đường Bưởi, Thủ Lệ, Ba Đình, Hà Nội'),(5,1,'5/3/2023','5/7/2023',5,5,5,1,0,0,5,5,90.000,'47 Phạm Hùng, Mễ Trì, Nam Từ Liêm, Hà Nội'),(6,1,'26/1/2023','6/8/2023',6,6,6,1,0,0,1,1,60.000,'1 Lương Yên, Bạch Đằng, Hai Bà Trưng, Hà Nội'),(7,1,'27/2/2023','7/9/2023',7,7,7,1,0,0,2,2,0.000,'27 Cổ Linh, Long Biên, Hà Nội'),(8,1,'18/4/2023','8/9/2023',8,8,8,1,0,0,3,3,70.000,'Ngõ 264 Âu Cơ, Nhật Tân, Tây Hồ, Hà Nội'),(9,1,'9/3/2023','9/7/2023',9,9,9,1,0,0,4,4,50.000,'16 tuyến phố xung quanh Hồ Gươm, Hoàn Kiếm, Hà Nội'),(10,1,'10/5/2023','10/6/2023',10,10,10,1,0,0,5,5,70.000,'222 Trần Duy Hưng, Cầu Giấy, Hà Nội'),(11,1,'12/6/2023','11/7/2023',11,1,11,1,0,0,1,1,15.000,'11 Lê Lợi, Vinh, Nghệ An'),(12,1,'22/2/2023','12/11/2023',12,2,12,1,0,0,2,2,35.000,'102 Đào Duy Từ, Hà Tĩnh, Hà Tĩnh'),(13,1,'3/2/2023','13/8/2023',13,3,13,1,0,0,3,3,10.000,'63 Nguyễn Huệ, Huế, Thừa Thiên Huế'),(14,1,'14/3/2023','14/10/2023',14,4,14,1,0,0,4,4,20.000,'75 Lê Lai, Đà Nẵng'),(15,1,'5/4/2023','15/7/2023',15,5,15,1,0,0,5,5,45.000,'29 Trần Hưng Đạo, Quảng Ngãi, Quảng Ngãi'),(16,1,'12/5/2023','16/12/2023',16,6,16,1,0,0,1,1,30.000,'144 Quang Trung, Pleiku, Gia Lai'),(17,1,'7/6/2023','17/10/2023',17,7,17,1,0,0,2,2,25.000,'212 Lê Duẩn, Buôn Ma Thuột, Đắk Lắk'),(18,1,'18/6/2023','18/8/2023',18,8,18,1,0,0,3,3,40.000,'9 Nguyễn Văn Cừ, Nha Trang, Khánh Hòa'),(19,1,'19/2/2023','19/10/2023',19,9,19,1,0,0,4,4,50.000,'16 Ngô Quyền, Cam Ranh, Khánh Hòa'),(20,1,'22/1/2023','20/9/2023',20,10,20,1,0,0,5,5,15.000,'89 Hùng Vương, Phan Thiết, Bình Thuận'),(21,1,'21/2/2023','21/9/2023',21,1,21,1,0,0,1,1,5.000,'6 Lê Lai, Vũng Tàu, Bà Rịa - Vũng Tàu'),(22,1,'22/3/2023','22/10/2023',22,2,22,1,0,0,2,2,30.000,'37 Đống Đa, Biên Hòa, Đồng Nai'),(23,1,'23/4/2023','23/11/2023',23,3,23,1,0,0,3,3,20.000,'88 Trần Hưng Đạo, Long Xuyên, An Giang'),(24,1,'24/5/2023','24/12/2023',24,4,24,1,0,0,4,4,10.000,'2 Nguyễn Huệ, Cần Thơ'),(25,1,'25/6/2023','25/1/2024',25,5,25,1,0,0,5,5,35.000,'4 Hàm Nghi, Sóc Trăng'),(26,1,'26/7/2023','26/2/2024',26,6,26,0,1,1,0,0,0.000,NULL),(27,1,'27/8/2023','27/3/2024',27,7,27,0,2,2,0,0,0.000,NULL),(28,1,'28/9/2023','28/4/2024',28,8,28,0,3,3,0,0,0.000,NULL),(29,1,'29/10/2023','29/5/2024',29,9,29,0,4,4,0,0,0.000,NULL),(30,1,'30/11/2023','30/6/2024',30,10,30,0,5,5,0,0,0.000,NULL),(31,1,'31/12/2023','31/7/2024',31,1,31,0,1,6,0,0,0.000,NULL),(32,1,'1/1/2024','1/8/2024',32,2,32,0,2,1,0,0,0.000,NULL),(33,1,'2/2/2024','2/9/2024',33,3,33,0,3,2,0,0,0.000,NULL),(34,1,'3/3/2024','3/10/2024',34,4,34,0,4,3,0,0,0.000,NULL),(35,1,'4/4/2024','4/11/2024',35,5,35,0,5,4,0,0,0.000,NULL),(36,1,'5/5/2024','5/12/2024',36,6,36,0,1,5,0,0,0.000,NULL),(37,1,'6/6/2024','6/1/2025',37,7,37,0,2,6,0,0,0.000,NULL),(38,1,'7/7/2024','7/2/2025',38,8,38,0,3,1,0,0,0.000,NULL),(39,1,'8/8/2024','8/3/2025',39,9,39,0,4,2,0,0,0.000,NULL),(40,1,'9/9/2024','9/4/2025',40,10,40,0,5,3,0,0,0.000,NULL),(41,1,'10/10/2024','10/5/2025',41,1,41,0,1,4,0,0,0.000,NULL),(42,1,'11/11/2024','11/6/2025',42,2,42,0,2,5,0,0,0.000,NULL),(43,1,'12/12/2024','12/7/2025',43,3,43,0,3,6,0,0,0.000,NULL),(44,1,'13/1/2025','13/8/2025',44,4,44,0,4,1,0,0,0.000,NULL),(45,1,'14/2/2025','14/9/2025',45,5,45,0,5,2,0,0,0.000,NULL),(46,1,'15/3/2025','15/10/2025',46,6,46,0,1,3,0,0,0.000,NULL),(47,1,'16/4/2025','16/11/2025',47,7,47,0,2,4,0,0,0.000,NULL),(48,1,'17/5/2025','17/12/2025',48,8,48,0,3,5,0,0,0.000,NULL),(49,1,'18/6/2025','18/1/2026',49,9,49,0,4,6,0,0,0.000,NULL),(50,1,'19/7/2025','19/2/2026',50,10,50,0,5,1,0,0,0.000,NULL);
/*!40000 ALTER TABLE `tblbill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblbilldetail`
--

DROP TABLE IF EXISTS `tblbilldetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblbilldetail` (
  `bill_detail_id` int(11) NOT NULL DEFAULT '0' COMMENT 'ID hóa đơn',
  `bill_detail_product_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'ID Sản phẩm',
  `bill_detail_product_price` int(11) NOT NULL DEFAULT '0' COMMENT 'Giá ',
  `bill_detail_product_quantity` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Số lượng'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Hóa đơn nhập kho(cửa hàng)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblbilldetail`
--

LOCK TABLES `tblbilldetail` WRITE;
/*!40000 ALTER TABLE `tblbilldetail` DISABLE KEYS */;
INSERT INTO `tblbilldetail` VALUES (1,1,27090000,5),(1,2,17990000,3),(1,3,17590000,2),(1,4,10990000,1),(1,5,8990000,4),(2,1,27090000,3),(2,3,17590000,2),(2,5,8990000,4),(2,7,10690000,1),(2,9,13490000,3),(3,2,17990000,4),(3,4,10990000,2),(3,6,19590000,1),(3,8,16990000,3),(3,10,13490000,2),(4,1,27090000,2),(4,3,17590000,3),(4,5,8990000,1),(4,7,10690000,4),(4,9,13490000,2),(5,2,17990000,3),(5,4,10990000,4),(5,6,19590000,1),(5,8,16990000,2),(5,10,13490000,3),(6,1,27090000,3),(6,3,17590000,2),(6,5,8990000,4),(6,7,10690000,1),(6,9,13490000,3),(7,2,17990000,4),(7,4,10990000,2),(7,6,19590000,1),(7,8,16990000,3),(7,10,13490000,2),(8,1,27090000,2),(8,3,17590000,3),(8,5,8990000,1),(8,7,10690000,4),(8,9,13490000,2),(9,2,17990000,3),(9,4,10990000,4),(9,6,19590000,1),(9,8,16990000,2),(9,10,13490000,3),(10,1,27090000,5),(10,2,17990000,3),(10,3,17590000,2),(10,4,10990000,1),(10,5,8990000,4),(11,1,27090000,3),(11,2,17990000,2),(11,3,17590000,1),(11,4,10990000,4),(11,5,8990000,3),(12,6,19590000,2),(12,8,16990000,3),(12,10,13490000,1),(12,12,9690000,4),(12,14,14990000,2),(13,16,9790000,3),(13,18,13990000,4),(13,20,11990000,1),(13,22,9190000,3),(13,24,15990000,2),(14,17,16290000,4),(14,19,13990000,2),(14,21,10990000,1),(14,23,15990000,3),(14,25,9190000,2),(15,1,27090000,3),(15,3,17590000,2),(15,5,8990000,1),(15,7,10690000,4),(15,9,13490000,2),(16,2,17990000,4),(16,4,10990000,2),(16,6,19590000,1),(16,8,16990000,3),(16,10,13490000,2),(17,1,27090000,2),(17,3,17590000,3),(17,5,8990000,1),(17,7,10690000,4),(17,9,13490000,2),(18,2,17990000,3),(18,4,10990000,4),(18,6,19590000,1),(18,8,16990000,2),(18,10,13490000,3),(19,11,15000000,3),(19,13,12000000,2),(19,15,9000000,1),(19,17,11000000,4),(19,19,13500000,2),(20,12,16000000,4),(20,14,13000000,3),(20,16,10000000,2),(20,18,8000000,1),(20,20,14000000,4),(21,21,27090000,3),(21,22,17990000,2),(21,23,17590000,1),(21,24,10990000,4),(21,25,8990000,3),(22,6,19590000,2),(22,8,16990000,3),(22,10,13490000,1),(22,12,9690000,4),(22,14,14990000,2),(23,16,9790000,3),(23,18,13990000,4),(23,20,11990000,1),(23,22,9190000,3),(23,24,15990000,2),(24,17,16290000,4),(24,19,13990000,2),(24,21,10990000,1),(24,23,15990000,3),(24,25,9190000,2),(25,1,27090000,3),(25,3,17590000,2),(25,5,8990000,1),(25,7,10690000,4),(25,9,13490000,2),(26,2,17990000,4),(26,4,10990000,2),(26,6,19590000,1),(26,8,16990000,3),(26,10,13490000,2),(27,1,27090000,2),(27,3,17590000,3),(27,5,8990000,1),(27,7,10690000,4),(27,9,13490000,2),(28,2,17990000,3),(28,4,10990000,4),(28,6,19590000,1),(28,8,16990000,2),(28,10,13490000,3),(29,11,15000000,3),(29,13,12000000,2),(29,15,9000000,1),(29,17,11000000,4),(29,19,13500000,2),(30,12,16000000,4),(30,14,13000000,3),(30,16,10000000,2),(30,18,8000000,1),(30,20,14000000,4),(31,21,27090000,3),(31,22,17990000,2),(31,23,17590000,1),(31,24,10990000,4),(31,25,8990000,3),(32,6,19590000,2),(32,8,16990000,3),(32,10,13490000,1),(32,12,9690000,4),(32,14,14990000,2),(33,16,9790000,3),(33,18,13990000,4),(33,20,11990000,1),(33,22,9190000,3),(33,24,15990000,2),(34,17,16290000,4),(34,19,13990000,2),(34,21,10990000,1),(34,23,15990000,3),(34,25,9190000,2),(35,1,27090000,3),(35,3,17590000,2),(35,5,8990000,1),(35,7,10690000,4),(35,9,13490000,2),(36,2,17990000,4),(36,4,10990000,2),(36,6,19590000,1),(36,8,16990000,3),(36,10,13490000,2),(37,1,27090000,2),(37,3,17590000,3),(37,5,8990000,1),(37,7,10690000,4),(37,9,13490000,2),(38,2,17990000,3),(38,4,10990000,4),(38,6,19590000,1),(38,8,16990000,2),(38,10,13490000,3),(39,11,15000000,3),(39,13,12000000,2),(39,15,9000000,1),(39,17,11000000,4),(39,19,13500000,2),(40,12,16000000,4),(40,14,13000000,3),(40,16,10000000,2),(40,18,8000000,1),(40,20,14000000,4),(41,21,27090000,3),(41,22,17990000,2),(41,23,17590000,1),(41,24,10990000,4),(41,25,8990000,3),(42,6,19590000,2),(42,8,16990000,3),(42,10,13490000,1),(42,12,9690000,4),(42,14,14990000,2),(43,16,9790000,3),(43,18,13990000,4),(43,20,11990000,1),(43,22,9190000,3),(43,24,15990000,2),(44,17,16290000,4),(44,19,13990000,2),(44,21,10990000,1),(44,23,15990000,3),(44,25,9190000,2),(45,1,27090000,3),(45,3,17590000,2),(45,5,8990000,1),(45,7,10690000,4),(45,9,13490000,2),(46,2,17990000,4),(46,4,10990000,2),(46,6,19590000,1),(46,8,16990000,3),(46,10,13490000,2),(47,1,27090000,2),(47,3,17590000,3),(47,5,8990000,1),(47,7,10690000,4),(47,9,13490000,2),(48,2,17990000,3),(48,4,10990000,4),(48,6,19590000,1),(48,8,16990000,2),(48,10,13490000,3),(49,11,15000000,3),(49,13,12000000,2),(49,15,9000000,1),(49,17,11000000,4),(49,19,13500000,2),(50,12,16000000,4),(50,14,13000000,3),(50,16,10000000,2),(50,18,8000000,1),(50,20,14000000,4);
/*!40000 ALTER TABLE `tblbilldetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcategory`
--

DROP TABLE IF EXISTS `tblcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblcategory` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `category_notes` text,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcategory`
--

LOCK TABLES `tblcategory` WRITE;
/*!40000 ALTER TABLE `tblcategory` DISABLE KEYS */;
INSERT INTO `tblcategory` VALUES (1,'Laptop - Tablet - Mobile','Danh m?c Laptop - Tablet - Mobile'),(2,'Máy tính - Máy chủ','Danh m?c Máy tính - Máy ch?'),(3,'Máy tính All-in-one','Danh m?c Máy tính All-in-one'),(4,'PCAP Máy Tính An Phát','Danh m?c PCAP Máy Tính An Phát'),(5,'Apple, đèn led','Danh m?c Apple'),(6,'Máy in - TB Văn Phòng','Danh m?c Máy in - TB V?n Phòng'),(7,'Linh Kiện Máy Tính','Danh m?c Linh Ki?n Máy Tính'),(8,'Màn Hình Máy Tính','Danh m?c Màn Hình Máy Tính'),(9,'Gaming Gear','Danh m?c Gaming Gear'),(10,'Thiết bị lưu trữ, USB, thẻ nhớ','Danh m?c Thi?t b? l?u tr?, USB, th? nh?'),(11,'Loa, Tai Nghe, Webcam, Tivi','Danh m?c Loa, Tai Nghe, Webcam, Tivi'),(12,'Camera','Danh m?c Camera'),(13,'Cooling, Tản nhiệt','Danh m?c Cooling, T?n nhi?t'),(14,'Thiết Bị Mạng','Danh m?c Thi?t B? M?ng'),(15,'Phụ Kiện Laptop, PC, Khác','Danh m?c Ph? Ki?n Laptop, PC, Khác'),(16,'Xe Đạp Điện, Smart Home','Danh m?c Xe ??p ?i?n, Smart Home'),(17,'Buồng Chơi Game Giả Lập','Danh m?c Bu?ng Ch?i Game Gi? L?p');
/*!40000 ALTER TABLE `tblcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblemployee`
--

DROP TABLE IF EXISTS `tblemployee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblemployee` (
  `employee_id` int(11) NOT NULL,
  `employee_contract_expired_date` varchar(15) DEFAULT NULL COMMENT 'Ngày kết thúc hợp đồng',
  `employee_status` smallint(2) unsigned NOT NULL DEFAULT '0' COMMENT '1-Đang làm; 0-Đã nghỉ \r\n',
  `employee_work_time_length` varchar(15) DEFAULT NULL COMMENT 'Thời gian làm việc',
  `employee_workplace_start_time` varchar(15) DEFAULT NULL COMMENT 'Thời gian bắt đầu làm việc',
  `employee_workplace_id` int(11) DEFAULT NULL COMMENT '0- Kho hàng; 1 Cửa hàng',
  `employee_work_finished_day` varchar(15) NOT NULL DEFAULT '0' COMMENT 'Số ngày làm việc',
  `employee_role` smallint(2) unsigned NOT NULL DEFAULT '0' COMMENT '0-Nhân viên; 1-Quản lý kho hàng hoặc Cửa hàng; 2-Admin',
  `employee_salary` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Thông tin nhân viên';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblemployee`
--

LOCK TABLES `tblemployee` WRITE;
/*!40000 ALTER TABLE `tblemployee` DISABLE KEYS */;
INSERT INTO `tblemployee` VALUES (1,'12/6/2023',1,'8 gi?','8 gi? sáng',1,'11/8/2023',1,100000),(2,'22/2/2023',1,'8 gi?','8 gi? sáng',2,'12/9/2023',1,200000),(3,'3/2/2023',1,'5 gi?','8 gi? sáng',3,'13/10/2023',1,300000),(4,'14/3/2023',1,'9 gi?','8 gi? sáng',4,'14/12/2023',1,400000);
/*!40000 ALTER TABLE `tblemployee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblexportbill`
--

DROP TABLE IF EXISTS `tblexportbill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblexportbill` (
  `export_bill_id` int(11) NOT NULL COMMENT 'Kế thừa từ bill để tạo ra cặp khóa (export_bill_id, product_id)',
  `export_bill_customer_id` int(11) NOT NULL COMMENT 'Người nhận hàng',
  `export_bill_price` int(11) NOT NULL DEFAULT '0' COMMENT 'Giá xuất (giá bán)',
  `export_bill_target_address` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Địa chỉ nhận hàng',
  `export_bill_current_workplace_id` int(11) NOT NULL COMMENT 'ID Kho xuất hàng',
  `export_bill_product_id` int(11) unsigned DEFAULT NULL,
  `export_bill_product_quantity` int(10) unsigned NOT NULL DEFAULT '0',
  `export_bill_product_discount` decimal(5,3) NOT NULL DEFAULT '0.000' COMMENT '% Giảm giá',
  PRIMARY KEY (`export_bill_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Phiếu xuất kho (hay phiếu bán hàng)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblexportbill`
--

LOCK TABLES `tblexportbill` WRITE;
/*!40000 ALTER TABLE `tblexportbill` DISABLE KEYS */;
INSERT INTO `tblexportbill` VALUES (1,1,10000,'8 Hùng Vương, Điện Biên, Ba Đình, Hà Nội',0,NULL,0,0.000),(2,2,20000,'458 Minh Khai, P.Vĩnh Tuy, Q.Hai Bà Trưng, Hà Nội',0,NULL,0,0.000),(3,3,30000,'72 Nguyễn Trãi, Thanh Xuân, Hà Nội',0,NULL,0,0.000),(4,4,40000,'12 Đường Bưởi, Thủ Lệ, Ba Đình, Hà Nội',0,NULL,0,0.000),(5,5,50000,'47 Phạm Hùng, Mễ Trì, Nam Từ Liêm, Hà Nội',0,NULL,0,0.000),(6,6,60000,'1 Lương Yên, Bạch Đằng, Hai Bà Trưng, Hà Nội',0,NULL,0,0.000),(7,7,70000,'27 Cổ Linh, Long Biên, Hà Nội',0,NULL,0,0.000),(8,8,80000,'Ngõ 264 Âu Cơ, Nhật Tân, Tây Hồ, Hà Nội',0,NULL,0,0.000),(9,9,90000,'16 tuyến phố xung quanh Hồ Gươm, Hoàn Kiếm, Hà Nội',0,NULL,0,0.000),(10,10,100000,'222 Trần Duy Hưng, Cầu Giấy, Hà Nội',0,NULL,0,0.000);
/*!40000 ALTER TABLE `tblexportbill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblguarantee`
--

DROP TABLE IF EXISTS `tblguarantee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblguarantee` (
  `guarantee_id` int(11) DEFAULT NULL,
  `guarantee_started_date` date DEFAULT NULL,
  `guarantee_expired_date` date DEFAULT NULL,
  `guarantee_deleted` smallint(6) DEFAULT NULL,
  `guarantee_price` int(11) DEFAULT NULL,
  `guarantee_method` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblguarantee`
--

LOCK TABLES `tblguarantee` WRITE;
/*!40000 ALTER TABLE `tblguarantee` DISABLE KEYS */;
INSERT INTO `tblguarantee` VALUES (8,'0000-00-00','0000-00-00',0,10000,0),(9,'0000-00-00','0000-00-00',0,900000,0),(10,'0000-00-00','0000-00-00',0,40000,0),(11,'0000-00-00','0000-00-00',0,45000,0),(12,'0000-00-00','0000-00-00',0,620000,0),(13,'0000-00-00','0000-00-00',1,780000,0),(14,'0000-00-00','0000-00-00',1,93000,0),(15,'0000-00-00','0000-00-00',1,365000,0),(16,'0000-00-00','0000-00-00',1,21000,0),(17,'0000-00-00','0000-00-00',1,104000,0);
/*!40000 ALTER TABLE `tblguarantee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblimportbill`
--

DROP TABLE IF EXISTS `tblimportbill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblimportbill` (
  `import_bill_id` int(11) NOT NULL COMMENT 'Kế thừa từ bill để tạo ra cặp khóa (import_bill_id, product_id)',
  `import_bill_target_workplace_id` int(11) DEFAULT NULL COMMENT 'ID kho hoặc cửa hàng được nhập hàng',
  `import_bill_price` int(11) NOT NULL DEFAULT '0' COMMENT 'Giá nhập (giá mua)',
  `import_bill_provider_id` int(11) DEFAULT NULL COMMENT 'ID Nhà cung cấp',
  `import_bill_product_id` int(11) unsigned NOT NULL,
  `import_bill_product_quantity` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`import_bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Hóa đơn nhập kho(cửa hàng)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblimportbill`
--

LOCK TABLES `tblimportbill` WRITE;
/*!40000 ALTER TABLE `tblimportbill` DISABLE KEYS */;
INSERT INTO `tblimportbill` VALUES (1,1,5000000,0,1,5),(2,2,600000,0,1,4),(3,3,3000000,0,1,9),(4,5,800000,0,1,1),(5,6,60000,0,3,10),(6,5,20000,0,1,1),(7,6,520000,0,2,3);
/*!40000 ALTER TABLE `tblimportbill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbllog`
--

DROP TABLE IF EXISTS `tbllog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbllog` (
  `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `log_user_id` varchar(45) NOT NULL,
  `log_user_permission` smallint(1) unsigned NOT NULL,
  `log_username` varchar(45) NOT NULL,
  `log_action` smallint(1) unsigned NOT NULL,
  `log_position` smallint(50) unsigned NOT NULL,
  `log_name` text,
  `log_notes` text,
  `log_created_date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbllog`
--

LOCK TABLES `tbllog` WRITE;
/*!40000 ALTER TABLE `tbllog` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbllog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblpc`
--

DROP TABLE IF EXISTS `tblpc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblpc` (
  `pc_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pc_name` varchar(200) NOT NULL,
  `pc_pg_id` int(10) unsigned NOT NULL,
  `pc_ps_id` smallint(3) unsigned NOT NULL,
  `pc_manager_id` int(10) unsigned DEFAULT NULL,
  `pc_notes` text,
  `pc_delete` tinyint(1) DEFAULT NULL,
  `pc_deleted_date` varchar(45) DEFAULT NULL,
  `pc_deleted_author` varchar(50) DEFAULT NULL,
  `pc_modified_date` varchar(45) DEFAULT NULL,
  `pc_created_date` varchar(45) DEFAULT NULL,
  `pc_image` varchar(100) DEFAULT NULL COMMENT 'Anh minh hoa cho the loai',
  `pc_enable` tinyint(1) unsigned DEFAULT NULL COMMENT 'Hien thi hay khong',
  `pc_name_en` varchar(200) DEFAULT NULL COMMENT 'Ten The loai san pham tieng Anh',
  `pc_created_author_id` int(10) unsigned DEFAULT '0',
  `pc_language` smallint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`pc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblpc`
--

LOCK TABLES `tblpc` WRITE;
/*!40000 ALTER TABLE `tblpc` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblpc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblpg`
--

DROP TABLE IF EXISTS `tblpg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblpg` (
  `pg_id` smallint(3) unsigned NOT NULL AUTO_INCREMENT,
  `pg_name` varchar(200) NOT NULL,
  `pg_ps_id` smallint(3) unsigned NOT NULL,
  `pg_manager_id` int(10) unsigned DEFAULT NULL,
  `pg_notes` text,
  `pg_delete` tinyint(1) DEFAULT NULL,
  `pg_deleted_date` varchar(45) DEFAULT NULL,
  `pg_deleted_author` varchar(50) DEFAULT NULL,
  `pg_modified_date` varchar(45) DEFAULT NULL,
  `pg_created_date` varchar(45) DEFAULT NULL,
  `pg_enable` tinyint(1) DEFAULT NULL COMMENT 'Hien thi hay khong',
  `pg_name_en` varchar(200) DEFAULT NULL COMMENT 'Ten Nhom san pham tieng Anh',
  `pg_created_author_id` int(10) unsigned DEFAULT '0',
  `pg_language` smallint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`pg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblpg`
--

LOCK TABLES `tblpg` WRITE;
/*!40000 ALTER TABLE `tblpg` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblpg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblproduct`
--

DROP TABLE IF EXISTS `tblproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblproduct` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `product_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 - chưa  đư?c bán\n1 - đang bán\n2 - ng?ng kinh doanh',
  `product_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 - không b? đưa vào thùng rác\n1 - b? đưa vào thùng rác',
  `product_import_price` int(11) NOT NULL DEFAULT '0',
  `product_images` text,
  `product_guarantee_id` int(11) DEFAULT NULL COMMENT 'M? b?o hành',
  `product_notes` text,
  `product_last_modified` varchar(45) DEFAULT NULL,
  `product_pc_id` smallint(5) unsigned NOT NULL COMMENT 'Loai san pham',
  `product_pg_id` smallint(3) unsigned NOT NULL COMMENT 'Nhom san pham',
  `product_ps_id` smallint(3) unsigned NOT NULL COMMENT 'He san pham',
  `product_sell_price` int(11) unsigned DEFAULT '0',
  `product_min_inven` int(11) unsigned DEFAULT '0',
  `product_max_inven` int(11) unsigned DEFAULT '0',
  `product_desc` text,
  `product_stoped_cell` smallint(1) unsigned DEFAULT '0',
  `product_user_modified_id` int(11) DEFAULT NULL,
  `product_bar_code` varchar(45) DEFAULT NULL COMMENT 'Mã vạch',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblproduct`
--

LOCK TABLES `tblproduct` WRITE;
/*!40000 ALTER TABLE `tblproduct` DISABLE KEYS */;
INSERT INTO `tblproduct` VALUES (6,'MacBook Air 13 inch M1 2020 7-core GPU',1,1,19590000,' /home/images/product/macbook-air-m1-2020-gray-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(7,'HP 15s fq5229TU i3 1215U (8U237PA)',0,0,10690000,' /home/images/product/hp-15s-fq5229tu-i3-8u237pa-thumb-600x600.png',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(8,'Asus TUF Gaming F15 FX506HF i5 11400H (HN014W)',0,1,16990000,'/home/images/product/asus-tuf-gaming-f15-fx506hf-i5-hn014w-thumb-600x600.jpg;;/home/images/product/download1703486295038.jpg;',0,' null',NULL,0,0,0,0,NULL,NULL,'null',0,NULL,'null'),(9,'Acer Aspire 5 Gaming A515 58GM 51LB i5 13420H (NX.KQ4SV.002)',0,1,16990000,'/home/images/product/acer-aspire-5-a515-58gm-51lb-i5-nxkq4sv002-170923-015941-600x600.jpg;;/home/images/product/download1703487446771.jpg;',0,' null',NULL,0,0,0,0,NULL,NULL,'null',1,NULL,'null'),(10,'Asus Vivobook Go 15 E1504FA R5 7520U (NJ776W)',1,1,13490000,' /home/images/product/asus-vivobook-go-15-e1504fa-r5-nj776w-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(12,'HP 240 G9 i3 1215U (6L1X7PA)',1,1,9690000,' /home/images/product/hp-240-g9-i3-6l1x7pa-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(13,'Asus Vivobook 16 X1605VA i5 1335U (MB360W)',0,0,15990000,' /home/images/product/asus-vivobook-16-x1605va-i5-mb360w-thumb-laptop-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(14,'HP 15s fq5162TU i5 1235U (7C134PA)',1,1,14990000,' /home/images/product/hp-15s-fq5162tu-i5-7c134pa-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(15,'Asus Vivobook 15 X1504ZA i3 1215U (NJ102W)',0,0,10990000,' /home/images/product/asus-vivobook-15-x1504za-i3-nj102w-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(16,'Asus Vivobook X515EA i3 1115G4 (EJ3948W)',1,1,9790000,' /home/images/product/asus-vivobook-x515ea-i3-ej3948w-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(17,'HP Pavilion 14 dv2074TU i5 1235U (7C0P3PA)',0,0,16290000,' /home/images/product/hp-pavilion-14-dv2074tu-i5-7c0p3pa-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(18,'Asus Vivobook X515EA i5 1135G7 (EJ4155W)',1,1,13990000,' /home/images/product/asus-vivobook-x515ea-i5-ej4155w-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(19,'Dell Vostro 15 3520 i3 1215U (5M2TT1)',0,0,11990000,' /home/images/product/dell-vostro-15-3520-i3-5m2tt1-090823-041032-600x600.png',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(20,'HP 14 ep0126TU i3 N305 (8U233PA)',1,1,9190000,' /home/images/product/hp-14-ep0126tu-i3-8u233pa-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(21,'Lenovo Ideapad 3 15ITL6 i3 1115G4 (82H803SGVN)',0,0,7990000,' /home/images/product/lenovo-ideapad-3-15itl6-i3-82h803sgvn-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(22,'Dell Inspiron 15 3520 i3 1215U (71003264)',1,1,11990000,' /home/images/product/dell-inspiron-3520-i3-71003264-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(23,'HP 15s fq2716TU i3 1115G4 (7C0X3PA)',0,0,10590000,' /home/images/product/hp-15s-fq2716tu-i3-7c0x3pa-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(24,'Asus Vivobook X415EA i3 1115G4 (EK2034W)',1,1,9190000,' /home/images/product/asus-vivobook-x415ea-i3-ek2034w-thumb-laptop-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(26,'Dell Inspiron 15 3520 i5 1235U (N5I5122W1)',1,1,14990000,' /home/images/product/dell-inspiron-15-3520-i5-n5i5122w1-191222-091429-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(27,'Asus Vivobook 15 X1504VA i5 1335U (NJ025W)',0,0,14990000,' /home/images/product/asus-vivobook-15-x1504va-i5-nj025w-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(29,'Lenovo Ideapad 3 15IAU7 i3 1215U (82RK005LVN)',0,0,8990000,' /home/images/product/lenovo-ideapad-3-15iau7-i3-82rk005lvn-281122-051953-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(30,'HP Pavilion 15 eg2082TU i5 1240P (7C0Q5PA)',1,1,16390000,' /home/images/product/hp-pavilion-15-eg2082tu-i5-7c0q5pa-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(31,'Lenovo Ideapad 3 15ITL6 i5 1155G7 (82H803RRVN)',0,0,11690000,' /home/images/product/lenovo-ideapad-3-15itl6-i5-82h803rrvn-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(32,'HP 240 G8 i3 1115G4 (6L1A1PA)',1,1,9090000,' /home/images/product/hp-240-g8-i3-6l1a1pa-210423-031503-600x600.jpg',0,'null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(33,'Asus TUF Gaming F15 FX506HE i7 11800H (HN378W)',0,0,19990000,' /home/images/product/asus-tuf-gaming-f15-fx506he-i7-hn378w-thumb-600x600.jpg',0,'null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(34,'HP 15s fq5147TU i7 1255U (7C133PA)',1,1,16490000,' /home/images/product/hp-15s-fq5147tu-i7-7c133pa-thumb-600x600.jpg',0,'null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(35,'Acer Aspire 3 A315 abc',0,0,8690000,'/home/images/product/acer-aspire-3-a315-59-314f-i3-nxk6tsv002-thumb-1-600x600.jpg;/home/images/product/download1703430951801.jpg;;',0,'nguy&#7877;n',NULL,0,0,0,8690000,NULL,NULL,'nguy&#7877;n v&#259;n kh&#7843;i abc bcd\r\n',1,NULL,'abcdb'),(36,'MSI Gaming GF63 Thin 11SC i5 11400H (664VN)',1,1,14990000,' /home/images/product/msi-gaming-gf63-thin-11sc-i5-664vn-glr-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(38,'MSI Gaming GF63 Thin 11UC i7 11800H (1228VN)',1,1,18890000,' /home/images/product/msi-gaming-gf63-thin-11uc-i7-1228vn-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(39,'Asus Vivobook 15 OLED A1505VA i5 13500H (L1341W)',0,0,17990000,' /home/images/product/asus-vivobook-15-oled-a1505va-i5-l1341w-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(40,'Lenovo Ideapad Slim 3 15IAH8 i5 12450H (83ER000EVN)',1,1,13990000,' /home/images/product/lenovo-ideapad-slim-3-15iah8-i5-83er00evn-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(41,'MacBook Air 13 inch M2 2022 8-core GPU',0,0,27090000,' /home/images/product/apple-macbook-air-m2-2022-vang-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(42,'Acer Nitro 5 Gaming AN515 57 5669 i5 11400H (NH.QEHSV.001)',1,1,17990000,' /home/images/product/acer-nitro-5-gaming-an515-57-5669-i5-11400h-8gb-512gb-144hz-4gb-gtx1650-win11-nhqehsv001-031221-100506-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(43,'HP Pavilion 15 eg2081TU i5 1240P (7C0Q4PA)',0,0,17590000,' /home/images/product/hp-pavilion-15-eg2081tu-i5-7c0q4pa-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(44,'Dell Vostro 3520 i3 1215U (V5I3614W1)',1,1,10990000,' /home/images/product/dell-vostro-3520-i3-v5i3614w1-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL),(45,'Lenovo Ideapad 3 15ITL6 i3 1115G4 (82H803SFVN)',0,0,8990000,' /home/images/product/lenovo-ideapad-3-15itl6-i3-82h803sfvn-thumb-600x600.jpg',0,' null',' 30/10/2023',0,0,0,NULL,NULL,NULL,NULL,0,NULL,NULL);
/*!40000 ALTER TABLE `tblproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblproviders`
--

DROP TABLE IF EXISTS `tblproviders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblproviders` (
  `provider_id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `provider_notes` text,
  `provider_address` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`provider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COMMENT='Nhà cung cấp';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblproviders`
--

LOCK TABLES `tblproviders` WRITE;
/*!40000 ALTER TABLE `tblproviders` DISABLE KEYS */;
INSERT INTO `tblproviders` VALUES (1,'Apple','Apple là m?t hãng công ngh? ?a d?ng n?i ti?ng v?i s?n ph?m máy tính và thi?t b? ?i?n t? sang tr?ng và hi?u su?t cao.','1 Infinite Loop, Cupertino, California 95014,'),(2,'Microsoft','Microsoft là m?t trong nh?ng tên tu?i hàng ??u trong l?nh v?c công ngh?, chuyên s?n xu?t các s?n ph?m và d?ch v? liên quan ??n máy tính và ph?n m?m.','1 Microsoft Way, Redmond, Washington 98052, H'),(3,'HP','HP (Hewlett-Packard) là m?t trong nh?ng th??ng hi?u n?i ti?ng trong ngành công nghi?p máy tính, chuyên s?n xu?t các s?n ph?m máy tính và máy in.','1501 Page Mill Road, Palo Alto, California 94'),(4,'Dell','Dell là m?t hãng máy tính toàn c?u n?i ti?ng v?i s? chuyên nghi?p trong vi?c s?n xu?t máy tính cá nhân, máy tính xách tay và các d?ch v? công ngh? liên quan.','1 Dell Way, Round Rock, Texas 78682, Hoa Kỳ.'),(5,'MSI','MSI (Micro-Star International) là m?t công ty chuyên s?n xu?t các s?n ph?m công ngh? ch?i game, bao g?m máy tính và linh ki?n ch?i game cao c?p.','69, Lide St., Zhonghe Dist., New Taipei City ');
/*!40000 ALTER TABLE `tblproviders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblps`
--

DROP TABLE IF EXISTS `tblps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblps` (
  `ps_id` smallint(3) unsigned NOT NULL AUTO_INCREMENT,
  `ps_name` varchar(200) NOT NULL,
  `ps_manager_id` int(10) unsigned DEFAULT NULL,
  `ps_notes` text,
  `ps_delete` tinyint(1) DEFAULT NULL,
  `ps_created_date` varchar(45) DEFAULT NULL,
  `ps_deleted_date` varchar(45) DEFAULT NULL,
  `ps_modified_date` varchar(45) DEFAULT NULL,
  `ps_deleted_author` varchar(50) DEFAULT NULL,
  `ps_table` varchar(45) DEFAULT NULL COMMENT 'Chi tiet cac san pham trong he duoc luu vao bang nay',
  `ps_enable` tinyint(1) DEFAULT NULL COMMENT 'Hien thi hay khong',
  `ps_name_en` varchar(200) DEFAULT NULL COMMENT 'Ten He san pham tieng Anh',
  `ps_created_author_id` int(11) unsigned DEFAULT '0',
  `ps_language` smallint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`ps_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblps`
--

LOCK TABLES `tblps` WRITE;
/*!40000 ALTER TABLE `tblps` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbltranferstoragebill`
--

DROP TABLE IF EXISTS `tbltranferstoragebill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbltranferstoragebill` (
  `export_bill_id` int(11) NOT NULL,
  `export_bill_current_workplace_id` int(11) DEFAULT NULL COMMENT 'ID kho xuất hàng',
  `export_bill_target_workplace_id` int(11) DEFAULT NULL COMMENT 'ID kho nhập hàng',
  `export_bill_price` int(11) DEFAULT NULL,
  PRIMARY KEY (`export_bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Hóa đơn chuyển kho';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbltranferstoragebill`
--

LOCK TABLES `tbltranferstoragebill` WRITE;
/*!40000 ALTER TABLE `tbltranferstoragebill` DISABLE KEYS */;
INSERT INTO `tbltranferstoragebill` VALUES (1,1,1,100000),(2,2,2,200000),(3,3,3,300000),(4,4,4,400000),(5,5,5,500000);
/*!40000 ALTER TABLE `tbltranferstoragebill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbltransferbill`
--

DROP TABLE IF EXISTS `tbltransferbill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbltransferbill` (
  `transfer_bill_id` int(11) NOT NULL,
  `transfer_bill_current_workplace_id` int(11) DEFAULT NULL COMMENT 'ID kho xuất hàng',
  `transfer_bill_target_workplace_id` int(11) DEFAULT NULL COMMENT 'ID kho nhập hàng',
  `transfer_bill_product_id` int(11) unsigned NOT NULL,
  `transfer_bill_product_quantity` int(11) unsigned NOT NULL DEFAULT '0',
  `transfer_bill_price` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`transfer_bill_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Hóa đơn chuyển kho';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbltransferbill`
--

LOCK TABLES `tbltransferbill` WRITE;
/*!40000 ALTER TABLE `tbltransferbill` DISABLE KEYS */;
INSERT INTO `tbltransferbill` VALUES (18,1,4,1,51,120000),(19,2,3,2,41,230000),(20,2,4,1,85,570000),(21,4,2,4,96,2400000),(22,2,6,5,120,3450000);
/*!40000 ALTER TABLE `tbltransferbill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbluser`
--

DROP TABLE IF EXISTS `tbluser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbluser` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_nickname` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `user_fullname` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `user_images` varchar(200) DEFAULT NULL,
  `user_email` varchar(100) DEFAULT NULL,
  `user_notes` text,
  `user_permission` smallint(6) DEFAULT NULL,
  `user_last_modified_id` int(11) DEFAULT NULL,
  `user_last_modified_date` varchar(15) DEFAULT NULL,
  `user_gender` smallint(1) DEFAULT NULL,
  `user_address` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `user_created_date` varchar(15) DEFAULT NULL,
  `user_deleted` smallint(6) DEFAULT '0',
  `user_mobile_phone` varchar(15) DEFAULT NULL,
  `user_office_phone` varchar(20) DEFAULT NULL,
  `user_social_links` varchar(100) DEFAULT NULL,
  `user_parent_id` int(11) DEFAULT NULL,
  `user_logined` int(10) unsigned NOT NULL DEFAULT '0',
  `user_name` varchar(45) NOT NULL,
  `user_pass` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbluser`
--

LOCK TABLES `tbluser` WRITE;
/*!40000 ALTER TABLE `tbluser` DISABLE KEYS */;
INSERT INTO `tbluser` VALUES (1,'Dog lover','John Wick','\\home\\images\\user\\01.jpg','doglover@gmail.com','Ng??i tiêu dùng',1,0,NULL,0,'7 Lê Duẩn, Quận 1, Thành phố Hồ Chí Minh.',NULL,0,'0342312435','0456323456',NULL,5,0,'DogLoverr','NoDogNoLife3000'),(2,'Pé','Nguyễn Thị Ngọc Mai','\\home\\images\\user\\02.jpg','mai06071969@gmail.com','Không có thông tin',1,0,NULL,0,'46 Hòa Mã, Quận Ba Đình, Hà Nội.',NULL,0,'0123423131','0563457123',NULL,4,0,'Pé','Pe060769'),(3,'SKT Khánh','Mai Quốc Khánh','\\home\\images\\user\\03.jpg','faker123@gmail.com','Không có thông tin',1,0,NULL,1,'191 Lãng Yên, Quận Hà Đông, Hà Nội.',NULL,0,'0257123423','0353412345',NULL,3,0,'SktKhanh','FakeVoDich'),(4,'Cloudy Vân','Tô Văn Vân','\\home\\images\\user\\04.jpg','imhigh@gmail.com','Không có thông tin',1,0,NULL,1,'57 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội.',NULL,0,'0385175892','0653451234',NULL,2,0,'Cloudy999','VanCloud'),(5,'Tuấn White','Trần Tuấn Bạch','\\home\\images\\user\\05.jpg','maninblack@gmail.com','Không có thông tin',1,1,NULL,1,'27 Liễu Giai, Quận Ba Đình, Hà Nội.',NULL,0,'0385174512','0578412345',NULL,1,0,'TuanWhite','TrangNhuOMO'),(6,'Donald Trump','Đỗ Nam Trung','\\home\\images\\user\06.jpg','greatagain999@gmail.com','Qu?n lý t?i chi nhánh Lâm ??ng',4,0,NULL,1,'51 Nguyễn Du, Quận Hai Bà Trưng, Hà Nội.',NULL,0,'08967912345','0123542523',NULL,20,347,'admin@gmail.com','75d23af433e0cea4c0e45a56dba18b30');
/*!40000 ALTER TABLE `tbluser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblworkplace`
--

DROP TABLE IF EXISTS `tblworkplace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblworkplace` (
  `workplace_id` int(11) NOT NULL AUTO_INCREMENT,
  `workplace_name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `workplace_type` smallint(6) NOT NULL DEFAULT '0' COMMENT '1 là kho hàng, 2 là cửa hàng',
  `workplace_address` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `workplace_status` smallint(1) NOT NULL DEFAULT '1',
  `workplace_manager_id` int(11) DEFAULT NULL,
  `workplace_website_link` varchar(45) DEFAULT NULL COMMENT 'Đường dẫn liên kết đến trang web làm việc',
  `workplace_map_link` varchar(45) DEFAULT NULL COMMENT 'Đường dẫn địa chỉ',
  `workplace_created_date` varchar(45) NOT NULL COMMENT '''1/1/2000''',
  `workplace_last_modified_id` int(11) DEFAULT NULL,
  `workplace_last_modified_date` varchar(45) DEFAULT NULL,
  `workplace_deleted` smallint(6) unsigned NOT NULL DEFAULT '0',
  `workplace_images` varchar(100) DEFAULT NULL,
  `workplace_investment` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Vốn đầu tư',
  `workplace_expected_profit` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Lợi nhuận mong muốn',
  `workplace_notes` text,
  `workplace_phone` varchar(15) DEFAULT NULL,
  `workplace_email` varchar(45) DEFAULT NULL,
  `workplace_creator_id` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`workplace_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblworkplace`
--

LOCK TABLES `tblworkplace` WRITE;
/*!40000 ALTER TABLE `tblworkplace` DISABLE KEYS */;
INSERT INTO `tblworkplace` VALUES (1,'Kho Vingroup',1,'Tầng 7, Tòa nhà Vincom Center, 72 Lê Thánh Tôn, Phường Bến Nghé, Quận 1, Thành phố Hồ Chí Minh.',1,1,NULL,NULL,'30/4/2023',NULL,'',0,'/home/images/workplace/storage01.jpg',123000000,234000000,'',NULL,NULL,0),(2,'Kho Viettel',1,'285 Cách Mạng Tháng Tám, Phường 12, Quận 10, Thành phố Hồ Chí Minh.',1,2,NULL,NULL,'1/5/2023',NULL,'',0,'/home/images/workplace/storage02.jpg',321000000,653000000,'',NULL,NULL,0),(3,'Kho FPT',1,'Số 17 Duy Tân, Dịch Vọng Hậu, Cầu Giấy, Hà Nội.',1,3,NULL,NULL,'28/6/2023',NULL,'',0,'/home/images/workplace/storage01.jpg',254000000,954020000,'',NULL,NULL,0),(4,'Kho Sun Group',1,'14, Hạ Long , Bãi Cháy, Thành phố Hạ Long, Quảng Ninh.',1,4,NULL,NULL,'14/2/2023',NULL,'',0,'/home/images/workplace/storage03.jpg',953000000,1602500000,'',NULL,NULL,0),(5,'Kho PV Gas',1,'36 Hoàng Cầu, Đống Đa, Hà Nội.',1,5,NULL,NULL,'3/4/2023',NULL,'',0,'/home/images/workplace/storage02.jpg',121000000,450200000,'',NULL,NULL,0),(6,'Kho hàng Lâm Đồng',0,'Lâm Đồng',1,3,NULL,NULL,'29/12/2003',0,NULL,0,'/home/images/workplace/storage03.jpg',14200000,35000000,'',NULL,NULL,0),(7,'Kho hàng Nguyên',0,'Lâm ??ng',1,19,NULL,NULL,'29/12/2003',0,NULL,0,NULL,0,0,NULL,NULL,NULL,0),(8,'Kho hàng Nguyên',0,'Lâm ??ng',1,19,NULL,NULL,'29/12/2003',0,NULL,0,NULL,0,0,NULL,NULL,NULL,0),(9,'',0,'',1,0,NULL,NULL,'15/12/2023',0,NULL,0,NULL,0,0,'','','',0),(10,'',0,'',1,0,NULL,NULL,'15/12/2023',0,NULL,0,NULL,0,0,'','','',0),(11,'',0,'',1,0,NULL,NULL,'15/12/2023',0,NULL,0,NULL,0,0,'','','',0);
/*!40000 ALTER TABLE `tblworkplace` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblworkplacestoragedetail`
--

DROP TABLE IF EXISTS `tblworkplacestoragedetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tblworkplacestoragedetail` (
  `workplace_storage_detail_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID cặp khóa',
  `workplace_id` int(11) NOT NULL COMMENT 'ID nơi làm việc',
  `product_id` int(11) NOT NULL COMMENT 'ID sản phẩm',
  `product_quantity` int(11) NOT NULL DEFAULT '0' COMMENT 'Số lượng sản phẩm',
  `workplace_storage_created_date` varchar(45) NOT NULL,
  `workplace_storage_deleted` smallint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`workplace_storage_detail_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COMMENT='Thông tin về sản phẩm được lưu trong kho hoặc cửa hàng ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblworkplacestoragedetail`
--

LOCK TABLES `tblworkplacestoragedetail` WRITE;
/*!40000 ALTER TABLE `tblworkplacestoragedetail` DISABLE KEYS */;
INSERT INTO `tblworkplacestoragedetail` VALUES (1,1,1,1,'',0),(2,2,2,2,'',0),(3,3,3,3,'',0),(4,4,4,4,'',0),(5,5,5,5,'',0),(6,7,1,12,'',0),(7,7,3,5,'',0),(8,7,1,12,'',0),(9,7,3,5,'',0),(10,8,1,12,'',0),(11,8,3,5,'',0);
/*!40000 ALTER TABLE `tblworkplacestoragedetail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-30 19:14:59
