package application.Services;

import application.Dto.CategoryDto;
import application.Dto.ProductDto;

import java.util.List;

public interface ICategoryService {

    CategoryDto create(CategoryDto categoryDto);


    void deleteById(Long id);

    CategoryDto getById(Long id);

    List<CategoryDto> getAll();

}
