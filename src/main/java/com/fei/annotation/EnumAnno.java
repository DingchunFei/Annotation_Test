package com.fei.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface EnumAnno {
    int UN_KNOW = -1;
    int UN_START = 0;
    int PROGRESSING = 1;
    int COMPLETED = 2;
}