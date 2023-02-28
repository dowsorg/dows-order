package org.dows.order.api.func;


@FunctionalInterface
public interface TestBranch {
    /**
     * 处理if else 逻辑函数式编程 也可以单独 if
     *
     * @param trueRun
     * @param orRun
     */
    void handle(Runnable trueRun, Runnable... orRun);

    /**
     * 逻辑判断 分支
     *
     * @param b
     * @return
     */
    static TestBranch isTrueOrFalse(boolean b) {
        return (t, o) -> {
            if (b) {
                t.run();
            } else {
                for (Runnable runnable : o) {
                    runnable.run();
                }
            }
        };
    }

}
