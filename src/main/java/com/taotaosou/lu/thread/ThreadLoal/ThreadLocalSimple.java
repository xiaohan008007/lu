/**
 * 
 */
package com.taotaosou.lu.thread.ThreadLoal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tracy.lu 自己实现Theadlocal 2017年9月6日
 */
public class ThreadLocalSimple<T> {
	Map<Object, T> map = Collections.synchronizedMap(new HashMap<>());

	void set(T o) {
		Thread currentThread = Thread.currentThread();
		map.put(currentThread, o);
	}

	T get() {
		Thread currentThread = Thread.currentThread();
		T o = map.get(currentThread);
		if (o == null) {
			o = initialValue();
			map.put(currentThread, o);
		}
		return o;
	}

	public void remove() {
		Thread currentThread = Thread.currentThread();
		map.remove(currentThread);
	}

	protected T initialValue() {
		return null;
	}

}
