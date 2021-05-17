// IBookManager.aidl
package com.wing.android;
import com.wing.android.Book;

// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}