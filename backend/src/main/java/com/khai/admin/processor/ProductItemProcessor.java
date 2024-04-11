package com.khai.admin.processor;

import com.khai.admin.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ProductItemProcessor  implements ItemProcessor<Product, Product> {
    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

    @Override
    public Product process(Product p) {
        final String firstName = p.getName().toUpperCase();
        final String code = p.getCode().toUpperCase();
        final Product transformedPerson = new Product(firstName, code);
        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }
}
