# Các cách quản lý tồn kho
## 1. Trừ số lượng tồn kho khi người dùng thêm vào giỏ hàng
- Ưu điểm: Thân thiện với người dùng, logic đơn giản vì người dùng sẽ biết số lượng chính xác số lượng còn lại trong kho
- Nhược điểm: Dẫn đến tình trạng đặt hàng không có chủ đích hoặc không mua hàng khi đã đặt hàng
dẫn đến người thực sự có nhu cầu mua không thể mua được, ảnh hưởng đến doanh số bán hàng thực sự
- Cách khắc phục:
  1. Đặt thời gian hiệu lực cho mỗi đơn hàng. Nếu sau N phút không thanh toán thì sẽ hủy đơn và hàng tồn kho sẽ được trả
  2. Giới hạn số lượng người dùng có thể đặt cho mỗi sản phẩm. Có thể là mỗi tài khoản hoặc mỗi ip hoặc mỗi tài khoản và
  ip chỉ mua được n sản phẩm 
  3. Kiểm soát ngầm bằng cách đánh giá kĩ thuật, chặn các tài khoản tình nghi, các tài khoản lý lịch đen

## 2. Thanh toán (Người dùng nhận hàng) đồng thời giảm hàng tồn kho
- Ưu điểm: Giảm tiêu dùng không hợp lệ
- Nhược điểm: Có thể gây ra số lượng mua vượt quá số lượng có trong kho
- Cách khắc phục: Những người dùng chưa được giao hàng sẽ được chờ có hàng và thông báo xin lỗi đến khách hàng