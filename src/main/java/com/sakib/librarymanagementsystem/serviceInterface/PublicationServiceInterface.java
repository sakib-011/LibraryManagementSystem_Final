package com.sakib.librarymanagementsystem.serviceInterface;

import com.sakib.librarymanagementsystem.modal.Publication;
import java.util.ArrayList;

public interface PublicationServiceInterface {
    boolean addNewPublication(Publication publication);
    boolean updatePublication(Publication publication);
    boolean deletePublication(int id);
    Publication getPublication(int id);
    ArrayList<Publication> getAllPublications();
}
