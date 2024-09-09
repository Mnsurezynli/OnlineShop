package application.Services.impl;

import application.Dto.CategoryDto;
import application.Services.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public CategoryDto update(Long categoryId, CategoryDto categoryDto) {
        return null;
    }

    @Override
    public void delete(Long categoryId) {

    }

    @Override
    public List<CategoryDto> getAll() {
        return null;
    }

    @Override
    public CategoryDto getById(Long categoryId) {
        return null;
    }
}
