package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/*
`wsd_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID cặp khóa',
  `wsd_workplace_id` int(11) NOT NULL COMMENT 'ID nơi làm việc',
  `wsd_product_id` int(11) NOT NULL COMMENT 'ID sản phẩm',
  `wsd_product_quantity` int(11) NOT NULL DEFAULT '0' COMMENT 'Số lượng sản phẩm',
  `wsd_created_date` varchar(45) NOT NULL COMMENT 'Ngày khởi tạo',
  `wsd_deleted` smallint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'Trạng thái xóa',
  `wsd_product_price` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'Giá bán theo kho hàng',
  `wsd_creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'Người khởi tạo',
  PK(wsd_workplace_id, wsd_product_id)
  FK1: wsd_workplace_id
  FK2: wsd_product_id
 */

@Entity
@Table(name = "tblwpd")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkplaceDetail {
    @EmbeddedId
    private WpdKey primaryKey;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("workplaceId")
    @JoinColumn(name = "workplace_id")
    private Workplace workplace;

    @Column(name = "wsd_product_quantity", columnDefinition = "int(11) NOT NULL DEFAULT '0' COMMENT 'Số lượng sản phẩm'")
    private int product_quantity;
    @Column(name = "wsd_product_price", columnDefinition = "int(11) NOT NULL DEFAULT '0' COMMENT 'Giá sản phẩm'")
    private int product_price;

}

@Embeddable
class WpdKey implements Serializable {

    @Column(name = "wpd_product_id")
    UUID productId;

    @Column(name = "wpd_workplace_id")
    UUID workplaceId;

    public WpdKey() {
    }

    public WpdKey(UUID productId, UUID workplaceId) {
        this.productId = productId;
        this.workplaceId = workplaceId;
    }

    public UUID getStudentId() {
        return productId;
    }

    public void setStudentId(UUID productId) {
        this.productId = productId;
    }

    public UUID getWorkplace_id() {
        return workplaceId;
    }

    public void setWorkplace_id(UUID workplaceId) {
        this.workplaceId = workplaceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WpdKey wpdKey = (WpdKey) o;
        return productId == wpdKey.productId && workplaceId == wpdKey.workplaceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, workplaceId);
    }
}
