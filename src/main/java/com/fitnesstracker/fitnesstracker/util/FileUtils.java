package com.fitnesstracker.fitnesstracker.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fitnesstracker.fitnesstracker.core.domain.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

	public static List<String> readTextFromFile(File file) {

		try (Scanner scanner = new Scanner(new File(Constants.WEIGHTS_FILE_PATH))) {
			ArrayList<String> lines = new ArrayList<>();

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lines.add(line);
			}

			return lines;
		} catch (Exception e) {
			log.error("Error reading text from file: " + file.getAbsolutePath(), e);
			return null;
		}
	}
	
}
