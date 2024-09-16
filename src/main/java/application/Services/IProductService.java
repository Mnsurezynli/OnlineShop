package application.Services;

import application.Dto.ProductDto;
import application.model.Product;

import java.util.List;

public interface IProductService {

       ProductDto create(ProductDto productDto);

       ProductDto update(Long id, ProductDto productDto);

       void deleteById(Long id);

       ProductDto getById(Long id);

       List<ProductDto> getAll();

       List<ProductDto> getByCategoryId(Long categoryId);

       ProductDto updateInventory(Long productId, int quantityChange);

       List<ProductDto> searchWithName(String name);



}
