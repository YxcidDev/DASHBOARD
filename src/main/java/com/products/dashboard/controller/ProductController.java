package com.products.dashboard.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.nio.file.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.products.dashboard.model.ProductModel;
import com.products.dashboard.repository.ProductRepository;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/product/add")
    public String formProduct(Model model) {
        ProductModel product = new ProductModel();
        model.addAttribute("product", product);
        return "add";
    }

    @PostMapping("/product")
    public String saveProduct(@ModelAttribute("product") ProductModel product,
            @RequestParam("image") MultipartFile imageFile) {

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String extension = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
                String fileName = UUID.randomUUID().toString() + "." + extension;
                Path imagePath = Paths.get("src/main/resources/static/img/products/" + fileName);

                Files.createDirectories(imagePath.getParent());
                Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                product.setImg("/img/products/" + fileName);
            } else {
                if (product.getId() != null) {
                    ProductModel existing = productRepository.findById(product.getId()).orElse(null);
                    if (existing != null) {
                        product.setImg(existing.getImg());
                    }
                }
            }

            productRepository.save(product);
            return "redirect:/products?success=saved";

        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/products?error=imageUpload";
        }
    }

    @GetMapping("/product/edit/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        Optional<ProductModel> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());
            return "add";
        } else {
            return "redirect:/?error=notFound";
        }
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return "redirect:/products?success=deleted";
        } else {
            return "redirect:/products?error=deleteFailed";
        }
    }
}
