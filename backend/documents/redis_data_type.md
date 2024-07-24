# Các loại dữ liệu trong redis
## 1. string
- embstring: <= 44 bytes
- raw: > 44 bytes
- int -> Integer
redis sẽ tự động quyết định kiểu cho string theo kích thước và kiểu dữ liệu đưa vào

Vd: 
- set str 0123456789012345678901234567890123456789abcd -> <= 46 kí tự (bytes) nên sẽ là embstr
- set str2 a0123456789012345678901234567890123456789abcd -> Lớn hơn 46 kí tự (bytes) nên sẽ là raws
- set num 1234 -> kiểu số nên là int

### 1. Một số thao tác với string

| Lệnh                                                 | ý nghĩa                                                                                                                                                                     | Ví dụ                                                  |
|------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------|
| set <key> <value>                                    | Tạo cặp key-value trong database                                                                                                                                            | set num 1234                                           |
| object encoding <key>                                | Lấy kiểu của value                                                                                                                                                          | object encoding num -> int                             |
| get <key>                                            | lấy value của key                                                                                                                                                           | get num -> 1234                                        |
| exist <key>                                          | Kiểm tra tồn tại key                                                                                                                                                        | exists num -> (integer) 1<br/>exists num1 -> (integer)0|
| strlen <key>                                         | Lấy độ dài của value của key                                                                                                                                                | strlen num -> (integer) 4                              |
| del <name>                                           | trả về 1 nếu xóa thành công, ngược lại là 0                                                                                                                                 | DEL num -> (integer)1                                  |
| MSET <key1> <value1> <key2> <value2>                 | set nhiều key-value cùng lúc                                                                                                                                                | MSET key1 value1 key2 value2                           |
| MGET <key1> <key2>                                   |                                                                                                                                                                             |                                                        |
| INCR <key>                                           | tăng giá trị của key lên 1                                                                                                                                                  | INCR 0001:like                                         |
| INCRBY <key> number                                  | tăng giá trị của key lên number giá trị -> có thể dùng trong tính like, lượt xem<br/>                                                                                       | INCR 0001:like 10                                      |
| DECR, DECRBY                                         | ngược lại với trên                                                                                                                                                          |                                                        |
| KEYS '*value*', KEY 'value*'                         | Tìm kiếm các key thỏa mãn điều kiện                                                                                                                                         |                                                        |
| EXPIRE <key> <time> hoặc set <key> <value> EX <time> | set thời gian tồn tai cho key                                                                                                                                               |                                                        |
| SET user:1 '{"name": "khai", "age": 200}'            | set đối tượng vào redis                                                                                                                                                     | -> "{\"name\": \"khai\", \"age\": 20}"                 |
| MSET user:1:name khaiabc user:1:age 21               | set lại giá trị cho các thuộc tính                                                                                                                                          |                                                        |
| SET lock_key unique_value NX PX 10000                | set thời gian hết hạn của khóa phân tán là 10s, NX là not exists, PX <time> là set thời gian tồn tại<br/>-> ngăn chặn máy khách hoạt động bất thường và có thể giả mạo khóa |                                                        |
|                                                      |                                                                                                                                                                             |                                                        |

## 2. Kiểu hash
Cho phép lưu trữ value của key là đối tượng

![redis hash](D:\learning\ki6\ccptpm\anh_tham_khao\redis_hash.png)

| Lệnh                                         | Ý nghĩa                       | Ví dụ                         |
|----------------------------------------------|-------------------------------|-------------------------------|
| HSET <key> <attr1> <value1> <attr2> <value2> |                               | HSET user:01 name khai age 19 |
| HGET <key> <attr1>                           |                               | HGET user:01 name age         |
| HMGET <key> <attr1> <attr2>                  |                               |                               |
| HDEL <key> <attr>                            | Xóa giá trị của attr chỉ định |                               |
| HLEN <key> <attr>                            |                               |                               |
| HGETALL <key>                                | Lấy các attr và value         |                               |
| HEXISTS <key> <attr>                         |                               |                               |
| HINCRY <key> <attr> num                      |                               |                               |
| HKEYS <key>                                  |                               |                               |
| HVALS <key>                                  |                               |                               |
