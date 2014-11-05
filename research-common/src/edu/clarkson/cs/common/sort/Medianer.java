package edu.clarkson.cs.common.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a tool that return the median of a list by doing quick median
 * 
 * @author harper
 * 
 * @param <T>
 */
public class Medianer<T extends Comparable<T>> {

	public T median(List<T> input) {
		int centerIdx = (input.size() - 1) / 2;
		return median(input, centerIdx);
	}

	protected T median(List<T> input, int index) {
		if (input.size() == 1)
			return input.get(0);
		T target = input.get(index);
		List<T> less = new ArrayList<T>();
		List<T> more = new ArrayList<T>();
		for (int i = 0; i < input.size(); i++) {
			if (i == index)
				continue;
			T obj = input.get(i);
			int compare = obj.compareTo(target);
			if (compare < 0) {
				less.add(obj);
			} else {
				more.add(obj);
			}
		}
		if (less.size() > index) {
			// Result is in less
			return median(less, index);
		}
		if (input.size() - 1 - more.size() < index) {
			// Result is in more
			return median(more, index - input.size() + more.size());
		}
		// Result is in center
		return target;
	}
}
