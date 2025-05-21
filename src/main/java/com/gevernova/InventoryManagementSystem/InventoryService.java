package com.gevernova.InventoryManagementSystem;

import java.util.*;
import java.util.stream.Collectors;

public class InventoryService {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) throws InvalidProductException {
        if (product.getPrice() < 0 || product.getQuantity() < 0) {
            throw new InvalidProductException("Invalid product data");
        }
        products.add(product);
    }


    public void removeProduct(String name) {
        boolean removed = products.removeIf(p -> p.getName().equalsIgnoreCase(name));
        if (!removed) {
            throw new ProductNotFoundException("Product with name '" + name + "' not found.");
        }
    }

    public List<Product> searchByName(String name) {
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public List<Product> searchByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Product> getLowStockItems() {
        return products.stream()
                .filter(p -> p.getQuantity() < 5)
                .collect(Collectors.toList());
    }

    public List<Product> getSortedProducts() {
        return products.stream()
                .sorted(Comparator.comparing(Product::getCategory)
                        .thenComparing(Product::getPrice))
                .collect(Collectors.toList());
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
}

