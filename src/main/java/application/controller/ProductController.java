package application.controller;

import application.Dto.ProductDto;
import application.Services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

   private IProductService iProductService;

    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }
    @PostMapping("/add")
    public ResponseEntity<ProductDto> add(@RequestBody ProductDto productDto){
        ProductDto productDto1=iProductService.add(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id , ProductDto productDto){
        ProductDto productDto1=iProductService.update(id,productDto);
        return new ResponseEntity<>(productDto1,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        iProductService.deleteById(id);
        return new ResponseEntity<>("product deleted sucessfully",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id){
        ProductDto productDto=iProductService.getById(id);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(){
        List<ProductDto> productDtos=iProductService.getAll();
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
}

    @GetMapping("/category/{categoryId}")
    public ResponseEntity <List<ProductDto>> getCategoryById(@PathVariable Long categoryId){
        List<ProductDto>productDtos=iProductService.getByCategoryId(categoryId);
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }

    @PatchMapping("/{productId}/inventory")
    public ResponseEntity<ProductDto> updateInventory(@PathVariable Long productId, @RequestParam int quantityChange) {
        ProductDto updatedProduct = iProductService.updateInventory(productId, quantityChange);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchWithName(@RequestParam String name) {
        List<ProductDto> products = iProductService.searchWithName(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}

