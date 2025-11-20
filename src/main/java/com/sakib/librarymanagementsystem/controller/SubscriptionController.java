package com.sakib.librarymanagementsystem.controller;

import com.sakib.librarymanagementsystem.dto.SubscriptionDto;
import com.sakib.librarymanagementsystem.modal.Subscription;
import com.sakib.librarymanagementsystem.service.SubscriptionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }


    @GetMapping("/subscription")
    public String subscription(
            @RequestParam(value = "deleteSubscriptionId" , required = false) Long deleteId,
            @RequestParam(value = "editSubscriptionId" , required = false) Long editId,
            @RequestParam(value = "searchQuery" , required = false) String searchInput,
            Model model , HttpSession httpSession){
        ArrayList<Subscription> subscriptions = subscriptionService.getAllSubscription();
        model.addAttribute("subscriptions" , subscriptions);
        model.addAttribute("subscription" , new SubscriptionDto("" , null , null));

        String subscriptionAddSuccessfully = (String) httpSession.getAttribute("subscriptionAddSuccessfully");
        String subscriptionAddFailed = (String)  httpSession.getAttribute("subscriptionAddFailed");

        if(subscriptionAddSuccessfully!=null && subscriptionAddSuccessfully.equals("true")){
            model.addAttribute("alertMessage", "Subscription Add successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("subscriptionAddSuccessfully");
        } else if(subscriptionAddFailed !=null && subscriptionAddFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to Add Subscription!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("subscriptionAddFailed");
        }

        if(searchInput!=null){
            model.addAttribute("subscriptions" , searchInput(searchInput));
        }


        if(editId != null){
            httpSession.setAttribute("editId" , editId);
            model.addAttribute("editMode" , true);
            model.addAttribute("editSubscription" , subscriptionService.getSubscription(editId.intValue()));
        }


        String subscriptionUpdateSuccessfully = (String) httpSession.getAttribute("subscriptionUpdateSuccessfully");
        String subscriptionUpdateFailed = (String)  httpSession.getAttribute("subscriptionUpdateFailed");

        if(subscriptionUpdateSuccessfully!=null && subscriptionUpdateSuccessfully.equals("true")){
            model.addAttribute("alertMessage", "Subscription Update successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("subscriptionUpdateSuccessfully");
        } else if(subscriptionUpdateFailed !=null && subscriptionUpdateFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to Update Subscription!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("subscriptionUpdateFailed");
        }


        if(deleteId != null){
            httpSession.setAttribute("deleteId" , deleteId);
            model.addAttribute("deleteMode" , true);
        }


        String subscriptionDeleteSuccessfully = (String) httpSession.getAttribute("subscriptionDeleteSuccessfully");
        String subscriptionDeleteFailed = (String)  httpSession.getAttribute("subscriptionDeleteFailed");

        if(subscriptionDeleteSuccessfully!=null && subscriptionDeleteSuccessfully.equals("true")){
            model.addAttribute("alertMessage", "Subscription Update successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("subscriptionDeleteSuccessfully");
        } else if(subscriptionDeleteFailed !=null && subscriptionDeleteFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to Update Subscription!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("subscriptionDeleteFailed");
        }


        return "subscription";
    }




    @PostMapping("/subscription")
    public String subscription(
            @RequestParam(value = "action" , required = false) String action,
            @ModelAttribute SubscriptionDto subscriptionDto , Model model , HttpSession httpSession){


        switch (action){
            case "add" :
                Subscription subscription = subscriptionDto.toSubscription();
                if(subscriptionService.addNewSubscription(subscription)){
                        httpSession.setAttribute("subscriptionAddSuccessfully" , "true");
                    } else{
                        httpSession.setAttribute("subscriptionAddFailed" , "true");
                    }
                    break;
            case "edit" :
                Long editId = (Long) httpSession.getAttribute("editId");
                Subscription editSubscription =  subscriptionDto.toSubscription();
                editSubscription.setId(editId.intValue());
                if(subscriptionService.updateSubscription(editSubscription)){
                    httpSession.setAttribute("subscriptionUpdateSuccessfully" , "true");
                } else{
                    httpSession.setAttribute("subscriptionUpdateFailed" , "true");
                }
                break;

            case "delete" :
                Long deleteId = (Long) httpSession.getAttribute("deleteId");
                if(subscriptionService.deleteSubscription(deleteId.intValue())){
                    httpSession.setAttribute("subscriptionDeleteSuccessfully" , "true");
                } else{
                    httpSession.setAttribute("subscriptionDeleteFailed" , "true");
                }
                break;
        }


        return "redirect:/subscription";
    }


    private List<Subscription> searchInput(String text){
        ArrayList<Subscription> subscriptions = subscriptionService.getAllSubscription();
        if(text == null || text.isEmpty()){
            return subscriptions;
        }
        final String input = text.trim().toLowerCase();

        return subscriptions.stream()
                .filter(b ->
                        b.getTitle().trim().toLowerCase().contains(input) ||
                                String.valueOf(b.getAmount()).trim().toLowerCase().contains(input) ||
                                String.valueOf(b.getDays()).contains(input)
                ).toList();
    }
}
