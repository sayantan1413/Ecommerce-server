package com.application.application.services;

import java.util.List;

import com.application.application.dto.ProductDto;
import com.application.application.model.Product;

public interface ProductService {

	public List<ProductDto> getProducts(int page);

	public List<ProductDto> searchProduct(String product_name);

	public Product getProductById(long productId);

	public void addProduct(Product product, String email) throws Exception;

	public void updateProduct(ProductDto productDto, String email) throws Exception;

	public void deleteProduct(long productId, String email) throws Exception;

	public void updateQuatity(Product product);

	public List<ProductDto> getProductByUser(String email, int page);
}
