package ra.dto.response;

import lombok.Data;
import ra.model.entity.Catalog;

@Data
public class CatalogDto {
    private int catalogId;
    private String catalogName;
    private String catalogImg;
    private String catalogDescription;
    private String parentName;
    private int parentId;
    private boolean catalogStatus= true;

    public CatalogDto(Catalog catalog) {
        this.catalogId = catalog.getCatalogId();
        this.catalogName = catalog.getCatalogName();
        this.catalogImg = catalog.getCatalogImg();
        this.catalogDescription = catalog.getCatalogDescription();
        this.parentName = catalog.getParentName();
        this.parentId = catalog.getParentId();
        this.catalogStatus = catalog.isCatalogStatus();
    }
}
