package org.mphasis.projectUtils;

// A wrapper class to primitive data by reference (i.e. return more than one item of data from a method)
public class Reference<T> {
    private T data;

    public Reference(T data) {
        this.data = data;
    }

    public void set(T data) {
        try {
            this.data = data;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T extract() {
        return data;
    }
}
