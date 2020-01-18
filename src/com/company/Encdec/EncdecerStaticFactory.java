package com.company.Encdec;

public class EncdecerStaticFactory {
    public static Encdecer newInstance(String type) {
        if (type.equals(ShiftEncdecer.class.getName())) {
            return new ShiftEncdecer();
        } else if (type.equals(UnicodeEncdecer.class.getName())) {
            return new UnicodeEncdecer();
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
