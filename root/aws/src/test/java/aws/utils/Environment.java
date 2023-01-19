package aws.utils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 環境変数にアクセスするためのインタフェース.
 * @author kero
 *
 */
public interface Environment {

	/**
	 * 環境変数を追加する.
	 * @param key 追加するキー
	 * @param val キーの値
	 * @throws ReflectiveOperationException 
	 */
	default public void set(String key, String val) throws ReflectiveOperationException  {
		getMap().put(key, val);
	}
	
	/**
	 * 環境変数を削除する.
	 * @param key 削除するキー
	 * @throws ReflectiveOperationException 
	 */
	default public void delete(String key) throws ReflectiveOperationException {
		getMap().remove(key);
	}
	
	static Map<String, String> getMap() throws ReflectiveOperationException {
		Class<?> clazz = Class.forName("java.lang.ProcessEnvironment");
		Field f = clazz.getDeclaredField("theCaseInsensitiveEnvironment");
		f.setAccessible(true);
		return (Map<String, String>) f.get(null);
	}


}
