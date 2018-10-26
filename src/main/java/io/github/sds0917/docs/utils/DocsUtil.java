package io.github.sds0917.docs.utils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import com.github.javafaker.Faker;

public final class DocsUtil {

	private DocsUtil() {
	}

	private static final Random RANDOM = new Random();
	private static final Faker ZHFAKER = new Faker(new Locale("zh-CN"));
	private static final Faker ENFAKER = new Faker(new Locale("en-US"));
	private static final Map<String, String> FIELD_VALUE = new HashMap<String, String>();

	static {
		FIELD_VALUE.put("name", ZHFAKER.name().username());
		FIELD_VALUE.put("account", ENFAKER.name().username());
		FIELD_VALUE.put("password", "******");
		FIELD_VALUE.put("id", UUID.nameUUIDFromBytes(FIELD_VALUE.get("name").getBytes(Charset.forName("UTF-8"))).toString());
		FIELD_VALUE.put("sex", String.valueOf(randomInt(0, 1)));
	}

	public static int randomInt(int min, int max) {
		return RANDOM.nextInt(max - min) + min;
	}

	public static String getValLikeName(String name) {
		for (Entry<String, String> e : FIELD_VALUE.entrySet()) {
			if (name.contains(e.getKey()) || e.getKey().contains(name)) {
				return e.getValue();
			}
		}
		return "null";
	}
}
