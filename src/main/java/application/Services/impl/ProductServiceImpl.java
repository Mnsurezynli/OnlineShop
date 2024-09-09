package application.Services.impl;

import application.Dto.ProductDto;
import application.Services.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Override
    public ProductDto add(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ProductDto getById(Long id) {
        return null;
    }

    @Override
    public List<ProductDto> getAll() {
        return null;
    }

    @Override
    public List<ProductDto> getByCategoryId(Long categoryId) {
        return null;
    }
}
