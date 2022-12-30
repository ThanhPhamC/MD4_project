package ra.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.dto.response.DetailProduct;
import ra.dto.response.ProductForView;
import ra.model.entity.Product;
import ra.model.service.ColorService;
import ra.model.service.ProductService;
import ra.model.service.SizeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/home") @AllArgsConstructor
public class HomeController {
    private ProductService productService;
    private ColorService colorService;
    private SizeService sizeService;
    @GetMapping("/getAllProduct")
    public ResponseEntity<?>getAllProduct(){
        try {
           List<Product>productList = productService.findAllForUser();
           List<ProductForView> productForViewList = productList.stream().map(product -> new ProductForView(product)).collect(Collectors.toList());
           return ResponseEntity.ok(productForViewList);
        }catch (Exception ex){
           return ResponseEntity.badRequest().body("Error");
        }
    }
   @GetMapping("/getDetailProduct")
   public ResponseEntity<?> detailProduct(int productId){
       try {
           Product product= (Product) productService.findById(productId);
           DetailProduct detailProduct= new DetailProduct(product);
           detailProduct.setColorList(colorService.findByProductDetailList(product.getDetailList()));
           detailProduct.setSizeList(sizeService.findByProductDetailList(product.getDetailList()));
           return ResponseEntity.ok().body(detailProduct);
       }catch (Exception ex){
           return ResponseEntity.badRequest().body("Error!");
       }
   }
}
