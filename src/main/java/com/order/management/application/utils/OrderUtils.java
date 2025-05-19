package com.order.management.application.utils;

import com.order.management.domain.model.OrderModel;
import com.order.management.domain.model.ProductModel;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderUtils {

    public String generateOrderSignature(OrderModel model) {
        String productData = model.getProducts().stream()
                //Faz a ordenação dos elementos da lista de produtos
                .sorted((p1, p2) -> p1.getProductId().compareTo(p2.getProductId()))
                .map(p -> p.getProductId() + "-" + p.getPrice() + "-" + p.getQuantity())
                .collect(Collectors.joining("|"));

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(productData.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar assinatura do pedido", e);
        }
    }

    public Boolean hasDuplicateProductIds(List<ProductModel> products) {
        Map<Long, Long> productIdCounts = products.stream()
                .collect(Collectors.groupingBy(ProductModel::getProductId, Collectors.counting()));

        return productIdCounts.values().stream()
                .anyMatch(count -> count > 1);
    }
}
