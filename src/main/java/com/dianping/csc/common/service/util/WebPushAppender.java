package com.dianping.csc.common.service.util;

import com.dianping.csc.websocket.api.dto.SocketMessageRequest;
import com.dianping.csc.websocket.api.service.WebSocketMessageService;
import com.dianping.pigeon.remoting.ServiceFactory;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by csophys on 15/12/28.
 */
@Setter
@Log
public class WebPushAppender extends AppenderSkeleton {

    private String namespace;
    private String topic;
    private String lionSwitch;

    private static WebSocketMessageService webSocketMessageService = ServiceFactory.getService(WebSocketMessageService.class);

    @Override
    protected void append(LoggingEvent loggingEvent) {
        String logSwitch = LionUtil.getValue(lionSwitch, "0");
        if (logSwitch.equals("1")) {
            log.warning(lionSwitch + ",的webpush 开关处于打开状态。");
            SocketMessageRequest socketMessageRequest = new SocketMessageRequest();
            socketMessageRequest.setData(this.getLayout().format(loggingEvent));
            socketMessageRequest.setDestRequestNameSpace(namespace);
            socketMessageRequest.setTopic(topic);
            webSocketMessageService.sendMessage(socketMessageRequest);
        }
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    public void close() {

    }
}
