# Các lưu ý khi connect db
- Cần kiểm tra hệ thống có bao nhiêu connect
- Thông báo khi server quá tải connect
- Có nên disconnect liên tục hay ko -> ko
- Nếu vượt quá poolsize thì sao: Nếu số lượng yêu kết nối vượt quá số lượng poolsize thi 
kết nối mới sẽ phải đợi có kết nối free thì mới được kết nối và thực thi