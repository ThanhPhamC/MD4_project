package ra.dto.request;

import lombok.Data;

import javax.persistence.Column;
@Data
public class ColorModel {
    private String colorName;
    private String colorHex;
}
