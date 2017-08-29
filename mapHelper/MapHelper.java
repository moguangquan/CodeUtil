import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import bean.MapBean;
import reflect.BeanUtils;
/**
 * map助手类
 */
public class MapHelper {
    /**
     * 判断一个Map容器是否为null或为空
     * 
     * @param map
     *            Map容器
     * @return 若Map容器为null或为空，则返回true；否则返回false
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
    
    /**
     * 判断一个Map容器是否不为null且不为空
     * 
     * @param map
     *            Map容器
     * @return 若Map容器不为null且不为空，则返回true；否则返回false
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !MapHelper.isEmpty(map);
    }
    
    /**
     * 将Object对象的属性转到Map容器里
     * 
     * @param objBean
     *            Object对象
     */
    public static MapBean toMap(Object objBean) {
        MapBean map = new MapBean();
        obj2Map(map, objBean);
        return map;
    }
    
    /**
     * 将Object对象的属性转到Map容器里
     * 
     * @param objBean
     *            Object对象
     * @param ignoreSuperCls
     *            忽略超类为ignoreSuperCls属性
     */
    public static MapBean toMap(Object objBean, Class<?> ignoreSuperCls) {
        MapBean map = new MapBean();
        obj2Map(map, objBean, ignoreSuperCls);
        return map;
    }
    
    /**
     * 找到sourceClass直接继承于superClass的类
     * 
     * @param sourceClass
     *            当前类
     * @param superClass
     *            超类
     * @return 直接继承于superClass的类
     */
    private static Class<?> findSuberClass(Class<?> sourceClass, Class<?> superClass) {
        Class<?> tmpClass = sourceClass;
        while (tmpClass != Object.class && tmpClass.getSuperclass() != superClass) {
            tmpClass = tmpClass.getSuperclass();
        }
        return tmpClass;
    }
    
    /**
     * 将Object对象的属性转到Map容器里
     * 
     * @param map
     *            Map容器
     * @param objBean
     *            Object对象
     */
    public static void obj2Map(MapBean map, Object objBean) {
        obj2Map(map, objBean, null);
    }
    
    /**
     * 将Object对象的属性转到Map容器里
     * 
     * @param map
     *            Map容器
     * @param objBean
     *            Object对象
     * @param ignoreSuperCls
     *            忽略超类为ignoreSuperCls属性
     */
    public static void obj2Map(MapBean map, Object objBean, Class<?> ignoreSuperCls) {
        if (objBean == null) {
            return;
        }
        
        Class<?> cls = objBean.getClass();
        if (ignoreSuperCls != null) {
            cls = findSuberClass(cls, ignoreSuperCls);
        }
        
        PropertyDescriptor pds[] = BeanUtils.getPropertyDescriptors(cls);
        for (int i = 0; i < pds.length; i++) {
            PropertyDescriptor pd = pds[i];
            if ("class".equals(pd.getName())) {
                continue;
            }
            
            try {
                Method readMethod = pd.getReadMethod();
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                
                Object value = readMethod.invoke(objBean, new Object[0]);
                if (value == null) {
                    value = StringHelper.EMPTY;
                }
                
                map.put(pd.getName(), value);
            } catch (Throwable ex) {
                throw new RuntimeException("Could not copy properties from source to target", ex);
            }
        }
    }
    
    /**
     * 将Map容器的对象转到Object对象的属性里
     * 
     * @param map
     *            Map容器
     * @param objBean
     *            Object对象
     */
    public static void map2Obj(Map<?, ?> map, Object objBean) {
        if (objBean == null) {
            return;
        }
        
        Class<?> cls = objBean.getClass();
        PropertyDescriptor targetPds[] = BeanUtils.getPropertyDescriptors(cls);
        for (int i = 0; i < targetPds.length; ++i) {
            PropertyDescriptor targetPd = targetPds[i];
            if ("class".equals(targetPd.getName())) {
                continue;
            }
            if (targetPd.getWriteMethod() == null) {
                continue;
            }
            
            Object value = map.get(targetPd.getName());
            if (value == null) {
                continue;
            }
            
            if (value instanceof String) {
                if (((String) value).length() <= 0 || targetPd.getPropertyType() != String.class) {
                    continue;
                }
            }
            
            try {
                Method writeMethod = targetPd.getWriteMethod();
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                writeMethod.invoke(objBean, new Object[] {value });
            } catch (Throwable ex) {
                throw new RuntimeException("Could not copy properties from source to target", ex);
            }
        }
    }
    
}
