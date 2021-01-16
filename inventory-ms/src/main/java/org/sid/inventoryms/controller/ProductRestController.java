//package org.sid.inventoryms.controller;
//
//
//import org.sid.inventoryms.entitites.Product;
//import org.sid.inventoryms.repositories.ProductRepository;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//public class ProductRestController {
//
//    private final ProductRepository productRepository;
//
//    public ProductRestController(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    @GetMapping(path = "/imageProduct/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] image(@PathVariable(name = "id") Long id) throws Exception{
//        Product product=productRepository.findById(id).get();
//        String PhotoName=product.getPhoto();
//        File file =new File(System.getProperty("user.home")+"/Pictures/products/"+ product.getPhoto() +".jpg");
//        Path path= Paths.get(file.toURI());
//        return Files.readAllBytes(path);
//    }
//
//
//
//}
