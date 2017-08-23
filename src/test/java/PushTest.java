import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.junye.android.updater.pojo.PushMsg;
import org.junit.Test;

/**
 * Created by junye on 15/07/2017.
 */
public class PushTest {
    @Test
    public void testPush() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        PushMsg pushMsg = new PushMsg();
        pushMsg.setEvent(3001);

        // 司机端
        PushPayload pushPayload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setMessage(Message.newBuilder().setMsgContent(objectMapper.writeValueAsString(pushMsg)).build())
                .build();

        PushClient pushClient = new PushClient("9b62b6f6eca800da95ade8ef","d8bd9dfd259d80c962ef715e");

        pushClient.sendPush(pushPayload);
    }
}
