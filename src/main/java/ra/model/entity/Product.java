package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private int productId;
    @Column(name = "productName", columnDefinition = "nvarchar(50)",unique = true,nullable = false)
    private String productName;
    @Column(name = "creatDate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creatDate;
    @Column(name = "ImPortPrice")
    private float imPortPrice;
    @Column(name = "tittle")
    private String title;
    @Column(name = "Discount")
    private float productDiscount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catalogId")
    private Catalog catalog;
    @Column(name = "productImg")
    private String productImg;
    @Column(name="productDescription")
    private String productDescription;
    @Column(name = "productStatus")
    private boolean productStatus;
    @OneToMany(mappedBy = "product")
    List<SubImg> imgList= new ArrayList<>();
    @OneToMany(mappedBy = "product")
    List<ProductDetail> detailList= new ArrayList<>();
}
