package application.Services.impl;

import application.Dto.CategoryDto;
import application.Dto.ProductDto;
import application.Repository.CategoryRepository;
import application.Services.ICategoryService;
import application.model.Category;
import application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Nodes.collect;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    private ProductServiceImpl productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = convertToEntity(categoryDto);
        category = categoryRepository.save(category);
        return convertToDto(category);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
        // .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setName(categoryDto.getName());
        category = categoryRepository.save(category);
        return convertToDto(category);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
        //   .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
        //  .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return convertToDto(category);
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryId(Long id) {
        Category category = categoryRepository.findById(id)
        //.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return category.getProducts().stream()
                .map(productService::convertToDto)
                .collect(Collectors.toList());

    }


    public CategoryDto convertToDto(Category category) {

        if (category == null) {
            return null;
        }
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public Category convertToEntity(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

}