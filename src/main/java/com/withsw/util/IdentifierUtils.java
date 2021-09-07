package com.withsw.util;

import java.util.UUID;

public class IdentifierUtils {

	public static String uuid() {
		return UUID.randomUUID().toString();
	}
}
