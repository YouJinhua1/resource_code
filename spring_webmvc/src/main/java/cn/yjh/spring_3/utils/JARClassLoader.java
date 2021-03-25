package cn.yjh.spring_3.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;

public enum JARClassLoader {
    factory;

    JARClassLoader() {
        this.loader = new ClassLoader() {

            private String classpath = this.getClass().getResource("/").getPath();
            private Map<String, JarFile> jarMap = new ConcurrentHashMap<>();

            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                synchronized (getClassLoadingLock(name)) {
                    Class<?> clazz = findLoadedClass(name);
                    ClassLoader parent = this.getClass().getClassLoader().getParent();
                    if (clazz == null) {
                        clazz = findClass(name);
                        if (clazz == null) {
                            if (parent != null) {
                                clazz = super.loadClass(name, false);
                            }
                        }
                    }
                    return clazz;
                }
            }

            @Override
            protected Class<?> findClass(String name) {
                Class clazz = null;
                byte[] classData = null;
                String className = name.substring(name.lastIndexOf(".") + 1, name.length());
                String sourcePath = classpath + File.separator + className + ".class";
                File file = new File(sourcePath);
                if (file.exists()) {
                    FileInputStream in = null;
                    try {
                        in = new FileInputStream(file);
                        FileChannel channel = in.getChannel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(in.available());
                        channel.read(byteBuffer);
                        classData = byteBuffer.array();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (classData != null) {
                    clazz = defineClass(name, classData, 0, classData.length);
                }
                return clazz;
            }
        };
    }

    private ClassLoader loader;

    public ClassLoader getLoader() {
        return loader;
    }
}
