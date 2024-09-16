package application.Services.impl;

import application.Dto.ProductDto;
import application.Dto.UserProfileDto;
import application.Repository.CategoryRepository;
import application.Repository.ProductRepository;
import application.Services.IProductService;
import application.exception.ResourceAlreadyExistsException;
import application.exception.ResourceNotFoundException;
import application.model.Category;
import application.model.Product;
import application.model.User;
import application.model.UserProfile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public ProductDto add(ProductDto productDto) {
        if (productDto.getId() != null) {
            throw new IllegalArgumentException("Product ID should be null for new product.");
        }
        Product product = convertToEntity(productDto);

        productRepository.saveAndFlush(product);

        System.out.println("The product has been successfully registered");

        return convertToDto(product);
    }


    @Transactional
    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product product1 = convertToEntity(productDto);
            product1.setId(id);
            productRepository.saveAndFlush(product1);
            System.out.println("Product information updated successfully");
        } else {
            throw new ResourceNotFoundException("Product with ID " + id + " not found.");
        }
        return productDto;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            System.out.println("Product deleted successfully");
        } else {
            throw new ResourceNotFoundException("Product with ID " + id + " not found.");
        }
    }

    @Override
    public ProductDto getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return convertToDto(product.get());
        } else {
            throw new ResourceNotFoundException("Product with ID " + id + " not found.");
        }
    }

    @Override
    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return
                products.stream().map(this::convertToDto).collect(Collectors.toList());

    }


    @Override
    public List<ProductDto> getByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for the given category");
        }

        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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
        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }


    @Override
    public List<ProductDto> searchWithName(String name) {
        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found with name: " + name);
        }
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setPrice(product.getPrice());
        productDto.setInventory(product.getInventory());
        productDto.setCategoryId(product.getCategory().getId());

        return productDto;
    }


    private Product convertToEntity(ProductDto productDto) {
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
