package com.application.application.services.Implementation;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.application.application.dto.ProductDto;
import com.application.application.model.Product;
import com.application.application.model.User;
import com.application.application.repository.ProductDao;
import com.application.application.services.AuthenticationService;
import com.application.application.services.IdGenrator;
import com.application.application.services.ProductService;

@Service
@Transactional
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	public List<ProductDto> getProducts(int page) {
		int pageSize = 40;
		Pageable paging = PageRequest.of(page, pageSize);
		Page<Product> product = productDao.findAll(paging);
		List<ProductDto> productList = new ArrayList<>();
		for (Product items : product) {
			ProductDto item = new ProductDto(
					items.getProductId(),
					items.getProductName(),
					items.getProductType(),
					items.getProductCount(),
					items.getProductRating(),
					items.getProductPrice(),
					items.getManufacturerName(),
					items.getTag(),
					items.getProductImage());
			productList.add(item);
		}
		return productList;
	}

	@Override
	public List<ProductDto> getProduct() {
		List<Product> product = productDao.findAll();
		List<ProductDto> productList = new ArrayList<>();
		for (Product items : product) {
			ProductDto item = new ProductDto(
					items.getProductId(),
					items.getProductName(),
					items.getProductType(),
					items.getProductCount(),
					items.getProductRating(),
					items.getProductPrice(),
					items.getManufacturerName(),
					items.getTag(),
					items.getProductImage());
			productList.add(item);
		}
		return productList;
	}

	@Override
	public void addProduct(Product product, String email) {
		User user = authenticationService.authenticateUser(email);
		if (user.getCompanyName().equals(product.getManufacturerName())) {
			product.setProductId(IdGenrator.generate());
			product.setUser(user);
			saveProduct(product);
		} else {
			throw new IllegalArgumentException("Manufacturer name can't be different!!!");
		}

	}

	@Override
	public void updateProduct(ProductDto productDto, String email) {
		Product product = getProductById(productDto.getProductId());
		if (authenticationService.authenticateUser(email).getCompanyName().equals(product.getManufacturerName())) {
			product.setProductName(productDto.getProductName());
			product.setProductType(productDto.getProductType());
			product.setProductCount(productDto.getProductCount());
			product.setProductRating(productDto.getProductRating());
			product.setProductPrice(productDto.getProductPrice());
			product.setManufacturerName(productDto.getManufacturerName());
			product.setTag(productDto.getTag());
			product.setProductImage(productDto.getProductImage());
			saveProduct(product);
		} else {
			throw new IllegalArgumentException("User not valid!!!");
		}
	}

	@Override
	public void deleteProduct(long productId, String email) {
		if (authenticationService.authenticateUser(email).getCompanyName()
				.equals(getProductById(productId).getManufacturerName()))
			productDao.deleteById(productId);
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.getById(productId);
	}

	private void saveProduct(Product product) {
		productDao.save(product);
	}

	@Override
	public List<ProductDto> getProductByUser(String email, int page) {
		User user = authenticationService.authenticateUser(email);
		int pageSize = 40;
		Pageable paging = PageRequest.of(page, pageSize);
		List<Product> products = productDao.findAllByUser(user, paging);
		List<ProductDto> productList = new ArrayList<>();
		for (Product items : products) {
			ProductDto item = new ProductDto(
					items.getProductId(),
					items.getProductName(),
					items.getProductType(),
					items.getProductCount(),
					items.getProductRating(),
					items.getProductPrice(),
					items.getManufacturerName(),
					items.getTag(),
					items.getProductImage());
			productList.add(item);
		}
		return productList;
	}

	@Override
	public void updateQuatity(Product product) {
		System.out.println(product);
		saveProduct(product);
	}

	@Override
	public List<ProductDto> searchProduct(String product_name) {
		// System.out.println(product_name);
		// // product_name = "%" + product_name + ";
		// // List<Product> product =
		// // productDao.findByProductNameContainingIgnoreCase(product_name);
		// //System.out.println(product);
		// List<ProductDto> productList = new ArrayList<>();
		// for (Product items : product) {
		// ProductDto item = new ProductDto(
		// items.getProductId(),
		// items.getProductName(),
		// items.getProductType(),
		// items.getProductCount(),
		// items.getProductRating(),
		// items.getProductPrice(),
		// items.getManufacturerName(),
		// items.getTag(),
		// items.getProductImage());
		// productList.add(item);
		// }
		return null;
	}

}
