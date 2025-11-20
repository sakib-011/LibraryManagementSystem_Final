package com.sakib.librarymanagementsystem.serviceInterface;

import com.sakib.librarymanagementsystem.modal.BookAllotmentHistory;
import java.util.ArrayList;

public interface BookAllotmentHistoryServiceInterface {

    boolean addBookAllotment(BookAllotmentHistory allotment);

    boolean updateBookAllotment(BookAllotmentHistory allotment);

    boolean deleteBookAllotment(int id);

    BookAllotmentHistory getBookAllotment(int id);

    ArrayList<BookAllotmentHistory> getAllBookAllotments();

}
