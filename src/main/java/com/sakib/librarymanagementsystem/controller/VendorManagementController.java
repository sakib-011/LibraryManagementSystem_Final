package com.sakib.librarymanagementsystem.controller;

import com.sakib.librarymanagementsystem.dto.VendorDto;
import com.sakib.librarymanagementsystem.modal.Vendor;
import com.sakib.librarymanagementsystem.service.VendorService;
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
public class VendorManagementController {

    private final VendorService vendorService;

    public VendorManagementController(VendorService vendorService){
        this.vendorService = vendorService;
    }


    @GetMapping("/vendor_management")
    public String vendorManagement(
            @RequestParam(value = "searchQuery" , required = false) String searchInput,
            @RequestParam(value = "vendorId" , required = false) Long vendorId ,
            @RequestParam(value = "vendorDeleteId" , required = false) Long vendorDeleteId ,
            Model model , HttpSession httpSession){

        ArrayList<Vendor> vendors = vendorService.getAllVendors();
        model.addAttribute("vendor" , new VendorDto("" , "" ,"", "",""));


        String addVendorSuccess = (String) httpSession.getAttribute("addVendorSuccessful");
        String addVendorFailed = (String) httpSession.getAttribute("addVendorFailed");

        if(addVendorSuccess!=null && addVendorSuccess.equals("true")){
            model.addAttribute("alertMessage", "Vendor added successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("addVendorSuccessful");
        } else if(addVendorFailed!=null && addVendorFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to add Vendor!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("addVendorFailed");
        }


        if(vendorId != null){
            httpSession.setAttribute("vendorId" , vendorId);
            System.out.println(vendorId);
            model.addAttribute("editMode" , true);
            Vendor editVendor = vendorService.getVendor(vendorId.intValue());
            model.addAttribute("editVendor" , editVendor);

        }

        if(vendorDeleteId!=null){
            httpSession.setAttribute("vendorDeleteId" , vendorDeleteId);
            model.addAttribute("deleteMode" , true);
        }


        String editVendorSuccess = (String) httpSession.getAttribute("editVendorSuccessful");
        String editVendorFailed = (String) httpSession.getAttribute("editVendorFailed");

        if(editVendorSuccess!=null && editVendorSuccess.equals("true")){
            model.addAttribute("alertMessage", "Vendor Edit successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("editVendorSuccessful");
        } else if(editVendorFailed!=null && editVendorFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to edit Vendor!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("editVendorFailed");
        }


        String deleteVendorSuccess = (String) httpSession.getAttribute("deleteVendorSuccessful");
        String deleteVendorFailed = (String) httpSession.getAttribute("deleteVendorFailed");

        if(deleteVendorSuccess!=null && deleteVendorSuccess.equals("true")){
            model.addAttribute("alertMessage", "Vendor Delete successfully!");
            model.addAttribute("alertType", "success");
            httpSession.removeAttribute("deleteVendorSuccessful");
        } else if(deleteVendorFailed!=null && deleteVendorFailed.equals("true")){
            model.addAttribute("alertMessage", "Failed to delete Vendor!");
            model.addAttribute("alertType", "error");
            httpSession.removeAttribute("deleteVendorFailed");
        }

        model.addAttribute("vendors" , vendors);

        if(searchInput != null){
            model.addAttribute("vendors" , searchInput(searchInput));
        }

        return "vendor_management";
    }





    @PostMapping("/vendor_management")
    public String vendorManagement(
            @RequestParam(value = "action" , required = false) String action ,
            @ModelAttribute VendorDto vendorDto , Model model , HttpSession httpSession) {


        switch (action){
            case "add" :
                        Vendor vendor = vendorDto.toVendor();

                        if(vendor == null){
                            httpSession.setAttribute("addVendorFailed" , "true");
                            return  "redirect:/vendor_management";
                        }

                        if(addNewVendor(vendor)){
                            httpSession.setAttribute("addVendorSuccessful" , "true");
                        } else{
                            httpSession.setAttribute("addVendorFailed" , "true");
                        }
                        break;

            case "edit" :
                        Vendor editVendor = vendorDto.toVendor();
                        Long vendorId = (Long) httpSession.getAttribute("vendorId");
                        editVendor.setVendorId(vendorId.intValue());
                        System.out.println("number  : " + vendorDto.phone());
                System.out.println("Edit vendor phone number  : " + editVendor.getPhone());
                        if(updateVendor(editVendor)){
                            httpSession.setAttribute("editVendorSuccessful", "true");
                        } else {
                            httpSession.setAttribute("editVendorFailed", "true");
                        }
                        break;
            case "delete" :
                        Long vendorDeleteId =  (Long) httpSession.getAttribute("vendorDeleteId");

                        if(deleteVendor(vendorDeleteId.intValue())){
                            httpSession.setAttribute("deleteVendorSuccessful", "true");
                        } else {
                            httpSession.setAttribute("deleteVendorFailed", "true");
                        }
                         break;


        }


        return "redirect:/vendor_management";
    }



    private boolean addNewVendor(Vendor vendor){
        return  vendorService.addNewVendor(vendor);
    }
    private boolean updateVendor(Vendor vendor) { return  vendorService.updateVendor(vendor);}
    private boolean deleteVendor(int  id) { return  vendorService.deleteVendor(id);}

    private List<Vendor> searchInput(String text){
        ArrayList<Vendor> vendors = vendorService.getAllVendors();
        if(text == null || text.isEmpty()){
            return vendors;
        }
        final String input = text.trim().toLowerCase();

        return vendors.stream()
                .filter(b ->
                        b.getName().toLowerCase().trim().contains(input) ||
                                b.getCompanyName().toLowerCase().trim().contains(input) ||
                                b.getEmail().toLowerCase().trim().contains(input) ||
                                b.getPhone().toLowerCase().trim().contains(input) ||
                                b.getAddress().toLowerCase().trim().contains(input)
                ).toList();
    }



}
