package com.kvn.mock.tool;

import java.io.IOException;  
import java.io.InputStream;  
import java.lang.reflect.Method;  
import java.lang.reflect.Modifier;  
import java.util.Arrays; 

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.kvn.mock.exception.TinyMockException;

/**
* @author wzy
* @date 2017年6月22日 上午11:49:46
*/
public class AsmUtils {  
	  
    /** 
     * 获取指定类指定方法的参数名 
     * 
     * @param clazz 要获取参数名的方法所属的类 
     * @param method 要获取参数名的方法 
     * @return 按参数顺序排列的参数名列表，如果没有参数，则返回null 
     */  
    public static String[] getMethodParameterNamesByAsm4(Class<?> clazz, final Method method) {  
        final Class<?>[] parameterTypes = method.getParameterTypes();  
        if (parameterTypes == null || parameterTypes.length == 0) {  
            return null;  
        }  
        final Type[] types = new Type[parameterTypes.length];  
        for (int i = 0; i < parameterTypes.length; i++) {  
            types[i] = Type.getType(parameterTypes[i]);  
        }  
        final String[] parameterNames = new String[parameterTypes.length];  
  
        String className = clazz.getName();  
        int lastDotIndex = className.lastIndexOf(".");  
        className = className.substring(lastDotIndex + 1) + ".class";  
        InputStream is = clazz.getResourceAsStream(className);  
        try {  
            ClassReader classReader = new ClassReader(is);  
            classReader.accept(new ClassVisitor(Opcodes.ASM4) {  
                @Override  
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {  
                    // 只处理指定的方法  
                    Type[] argumentTypes = Type.getArgumentTypes(desc);  
                    if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {  
                        return null;  
                    }  
                    return new MethodVisitor(Opcodes.ASM4) {  
                        @Override  
                        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {  
                            // 静态方法第一个参数就是方法的参数，如果是实例方法，第一个参数是this  
                            if (Modifier.isStatic(method.getModifiers())) {  
                                parameterNames[index] = name;  
                            }  
                            else if (index > 0) {  
                                parameterNames[index - 1] = name;  
                            }  
                        }  
                    };  
  
                }  
            }, 0);  
        } catch (IOException e) {
            throw new TinyMockException(-1, "asm获取方法参数名异常", e);
        }  
        return parameterNames;  
    }
  
}  
