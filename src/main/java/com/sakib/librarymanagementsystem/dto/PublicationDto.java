package com.sakib.librarymanagementsystem.dto;

import com.sakib.librarymanagementsystem.modal.Publication;

public record PublicationDto(String name , String address , String description) {

    public Publication toPublication()
    {
        return new Publication(name , address , description);
    }
}
