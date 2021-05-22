package com.wing.android.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * https://www.jianshu.com/p/23612b2cce30
 * https://www.cnblogs.com/rookiechen/p/5352053.html
 * 如果aidl文件中，用到了自定义的parcelable对象，必须新建一个和它同名的aidl文件，并在其中声明它为Parcelable类型
 *
 * // Book.aidl
 * package com.wing.android.aidl;
 *
 * // Declare any non-default types here with import statements
 *
 * parcelable Book;
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

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
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

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
