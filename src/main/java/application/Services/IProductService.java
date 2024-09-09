package application.Services;

import application.Dto.ProductDto;

import java.util.List;

public interface IProductService {

       public ProductDto add(ProductDto productDto);

       public ProductDto update(Long id , ProductDto productDto);

       public  void  delete(Long id);

       public ProductDto getById(Long id);

       public List<ProductDto> getAll();

       public List<ProductDto> getByCategoryId(Long categoryId);

       public ProductDto inventoryUpdate(Long productId , ProductDto productDto);

       
}
