package ra.dto.response;

import lombok.Data;
import ra.model.entity.Catalog;
import ra.model.entity.Color;
import ra.model.entity.Size;

import java.util.ArrayList;
import java.util.List;
@Data
public class PreCreatProduct {
    List<Catalog> catalogList=new ArrayList<>();
    List<Size> sizeList= new ArrayList<>();
    List<Color> colorList= new ArrayList<>();
}
