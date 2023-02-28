package org.dows.order.api.func;


import java.util.function.Consumer;

@FunctionalInterface
public interface TestNotNullBranch<T> {
    /**
     * 处理if else 逻辑函数式编程 isNotNull 消费 否则处理别的任务
     *
     * @param function
     * @param runnable
     */
    void handleBranch(Consumer<T> function, Runnable runnable);

    /**
     * 逻辑 isNotNull 分支
     *
     * @param t
     * @param <T>
     * @return
     */
    static <T> TestNotNullBranch<T> isNotNullTest(T t) {
        return (c, r) -> {
            if (t == null || "".equals(t)) {
                r.run();
            } else {
                c.accept(t);
            }
        };
    }

}
