// IBookManager.aidl
package com.wing.android.messenger;
import com.wing.android.messenger.Book;

// Declare any non-default types here with import statements
//build/generated/aidl_source_output_dir/debug/out/com.wing.android.messenger
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}