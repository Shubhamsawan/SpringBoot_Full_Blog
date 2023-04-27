package com.sprinboot.blog.controller;

import com.sprinboot.blog.entity.Category;
import com.sprinboot.blog.payload.CategoryDto;
import com.sprinboot.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// http://localhost:8080/swagger-ui/index.html

@RestController
@RequestMapping("/api/catagories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //Build add category rest api
    // url http://localhost:8080/api/catagories
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    //Build get category rest api

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") long categoryId){
        CategoryDto dto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(dto);
    }

    //Build get All category rest api
// URL http://localhost:8080/api/catagories
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    //Build update category rest api
// URL http://localhost:8080/api/catagories/1
    @PutMapping ("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("id") Long categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
    }

    //Build delete category rest api

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted sucessfully...");
    }


}

// Before posting data we need to do login using follwind step
//1. access url http://localhost:8080/api/auth/login
//2. {
//        "usernameOrEmail":"admin@gmail.com",
//        "password":"admin"
//        }
//3. then copy : accesstoken
//4. Then go to api page -> Authorization -> Type: Bearer Token -> copy the token -> then data will send