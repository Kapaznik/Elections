package application.model;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class Set<T> extends Vector<T> implements Serializable {

	private Vector<T> arr = new Vector<T>();

	protected Set() {
	}

	public boolean add(T object) {
		if (arr.size() != 0) {
			for (int i = 0; i < arr.size(); i++) {
				if (arr.get(i).equals(object) == true)
					return false;
			}
		}
		arr.add(object);
		return true;
	}
	public T get (int i){
		return arr.get(i);
	}
	public int capacity() {
		return arr.capacity();
	}
	public int size() {
		return arr.size();
	}

}
