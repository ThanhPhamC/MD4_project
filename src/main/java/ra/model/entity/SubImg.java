package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "subImg")
public class SubImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subImgId")
    private int subImgId;
    @Column(name = "imgLink")
    private String imgLink;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;
}
