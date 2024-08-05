package cs.toronto.edu;
import java.sql.Timestamp;
import java.sql.PreparedStatement;

public class FriendRequest {
    private int requestId;
    private String senderName;
    private String status;
    private Timestamp requestTime;


    public FriendRequest(int requestId, String senderName) {
        this.requestId = requestId;
        this.senderName = senderName;
        this.status = "Pending";
        this.requestTime = new Timestamp(System.currentTimeMillis());
    }

    public FriendRequest(int requestId, String senderName, Timestamp timestamp) {
        this.requestId = requestId;
        this.senderName = senderName;
        this.status = "Pending";
        this.requestTime = timestamp;
    }

    public FriendRequest(int requestId, String senderName,String status, Timestamp timestamp) {
        this.requestId = requestId;
        this.senderName = senderName;
        this.status = status;
        this.requestTime = timestamp;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getStatus() {
        return status;
    }


    // 获取时间戳
    public Timestamp getRequestTime() {
        return requestTime;
    }

    // 设置时间戳
    public void setTimestamp(Timestamp timestamp) {
        this.requestTime = timestamp;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "requestId=" + requestId +
                ", senderName='" + senderName + '\'' +
                ", status='" + status + '\'' +
                ", requestTime=" + requestTime +
                '}';
    }
}
