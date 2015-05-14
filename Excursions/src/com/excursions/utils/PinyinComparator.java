package com.excursions.utils;

import java.util.Comparator;

import com.excursions.bean.User;

public class PinyinComparator implements Comparator<User> {

	public int compare(User u1, User u2) {
		if (u1.getSortLetters().equals("@") || u2.getSortLetters().equals("#")) {
			return -1;
		} else if (u1.getSortLetters().equals("#")
				|| u2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return u1.getSortLetters().compareTo(u2.getSortLetters());
		}
	}

}
