package ra.model.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
@Data
@Entity
@Table
public class CartDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartDetailId")
    private int cartDetailId;
    @Column(name = "priceCurent")
    private float priceCurent;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productDetailId")
    private ProductDetail productDetail;
    @Column(name = "detailCartquantity")
    private int detailCartquantity;
    @Column(name = "cartDetailStatus")
    private boolean cartDetailStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Cart cart;
}
