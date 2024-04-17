package com.khai.admin.config;

import com.khai.admin.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ProductItemProcessor implements ItemProcessor<Product, Product> {
    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

    @Override
    public Product process(final Product p) {
        final String pName = p.getName().toUpperCase();
        final String pDesc = p.getDescription().toUpperCase();

        final Product transformedProduct = new Product();
        transformedProduct.setName(pName);
        transformedProduct.setDescription(pDesc);

        log.info("Converting (" + p + ") into (" + transformedProduct + ")");

        return transformedProduct;
    }
}
