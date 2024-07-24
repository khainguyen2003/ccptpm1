# Có 23 mẫu pattern được chia thành 3 nhóm
- Creational Pattern (nhóm khởi tạo – 5 mẫu) gồm: Factory Method, Abstract Factory, Builder, 
Prototype, Singleton. Những Design pattern loại này cung cấp một giải pháp để tạo ra các object 
và che giấu được logic của việc tạo ra nó, thay vì tạo ra object một cách trực tiếp bằng cách 
sử dụng method new. Điều này giúp cho chương trình trở nên mềm dẻo hơn trong việc quyết định 
object nào cần được tạo ra trong những tình huống được đưa ra.

- Structural Pattern (nhóm cấu trúc – 7 mẫu) gồm: Adapter, Bridge, Composite, Decorator, Facade, 
Flyweight và Proxy. Những Design pattern loại này liên quan tới class và các thành phần của object. 
Nó dùng để thiết lập, định nghĩa quan hệ giữa các đối tượng.

- Behavioral Pattern (nhóm tương tác/ hành vi – 11 mẫu) gồm: Interpreter, Template Method, Chain of 
Responsibility, Command, Iterator, Mediator, Memento, Observer, State, Strategy và Visitor. Nhóm này 
dùng trong thực hiện các hành vi của đối tượng, sự giao tiếp giữa các object với nhau.

## 1. Factory Pattern 
- Là Pattern chịu trách nhiệm quản lý và tạo các đối tượng theo điều kiện đầu vào (Như loại sản phẩm để quyết
định tạo ra đối tượng là PhoneItem hay LaptopItem)
- Giả sử ta có một hệ thống sử dụng dịch vụ của các ngân hàng, dựa vào tên ngân hàng ta có thể quyết định
nên đưa ra đối tượng ngân hàng cần dùng. Khi muốn thêm dịch vụ cho ngân hàng khác thì ta chỉ cần thêm class
triển khai các dịch vụ của ngân hàng cần thêm và thêm loại có thể vào FactoryClass mà không cần phải sửa đổi code
```java
// Base class
public interface Bank {
    String getBankName();
}
// Các lớp triển khai
public class TPBank implements Bank {

    @Override
    public String getBankName() {
        return "TPBank";
    }
    // Triển khai các dịch vụ của TPBank
}

public class VietcomBank implements Bank {

    @Override
    public String getBankName() {
        return "VietcomBank";
    }
    // Triển khai các dịch vụ của TPBank
}
// Các class Triển khai các dịch vụ của các ngân hàng khác tương tự

// Factory class
public class BankFactory {
    private BankFactory() {
    }
    // Nếu muốn thêm dịch vụ của ngân hàng khác thì chỉ cần truyền loại ngân hàng ở đây và thêm loại đó vào switch-case
    public static final Bank getBank(BankType bankType) {
        switch (bankType) {

            case TPBANK:
                return new TPBank();

            case VIETCOMBANK:
                return new VietcomBank();
            // Thêm các loại khác ở đây
            default:
                throw new IllegalArgumentException("This bank type is unsupported");
        }
    }

}
```

### Tham khảo
[https://gpcoder.com/4352-huong-dan-java-design-pattern-factory-method/](https://gpcoder.com/4352-huong-dan-java-design-pattern-factory-method/)
