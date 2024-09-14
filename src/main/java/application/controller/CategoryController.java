package application.controller;

import application.Dto.CategoryDto;
import application.Services.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private ICategoryService iCategoryService;

    public CategoryController(ICategoryService iCategoryService) {
        this.iCategoryService = iCategoryService;
    }

    @PostMapping("/create")
    public ResponseEntity <CategoryDto> create(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto categoryDto1 = iCategoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody @Valid CategoryDto categoryDto) {
        CategoryDto categoryDto1 = iCategoryService.update(id, categoryDto);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        iCategoryService.deleteById(id);
        return new ResponseEntity<>("category deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
        CategoryDto categoryDto = iCategoryService.getById(id);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
}
