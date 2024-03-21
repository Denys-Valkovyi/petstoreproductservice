package com.chtrembl.petstore.product.converter;

import com.chtrembl.petstore.product.model.Product;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Product.StatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(Product.StatusEnum attribute) {
        return attribute.getValue();
    }

    @Override
    public Product.StatusEnum convertToEntityAttribute(String dbData) {
        return Product.StatusEnum.fromValue(dbData);
    }
}