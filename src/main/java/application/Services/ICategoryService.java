package application.Services;

import application.Dto.CategoryDto;

import java.util.List;

public interface ICategoryService {

    public CategoryDto add(CategoryDto categoryDto);

    public CategoryDto update(Long categoryId , CategoryDto categoryDto );

    public void delete(Long categoryId);

    public List<CategoryDto> getAll();

    public CategoryDto getById(Long categoryId);
}
