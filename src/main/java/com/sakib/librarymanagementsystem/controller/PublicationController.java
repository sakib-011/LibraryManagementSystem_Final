package com.sakib.librarymanagementsystem.controller;

import com.sakib.librarymanagementsystem.dto.PublicationDto;
import com.sakib.librarymanagementsystem.dto.PurchaseBookDto;
import com.sakib.librarymanagementsystem.modal.Publication;
import com.sakib.librarymanagementsystem.modal.Subscription;
import com.sakib.librarymanagementsystem.service.PublicationService;
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
public class PublicationController {

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/publication")
    public String publication(
            @RequestParam(value =  "searchQuery" , required = false) String searchInput,
            @RequestParam(value = "editId" , required = false) Long editId,
            @RequestParam(value = "deleteId" , required = false) Long deleteId,
            Model model , HttpSession httpSession){

        model.addAttribute("publication" , new PublicationDto("" , "" ,""));
        String add = (String) httpSession.getAttribute("add");
        String addFailed = (String) httpSession.getAttribute("addFailed");

        model.addAttribute("publications" , publicationService.getAllPublications());

        if(add!=null && add.equals("true")){
            model.addAttribute("alertMessage", "Publication add successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("add");
        } else if(addFailed !=null && addFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to add Publication!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("addFailed");
        }


        if(editId!=null){
            httpSession.setAttribute("editId" , editId);
            model.addAttribute("editMode" , true);
            model.addAttribute("editPublication" , publicationService.getPublication(editId.intValue()));
        }


        String edit = (String) httpSession.getAttribute("edit");
        String editFailed = (String) httpSession.getAttribute("editFailed");

        model.addAttribute("publications" , publicationService.getAllPublications());

        if(edit!=null && edit.equals("true")){
            model.addAttribute("alertMessage", "Publication Update successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("edit");
        } else if(editFailed !=null && editFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to Update Publication!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("editFailed");
        }




        if(deleteId!=null){
            httpSession.setAttribute("deleteId" , deleteId);
            model.addAttribute("deleteMode" , true);
//            model.addAttribute("editPublication" , publicationService.getPublication(deleteId.intValue()));
        }


        String delete = (String) httpSession.getAttribute("delete");
        String deleteFailed = (String) httpSession.getAttribute("deleteFailed");

        model.addAttribute("publications" , publicationService.getAllPublications());

        if(delete!=null && delete.equals("true")){
            model.addAttribute("alertMessage", "Publication delete successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("delete");
        } else if(deleteFailed !=null && deleteFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to delete Publication!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("deleteFailed");
        }



        if(searchInput!=null){
            model.addAttribute("publications" , searchField(searchInput));
        }


        return "publication";
    }




    @PostMapping("/publication")
    public String publication(
            @ModelAttribute(value = "action") String action ,
            @ModelAttribute("publication")PublicationDto publicationDto , HttpSession httpSession , Model model
            ){


        if(action.equals("add")){
            Publication publication = publicationDto.toPublication();

            if(publicationService.addNewPublication(publication)){
                httpSession.setAttribute("add" , "true");
            }else{
                httpSession.setAttribute("addFailed" , "true");
            }

        }


        if(action.equals("edit")){
            Publication publication = publicationDto.toPublication();
            Long editId = (long) httpSession.getAttribute("editId");
            publication.setId(editId.intValue());
            if(publicationService.updatePublication(publication)){
                httpSession.setAttribute("edit" , "true");
            }else{
                httpSession.setAttribute("editFailed" , "true");
            }
        }

        if(action.equals("delete")){
//            Publication publication = publicationDto.toPublication();
            Long deleteId = (long) httpSession.getAttribute("deleteId");
//            publication.setId(editId.intValue());
            if(publicationService.deletePublication(deleteId.intValue())){
                httpSession.setAttribute("delete" , "true");
            }else{
                httpSession.setAttribute("deleteFailed" , "true");
            }
        }



        return "redirect:/publication";
    }




    private List<Publication> searchField(String text){
        ArrayList<Publication> publications = publicationService.getAllPublications();
        if(text == null || text.isEmpty()){
            return publications;
        }
        final String input = text.trim().toLowerCase();

        return publications.stream()
                .filter(b ->
                        b.getName().trim().toLowerCase().contains(input) ||
                               b.getAddress().trim().toLowerCase().contains(input) ||
                                b.getDescription().trim().contains(input)
                ).toList();
    }
}
