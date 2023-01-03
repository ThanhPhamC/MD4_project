package ra.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.ProductModel;
import ra.dto.request.ProductUpdate;
import ra.dto.response.DetailProduct;
import ra.dto.response.PreCreatProduct;
import ra.dto.response.ProductForView;
import ra.model.entity.*;
import ra.model.service.*;

import java.time.LocalDate;

import java.util.HashMap;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/product")
@RestController
@AllArgsConstructor
public class ProductController {
    private SizeService sizeService;
    private ColorService colorService;
    private SubImgService subImgService;
    private CatalogService catalogService;
    private ProductService productService;

    @GetMapping("/getPaggingAndSortByName")
    public ResponseEntity<Map<String, Object>> getPaggingAndSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3 ") int size,
            @RequestParam String direction) {
        Sort.Order order;
        if (direction.equals("asc")) {
            order = new Sort.Order(Sort.Direction.ASC, "productName");
        } else {
            order = new Sort.Order(Sort.Direction.DESC, "productName");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        Page<Product> pageProduct = productService.getPagging(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("products", pageProduct.getContent());
        data.put("total", pageProduct.getSize());
        data.put("totalItems", pageProduct.getTotalElements());
        data.put("totalPages", pageProduct.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping ("/creatNew")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> preCreatNew(){
        PreCreatProduct preCreatProduct = new PreCreatProduct();
        preCreatProduct.setCatalogList(catalogService.findCatalogForCreatProductDetail());
        preCreatProduct.setSizeList(sizeService.findSizeForCreatProductDetail());
        preCreatProduct.setColorList(colorService.findColorForCreatProductDetail());
        return ResponseEntity.ok().body(preCreatProduct);
    }

    @PostMapping("/creatNew")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> creatNew(@RequestBody ProductModel model) {
        try {
            Product product = new Product();
            product.setProductName(model.getProductName());
            product.setImPortPrice(model.getImPortPrice());
            product.setCreatDate(LocalDate.now());
            product.setProductImg(model.getProductImg());
            product.setProductDescription(model.getProductDescription());
            product.setProductDiscount(model.getProductDiscount());
            product.setProductStatus(true);
            Catalog catalog = (Catalog) catalogService.findById(model.getCatalogId());
            product.setCatalog(catalog);
            product.setTitle(model.getTitle());
            Product pr= (Product) productService.saveOrUpdate(product);
            for (String str : model.getSubImgList()) {
                SubImg sub = new SubImg();
                sub.setProduct(pr);
                sub.setImgLink(str);
                subImgService.saveOrUpdate(sub);
            }
            return ResponseEntity.ok("Creat new product successfully");
        }catch (Exception exception){
            return ResponseEntity.ok("Creat new product error");
        }
    }
    @PutMapping("/updateProduct")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdate pUdate) {
        try {
            Product product = new Product();
            product.setProductId(pUdate.getProductId());
            product.setProductName(pUdate.getProductName());
            product.setImPortPrice(pUdate.getImPortPrice());
            product.setCreatDate(LocalDate.parse(pUdate.getCreatDate()));
            product.setProductImg(pUdate.getProductImg());
            product.setProductDescription(pUdate.getProductDescription());
            product.setProductDiscount(pUdate.getProductDiscount());
            product.setProductStatus(pUdate.isProductStatus());
            product.setCatalog((Catalog) catalogService.findById(pUdate.getCatalogId()));
            product.setTitle(pUdate.getTitle());
            Product pr= (Product) productService.saveOrUpdate(product);
            for (String str : pUdate.getSubImgList()) {
                SubImg sub = new SubImg();
                sub.setProduct(pr);
                sub.setImgLink(str);
                subImgService.saveOrUpdate(sub);
            }
            return ResponseEntity.ok("Update product successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Update product error");
        }
    }
    @GetMapping("/getByProductId")
    public ResponseEntity<?> getByProductId(int productId){
        Product pr= (Product) productService.findById(productId);
        System.out.println(pr.getCatalog().getCatalogId());
        return ResponseEntity.ok(pr);
    }
    @GetMapping("/detailProduct")
    public ResponseEntity<?> detailProduct(int productId){
        try {
            Product product= (Product) productService.findById(productId);
            ProductForView forView= new ProductForView(product);
            return ResponseEntity.ok().body(forView);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Error!");
        }
    }
}
