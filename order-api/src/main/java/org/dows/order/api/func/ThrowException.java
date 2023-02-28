package org.dows.order.api.func;



@FunctionalInterface
public interface ThrowException {
    /**
     * 判断 true  throw日志
     *
     * @param errorMessage
     */
    void throwMessage(String errorMessage);

    /**
     * 分支 throw
     *
     * @param b
     * @return
     */
    static ThrowException isTrue(boolean b) {
        return errorMessage -> {
            if (b) {
                throw new RuntimeException(errorMessage);
            }
        };
    }

}
