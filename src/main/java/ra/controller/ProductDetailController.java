package ra.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.ProductDetailModel;
import ra.dto.request.ProductDetailUpdate;
import ra.model.entity.Color;
import ra.model.entity.Product;
import ra.model.entity.ProductDetail;
import ra.model.entity.Size;
import ra.model.service.ColorService;
import ra.model.service.ProductDetailService;
import ra.model.service.ProductService;
import ra.model.service.SizeService;

import java.time.LocalDate;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/productDetail")
@AllArgsConstructor
public class ProductDetailController {
    private ProductService productService;
    private ColorService colorService;
    private SizeService sizeService;
    private ProductDetailService detailService;

    @PostMapping("/creatNew")
    public ResponseEntity<?> creatNew(@RequestBody ProductDetailModel model) {
        boolean check = detailService.existsByColor_ColoIdAndAndSize_SizeIdAndProduct_ProductId(
                model.getColorId(), model.getSizeId(), model.getProductId());
        try {
            if (check) {
                ProductDetail productDetail = detailService.findByColor_ColoIdAndSize_SizeIdAndProduct_ProductId(
                        model.getColorId(), model.getSizeId(), model.getProductId());
                productDetail.setQuantity(productDetail.getQuantity() + model.getQuantity());
                detailService.saveOrUpdate(productDetail);
                return ResponseEntity.ok(detailService.saveOrUpdate(productDetail));
            } else {
                ProductDetail detail = new ProductDetail();
                detail.setProduct((Product) productService.findById(model.getProductId()));
                detail.setQuantity(model.getQuantity());
                detail.setColor((Color) colorService.findById(model.getColorId()));
                detail.setSize((Size) sizeService.findById(model.getSizeId()));
                detail.setDateDetail(LocalDate.now());
                detail.setExportDetail(model.getExportDetail());
                detail.setDetailStatus(true);
                detailService.saveOrUpdate(detail);
                return ResponseEntity.ok(detailService.saveOrUpdate(detail));
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("creat new productDetail error");
        }
    }
    @PutMapping("/updateDetail")
    public ResponseEntity<?> updateDetail(@RequestBody ProductDetailUpdate update) {
        ProductDetail detailList= detailService.findByColor_ColoIdAndSize_SizeIdAndProduct_ProductId(
                update.getColorId(), update.getSizeId(),update.getProductId());
        if (detailList!=null&& detailList.getProductDetailId()!=update.getProductDetailId()){
            return ResponseEntity.badRequest().body("update productDetail error, already exists with another Id");
        }else {
            try {
                ProductDetail detail = new ProductDetail();
                detail.setProductDetailId(update.getProductDetailId());
                detail.setDetailStatus(update.isDetailStatus());
                detail.setProduct((Product) productService.findById(update.getProductId()));
                detail.setQuantity(update.getQuantity());
                detail.setColor((Color) colorService.findById(update.getColorId()));
                detail.setSize((Size) sizeService.findById(update.getSizeId()));
                detail.setDateDetail(LocalDate.now());
                detail.setExportDetail(update.getExportDetail());
                detailService.saveOrUpdate(detail);
                return ResponseEntity.ok(detailService.saveOrUpdate(detail));
            } catch (
                    Exception ex) {
                return ResponseEntity.badRequest().body("update productDetail error");
            }
        }
    }
    @PutMapping("/deleteDetail")
    public ResponseEntity<?> updateDetail(int productDetailId) {
        try {
            ProductDetail detail = (ProductDetail) detailService.findById(productDetailId);
            detail.setDetailStatus(false);
            detailService.saveOrUpdate(detail);
            return ResponseEntity.ok(detailService.saveOrUpdate(detail));
        } catch (
                Exception ex) {
            return ResponseEntity.badRequest().body("creat new productDetail error");
        }
    }
}
