package cf.shuhan.domain;

import lombok.Data;

@Data
public class UserInfo {
    private Integer ChatRoomId;

    private Integer Sex;
    private String UserName;
    private String NickName;
}
