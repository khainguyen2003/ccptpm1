package com.khai.admin.dto.Product;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BulkUpdateSellProduct {
    UUID[] ids;
    boolean sellStatus;
}
