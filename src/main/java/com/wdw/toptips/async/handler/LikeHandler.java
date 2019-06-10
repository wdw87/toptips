package com.wdw.toptips.async.handler;

import com.wdw.toptips.async.EventHandler;
import com.wdw.toptips.async.EventModel;
import com.wdw.toptips.async.EventType;
import com.wdw.toptips.model.Message;
import com.wdw.toptips.model.User;
import com.wdw.toptips.service.MessageService;
import com.wdw.toptips.service.NewsService;
import com.wdw.toptips.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: Wudw
 * @Date: 2019/6/10 21:50
 * @Version 1.0
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Override
    public void doHandler(EventModel model) {
        Message message = new Message();
        User user = userService.getUser(model.getActorId());
        int fromId = 3;
        int toId = model.getEntityOwnerId();
        message.setFromId(fromId);//id 3 代表系统发出的消息
        message.setToId(toId);
        message.setContent(user.getName()
                + "赞了你的资讯，http://127.0.0.1:8080/news/" + model.getEntityId());
        message.setCreatedDate(new Date());
        message.setConversationId( fromId < toId ? String.format("%d_%d",fromId,toId) : String.format("%d_%d",toId,fromId) );
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
