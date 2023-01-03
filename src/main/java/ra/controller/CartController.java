package ra.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.CartConfirm;
import ra.dto.request.CartDetailModel;
import ra.model.entity.Cart;
import ra.model.entity.CartDetail;
import ra.model.entity.ProductDetail;
import ra.model.entity.Users;
import ra.model.sendEmail.ProvideSendEmail;
import ra.model.service.CartDetailService;
import ra.model.service.CartService;
import ra.model.service.ProductDetailService;
import ra.model.service.UserService;
import ra.security.CustomUserDetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("api/v1/cart")
public class CartController {
    private ProvideSendEmail provideSendEmail;
    private CartService cartService;
    private CartDetailService cartDetailService;
    private ProductDetailService productDetailService;
    private UserService userService;

    @PutMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody CartDetailModel cartDetailModel, @RequestParam("action") String action) {
        CartDetail cartDetail = null;
        try {
            cartDetail = cartDetailService.findByProductDetail_ProductDetailIdAndCart_CartId(
                    cartDetailModel.getProductDetailId(), cartDetailModel.getCartId());
            if (cartDetail != null) {
                if (action.equals("add more")) {
                    cartDetail.setDetailCartquantity(cartDetail.getDetailCartquantity() + cartDetailModel.getDetailCartQuantity());
                } else if (action.equals("edit")) {
                    cartDetail.setDetailCartquantity(cartDetailModel.getDetailCartQuantity());
                }
            } else {
                cartDetail = new CartDetail();
                cartDetail.setCart((Cart) cartService.findById(cartDetailModel.getCartId()));
                cartDetail.setDetailCartquantity(cartDetailModel.getDetailCartQuantity());
                cartDetail.setProductDetail((ProductDetail) productDetailService.findById(cartDetailModel.getProductDetailId()));
                cartDetail.setCartDetailStatus(true);
                cartDetail.setPriceCurent(cartDetailModel.getPriceCurrentDetail());
            }
            cartDetailService.saveOrUpdate(cartDetail);
            return ResponseEntity.ok().body("add product to cart successfully");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("add product to cart error");
        }
    }

    @DeleteMapping("/deleteCartDetail")
    public ResponseEntity<?> deleteCartDetail(@RequestParam int detailId) {
        try {
            cartDetailService.delete(detailId);
            return ResponseEntity.ok().body("delete successfully");
        } catch (Exception ex) {
            return ResponseEntity.ok().body("delete error");
        }
    }

    @PutMapping("/confirmCart")
    public ResponseEntity<?> confirmCart(@RequestBody CartConfirm confirm) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = (Cart) cartService.findById(confirm.getCartId());
        try {
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String strName = "PC" + df.format(date) + "-" + (int) ((Math.random()) * 1000);
            cart.setCartName(strName);
            cart.setToAddress(confirm.getToAddress());
            cart.setPhoneNumber(confirm.getPhoneNumber());
            cart.setFullName(confirm.getFullName());
            cart.setEmailConfirm(confirm.getEmailConfirm());
            cart.setTotalAmount(confirm.getTotalAmount());
            cart.setCreatDate(LocalDate.now());
            cart.setNote(confirm.getNote());
            cart.setCartStatus(1);
            cartService.saveOrUpdate(cart);
            for (CartDetail detail : cart.getCartDetails()) {
                ProductDetail productDetail= new ProductDetail();
                 productDetail= detail.getProductDetail();
                productDetail.setQuantity(productDetail.getQuantity()-detail.getDetailCartquantity());
                productDetailService.saveOrUpdate(productDetail);
            }
            String subject = "Payment successfully: " + cart.getCartName();
            String mess = "Thanks for payment. Thank you for your purchase. Your order is being confirmed. Delivery time will be updated after successful confirmation. Please check your email for the latest information.\n" +
                    "Detail oder:\n";
            String sDetail = "";
            for (CartDetail detail : cart.getCartDetails()) {
                sDetail += detail.getProductDetail().getProduct().getProductName() + " Size: " + detail.getProductDetail().getSize().getSizeName() + ", Color: " + detail.getProductDetail().getColor().getColorName() + " x" + detail.getDetailCartquantity() + " " + " x" + detail.getProductDetail().getExportDetail() + "vnd" + "\n";
            }
            mess = mess + sDetail +
                    "-------------------------------------------------\n" +
                    "Total: " + cart.getTotalAmount() + "vnd.\n" +
                    "Full name: " + cart.getFullName() + ".\n" +
                    "Phone: " + cart.getPhoneNumber() + ".\n" +
                    "Address: " + cart.getToAddress()
            ;
            provideSendEmail.sendSimpleMessage(cart.getEmailConfirm(),
                    subject, mess);
            Cart newCart = new Cart();
            newCart.setUsers((Users) userService.findById(customUserDetails.getUserId()));
            cartService.saveOrUpdate(newCart);
            return ResponseEntity.ok().body("Payment successfully, please check your email.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Payment error.");
        }
    }
}
