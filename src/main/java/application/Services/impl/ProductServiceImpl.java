package application.Services.impl;

import application.Dto.ProductDto;
import application.Dto.UserProfileDto;
import application.Repository.ProductRepository;
import application.Services.IProductService;
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

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Transactional
    @Override
    public ProductDto add(ProductDto productDto) {
        Optional<Product> product = productRepository.findById(productDto.getId());
        if (product.isPresent()) {
            System.out.println("There is a product");// اینجا میخوام اکسپشن بزارم
        } else {
            Product product1 = convertToEntity(productDto);
            productRepository.saveAndFlush(product1);
            System.out.println("The product has been successfully registered");
        }
        return productDto;
    }
    @Transactional
    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        Optional<Product> product = productRepository.findById(productDto.getId());
        if (product.isPresent()) {
           Product product1= convertToEntity(productDto);
            productRepository.saveAndFlush(product1);
            System.out.println("Product information updated successfully");
        } else {
            System.out.println("Product not found ");
        }
        return productDto;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<Product>product=productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            System.out.println("Product deleted successfully");
        }else {
            System.out.println("Product not found َََ");
        }
    }

    @Override
    public ProductDto getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return convertToDto(product.get());
        } else {
            System.out.println("Product not found");
        }
        return null;
    }

    @Override
    public List<ProductDto> getAll() {
        List<Product>products=productRepository.findAll();
        return
                products.stream().map(this::convertToDto).collect(Collectors.toList());

}

    @Override
    public List<ProductDto> getByCategoryId(Long categoryId) {
        List<Product>products=productRepository.findByCategoryId(categoryId);
        return
                products.stream().map(this::convertToDto).collect(Collectors.toList());

    }
    @Override
    public ProductDto inventoryUpdate(Long productId, ProductDto productDto) {
     Optional<Product>product=productRepository.findById(productId);
    // .orElseThrow(() -> new ResourceNotFoundException("Product not found"));


    }

    @Override
    public List<ProductDto> searchWithName(String name) {
        return null;
    }



    public ProductDto convertToDto(Product product) {

        if (product == null) {
            return null;
        }
        ProductDto productDto=new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setPrice(product.getPrice());
        productDto.setInventory(product.getInventory());
        return productDto;
    }

    public Product convertToEntity(ProductDto productDto) {
        if (productDto== null) {
            return null;
        }
        Product product=new Product();
      product.setId(productDto.getId());
      product.setName(productDto.getName());
      product.setBrand(productDto.getBrand());
      product.setPrice(productDto.getPrice());
      product.setInventory(productDto.getInventory());
      return product;
    }

}
