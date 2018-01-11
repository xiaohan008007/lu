package com.taotaosou.lu;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		// System.out.println( "Hello World!" );
		List<String> list = FileUtils.readLines(new File("C:\\Users\\Administrator\\Documents\\1111.csv"));
		int line = 0;
		for (String w : list) {
			w = w.trim();
			if (StringUtils.isNumeric(w)) {
				// System.out.println((line++) + " " + w);
				continue;
			}
			if (w.length() == 1) {
				// System.out.println((line++) + " " + w);
				continue;
			}
			if (w.length() > 8) {
				System.out.println((line++) + "_" + w);
			}
		}

		// System.out.println(StringUtils.isNumeric(w) +
		// StringUtils.isNumeric("12864"));
	}
}
