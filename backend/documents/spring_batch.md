# Spring batch
1. Khái niệm
   
Spring batch giúp ta xử lý một chuỗi công việc trong một lúc, giảm overhead so với việc xử lý từng request một.

Tại sao chúng ta cần dùng Spring Batch?
- Khi cần xử lý một lượng lớn dữ liệu (không thể load một lần lên memory) trong một khoảng thời gian ngắn.
- Spring Batch hỗ trợ sẵn việc đọc, ghi vào CSV, database, …
- Spring Batch cũng hỗ trợ sẵn các cross-cutting concern như monitoring, logging, cơ chế fail-tolerance, …
- Ngoài ra ta còn dễ dàng start, stop job, quản lý resource các thứ
2. Cấu trúc

![Cấu trúc spring batch](https://thinhdanggroup.github.io/assets/images/streaming/spring-batch-component-1.png)
- _JobLaucher_: interface cho phép thực thi Job với tham số truyền vào
- _Job_: nhận tham số và thực thi các Step được định nghĩa sẵn
- _Step_: ta sẽ qui định các business ở đây. Mỗi Step sẽ gồm 3 bước: Item Reader, Item Processor, Item Writer. Hai bước Reader và Processor sẽ được xử lý đồng thời tuỳ vào lượng worker, riêng bước Writer sẽ xử lý theo chunk.
- _JobRepository_: cung cấp CRUD cho JobLaucher, Job, Step.







### Tham khảo
[https://thinhdanggroup.github.io/spring-batch/](https://thinhdanggroup.github.io/spring-batch/)
