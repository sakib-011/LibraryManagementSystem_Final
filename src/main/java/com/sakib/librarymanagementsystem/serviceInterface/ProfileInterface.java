package com.sakib.librarymanagementsystem.serviceInterface;

import com.sakib.librarymanagementsystem.modal.Profile;

import java.util.ArrayList;

public interface ProfileInterface {
    boolean addNewProfile(Profile profile);
    boolean updateProfile(Profile profile);
    boolean deleteProfile(int id);
    Profile getProfile(int id);
    ArrayList<Profile> getAllProfiles();
}
