package com.wing.android.messenger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * https://www.jianshu.com/p/23612b2cce30
 * https://www.cnblogs.com/rookiechen/p/5352053.html
 */
public class Book implements Parcelable {

    public int bookId;
    public String bookName;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bookId);
        dest.writeString(this.bookName);
    }

    public void readFromParcel(Parcel source) {
        this.bookId = source.readInt();
        this.bookName = source.readString();
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.bookId = in.readInt();
        this.bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
