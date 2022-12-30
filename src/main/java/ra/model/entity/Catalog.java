package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ra.dto.request.CatalogModel;
import ra.dto.response.CatalogDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "catalog")
@Data
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalogId")
    private int catalogId;
    @Column(name = "catalogName", nullable = false, unique = true)
    private String catalogName;
    @Column(name = "catalogImg")
    private String catalogImg;
    @Column(name = "catalogDescription")
    private String catalogDescription;
    @Column(name = "parentName")
    private String parentName;
    @Column(name = "parentId")
    private int parentId;
    @JsonIgnore
    @OneToMany(mappedBy = "catalog")
    List<Product> productList = new ArrayList<>();
    @Column(name = "catalogStatus")
    private boolean catalogStatus= true;

    public Catalog(CatalogModel catalogModel) {
        this.catalogName = catalogModel.getCatalogName();
        this.catalogImg = catalogModel.getCatalogImg();
        this.catalogDescription = catalogModel.getCatalogDescription();
        if(catalogModel.getParentId()==0){
            this.parentName="Root";
        }else {
            this.parentName = catalogModel.getParentName();
        }
        this.parentId=catalogModel.getParentId();
        this.catalogStatus = true;
    }
    public Catalog(CatalogDto catalogDto) {
        this.catalogId= catalogDto.getCatalogId();
        this.catalogName = catalogDto.getCatalogName();
        this.catalogImg = catalogDto.getCatalogImg();
        this.catalogDescription = catalogDto.getCatalogDescription();
        this.parentName = catalogDto.getParentName();
        this.parentId=catalogDto.getParentId();
        this.catalogStatus = true;
    }

    public Catalog() {

    }
}
