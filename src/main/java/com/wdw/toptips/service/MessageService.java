package com.wdw.toptips.service;

import com.wdw.toptips.dao.MessageDao;
import com.wdw.toptips.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wudw
 * @Date: 2019/5/29 21:20
 * @Version 1.0
 */
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public void addMessage(Message message){
        messageDao.addMessage(message);
    }

    public List<Message> getMessageDetail(String conversationId,int offset,int limit){
        return messageDao.getMessageDetail(conversationId,offset,limit);
    }


}
