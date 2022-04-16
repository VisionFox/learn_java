package com.lusir;

public class Singleton {
    // 阻止外部new
    private Singleton() {
    }

    // static 为了配合 static方法的访问
    // volatile 为了防止指令重排
    private static volatile Singleton instance = null;

    public static Singleton getInstance() {
        // 内存可见直接比较，比加锁省资源
        if (Singleton.instance == null) {
            // 加锁初始化，防止并发初始化
            synchronized (Singleton.class) {
                // 防止刚进sync时，有其他线程已经初始化完成，需要再比较一层
                if (Singleton.instance == null) {
                    Singleton.instance = new Singleton();
                }
            }
        }
        return Singleton.instance;
    }
}
