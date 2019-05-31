package com.wdw.toptips.controller;

import com.wdw.toptips.dao.UserDAO;
import com.wdw.toptips.model.Hostholder;
import com.wdw.toptips.model.Message;
import com.wdw.toptips.model.User;
import com.wdw.toptips.model.ViewObject;
import com.wdw.toptips.service.MessageService;
import com.wdw.toptips.service.UserService;
import com.wdw.toptips.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Wudw
 * @Date: 2019/5/29 21:21
 * @Version 1.0
 */

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    Hostholder hostholder;

    @RequestMapping(path = {"/msg/list"},method = RequestMethod.GET)
    public String getMessageList(Model model, @RequestParam("userId") int userId){
        try{
            if(hostholder.getUser() == null || hostholder.getUser().getId() != userId){
                return "redirect:/";
            }
            int localUserId = hostholder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<>();
            List<Message> conversationList = messageService.getConversationList(localUserId,0,10);
            for(Message msg : conversationList){
                ViewObject vo = new ViewObject();
                vo.set("msg",msg);
                int targetId = (msg.getFromId() == localUserId? localUserId:msg.getFromId());
                User user = userService.getUser(targetId);
                vo.set("user",user);
                String conversationId = msg.getConversationId();
                vo.set("unReadCnt",messageService.getUnReadCount(localUserId,conversationId));

                conversations.add(vo);
            }
            model.addAttribute("conversations",conversations);
        }catch (Exception e){
            logger.error("获取消息列表失败 " + e.getMessage());
        }

        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"},method = RequestMethod.GET)
    public String getMessageDetail(Model model, @RequestParam("conversationId") String conversationId){

        try{
            List<Message> conversation = messageService.getMessageDetail(conversationId,0,10);
            List<ViewObject> messages = new ArrayList<>();
            for(Message msg:conversation){
                ViewObject vo = new ViewObject();
                vo.set("message",msg);
                User user = userService.getUser(msg.getFromId());
                if(user == null){
                    continue;
                }
                vo.set("headUrl",user.getHeadUrl());
                messages.add(vo);
            }
            model.addAttribute("messages",messages);
        }catch (Exception e){
            logger.error("获取消息详情失败 "+ e.getMessage());
        }
        return "letterDetail";

    }


    @RequestMapping(path = {"/msg/addMessage"},method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content){
        try {
            Message message = new Message();
            message.setFromId(fromId);
            message.setToId(toId);
            message.setHasRead(0);
            message.setCreatedDate(new Date());
            message.setContent(content);
            message.setConversationId( fromId < toId ? String.format("%d_%d",fromId,toId) : String.format("%d_%d",toId,fromId) );
            messageService.addMessage(message);
            return ToutiaoUtil.getJSONString(message.getId());
        }catch (Exception e){
            logger.error("插入评论失败 " + e.getMessage());
            return ToutiaoUtil.getJSONString(1,"插入评论失败");
        }
    }


}
