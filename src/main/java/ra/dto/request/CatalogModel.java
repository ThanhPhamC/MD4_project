package ra.dto.request;

import lombok.Data;

@Data
public class CatalogModel {
    private String catalogName;
    private String catalogImg;
    private String catalogDescription;
    private String parentName;
    private int parentId;
}
