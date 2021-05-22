// IOnNewBookArrivedListener.aidl
package com.wing.android.aidl;
import com.wing.android.aidl.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     //custom parcelable args must be in or out or inout
    void onNewBookArrived(in Book book);
}