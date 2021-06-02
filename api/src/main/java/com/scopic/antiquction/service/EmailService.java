package com.scopic.antiquction.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.scopic.antiquction.model.Bid;
import com.scopic.antiquction.model.Item;
import com.scopic.antiquction.model.User;
import com.scopic.antiquction.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    public ItemRepository itemRepository;

    @Async
    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Scheduled(cron="*/30 * * * * ?")
    public void demoServiceMethod()
    {
        List<Item> items = itemRepository.findByAwarded(false);
        Boolean changed = false;
        for(Item i : items) {
            if(i.getDateEnd().before(new Date())) {
                i.setAwarded(true);
                if(i.getLastBid() != null) {
                    System.out.println("Sending mail to " + i.getLastBid().getUser().getUsername() + " for winning item " + i.getName());
                    sendMessage("antiquction@gmailnator.com", "Item (" + i.getName() + ") won", "Hello " + i.getLastBid().getUser().getUsername() + "!\n\nHere is your bill: \nItem name: " + i.getName() + "\nItem description: " + i.getDescription() + "\nStart price: " + i.getStartPrice() + " €\n" + "Bid price: " + i.getLastBid().getBidPrice() + " €");
                }
                changed = true;
            }
        }
        if(changed)
            itemRepository.saveAll(items);
    }

    public void sendBidNotification(Item item) {
        Long lastUserId = item.getLastBid().getUser().getId();
        List<User> toSend = new ArrayList<>();
        for(Bid bid : item.getBids()) {
            User u = bid.getUser();
            if(u.getId() != lastUserId && !toSend.contains(u)) {
                toSend.add(u);
            }
        }

        for(User u : toSend) {
            System.out.println("Sending mail to " + u.getUsername() + " about new highest bid (" + item.getLastBid().getBidPrice() + ") on item " + item.getName() + " made by user: " + item.getLastBid().getUser().getUsername());
            sendMessage("antiquction@gmailnator.com", "New bid", "There has been new highest bid on item: " + item.getName() + " made by user " + item.getLastBid().getUser().getUsername());
        }
    }
}
