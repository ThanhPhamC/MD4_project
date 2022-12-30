package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private int cartId;
    @Column(name = "cartName")
    private String cartName;
    @Column(name = "toAddress")
    private String toAddress;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "emailConfirm")
    private String emailConfirm;
    @Column(name = "totalAmount")
    private float totalAmount;
    @Column(name = "creatDate")
    private LocalDate creatDate;
    @Column(name = "endDate")
    private LocalDate endDate;
    @Column(name = "note")
    private String note;
    @Column(name = "cartStatus")
    private int cartStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private Users users;
    @OneToMany (mappedBy = "cart")
    List<CartDetail>  cartDetails= new ArrayList<>();

}
