package application.Services.impl;

import application.Dto.ProductDto;
import application.Repository.ProductRepository;
import application.Repository.CategoryRepository;
import application.Services.IProductService;
import application.exception.ResourceNotFoundException;
import application.model.Category;
import application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public ProductDto create(ProductDto productDto) {
        if (productDto.getId() != null) {
            throw new IllegalArgumentException("Product ID should be null for new product.");
        }
        Product product = convertToEntity(productDto);
        productRepository.saveAndFlush(product);
        return convertToDto(product);
    }

    @Transactional
    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found."));

        existingProduct.setName(productDto.getName());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setInventory(productDto.getInventory());

        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existingProduct.setCategory(category);
        }

        Product updatedProduct = productRepository.saveAndFlush(existingProduct);
        return convertToDto(updatedProduct);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found."));
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found."));
        return convertToDto(product);
    }

    @Override
    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for the given category");
        }
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ProductDto updateInventory(Long productId, int quantityChange) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        int newInventory = product.getInventory() + quantityChange;
        if (newInventory < 0) {
            throw new IllegalArgumentException("Inventory cannot be negative");
        }

        product.setInventory(newInventory);
        Product updatedProduct = productRepository.saveAndFlush(product);
        return convertToDto(updatedProduct);
    }

    @Override
    public List<ProductDto> searchWithName(String name) {
        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found with name: " + name);
        }
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setPrice(product.getPrice());
        productDto.setInventory(product.getInventory());
        productDto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        return productDto;
    }

    public  Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setPrice(productDto.getPrice());
        product.setInventory(productDto.getInventory());

        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            product.setCategory(category);
        }

        return product;
    }
}
