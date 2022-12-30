package ra.dto.response;

import ra.model.entity.Cart;

import java.util.Date;
import java.util.List;

public class JwtResponse {
    private String fullName;
    private String token;
    private String type = "Bearer";
    private String userName;
    private String userEmail;
    private String userPhone;
    private String avatar;
    private String userAddress;
    private List<String> listRoles;
    private Cart cart;

    public JwtResponse(String fullName, String token, String userName, String userEmail, String userPhone, String avatar, String userAddress, List<String> listRoles,Cart cart) {
        this.fullName = fullName;
        this.token = token;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.avatar = avatar;
        this.userAddress = userAddress;
        this.listRoles = listRoles;
        this.cart=cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public List<String> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<String> listRoles) {
        this.listRoles = listRoles;
    }

}
