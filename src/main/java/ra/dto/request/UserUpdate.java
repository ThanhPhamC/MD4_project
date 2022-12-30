package ra.dto.request;
import lombok.Data;
import java.util.Date;
@Data
public class UserUpdate extends UserRegister {
    private int userId;
    private String created;
    private boolean userStatus;
    private String fullName;
}
