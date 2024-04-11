package com.khai.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/*
`wsd_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID cặp khóa',
  `wsd_branch_id` int(11) NOT NULL COMMENT 'ID nơi làm việc',
  `wsd_product_id` int(11) NOT NULL COMMENT 'ID sản phẩm',
  `wsd_product_quantity` int(11) NOT NULL DEFAULT '0' COMMENT 'Số lượng sản phẩm',
  `wsd_created_date` varchar(45) NOT NULL COMMENT 'Ngày khởi tạo',
  `wsd_deleted` smallint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'Trạng thái xóa',
  `wsd_product_price` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'Giá bán theo kho hàng',
  `wsd_creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'Người khởi tạo',
  PK(wsd_branch_id, wsd_product_id)
  FK1: wsd_branch_id
  FK2: wsd_product_id
 */

@Entity
@Table(name = "tblbd")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDetail {
    @EmbeddedId
    private BdKey primaryKey;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("branchId")
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(name = "wsd_product_quantity", columnDefinition = "int(11) NOT NULL DEFAULT '0' COMMENT 'Số lượng sản phẩm'")
    private int product_quantity;
    @Column(name = "wsd_product_price", columnDefinition = "int(11) NOT NULL DEFAULT '0' COMMENT 'Giá sản phẩm'")
    private double product_price;

}

@Embeddable
class BdKey implements Serializable {

    @Column(name = "bd_product_id")
    int productId;

    @Column(name = "bd_branch_id")
    int branchId;

    public BdKey() {
    }

    public BdKey(int productId, int branchId) {
        this.productId = productId;
        this.branchId = branchId;
    }

    public int getStudentId() {
        return productId;
    }

    public void setStudentId(int productId) {
        this.productId = productId;
    }

    public int getWorkplace_id() {
        return branchId;
    }

    public void setWorkplace_id(int branchId) {
        this.branchId = branchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BdKey bdKey = (BdKey) o;
        return productId == bdKey.productId && branchId == bdKey.branchId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, branchId);
    }
}
