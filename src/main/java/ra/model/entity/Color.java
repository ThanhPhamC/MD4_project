package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "colorId")
    private int coloId;
    @Column(name = "colorName", unique = true)
    private String colorName;
    @Column(name = "colorHex")
    private String colorHex;
    @Column(name = "colorStatus")
    private boolean colorStatus;
    @OneToMany(mappedBy = "color")
    @JsonIgnore
    List<ProductDetail> productDetailList = new ArrayList<>();
}
