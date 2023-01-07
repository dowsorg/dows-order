package org.dows.utils;
import java.util.function.Supplier;

public class AssertUtil {

    public static void isTrue(boolean condition,RuntimeException exception){
        if(condition){
            throw exception;
        }
    }

    public static void isTrue(boolean condition, Supplier<RuntimeException> exception){
        if(condition){
            throw exception.get();
        }
    }

}
