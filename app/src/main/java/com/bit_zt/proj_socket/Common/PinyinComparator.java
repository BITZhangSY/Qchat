package com.bit_zt.proj_socket.Common;

import com.bit_zt.proj_socket.DataSet.ContactsEntity;

import java.util.Comparator;

/**
 *昵称首字母排序
 */
public class PinyinComparator implements Comparator<ContactsEntity> {

	public int compare(ContactsEntity o1, ContactsEntity o2) {
		if (o1.getSortLetter().equals("@")
				|| o2.getSortLetter().equals("#")) {
			return -1;
		} else if (o1.getSortLetter().equals("#")
				|| o2.getSortLetter().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetter().compareTo(o2.getSortLetter());
		}
	}

}
