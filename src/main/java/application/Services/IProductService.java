package application.Services;

import application.Dto.ProductDto;

import java.util.List;

public interface IProductService {

       ProductDto add(ProductDto productDto);

       ProductDto update(Long id , ProductDto productDto);

       void  deleteById(Long id);

       ProductDto getById(Long id);

       List<ProductDto> getAll();

        List<ProductDto> getByCategoryId(Long categoryId);

       public ProductDto updateInventory(Long productId , int quantityChange);

       public List<ProductDto> searchWithName(String name);

       
}
