package com.khai.admin.dto.Product;

import java.util.UUID;

public record ProductBarcode(UUID id, String name, String code, int price, int sl) {
}
