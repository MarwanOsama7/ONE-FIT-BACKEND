package com.global.ProjectManagement.Services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.global.ProjectManagement.Base.Exception.FileStorageException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class UploadFileService {

	private Path fileStorageLocation;

//	@Value("${file.upload.base-path}")    /var/www/images/  E:\\Global\\book
	private final String basePath = "/var/www/images/";

	public Map<String, String> storeFile(MultipartFile file, Long id, String pathType, String content) {
		// Resolve base path
		this.fileStorageLocation = Paths.get(basePath + pathType).toAbsolutePath().normalize();
		log.info("File storage location: " + fileStorageLocation.toString());

		// Create directories if not exist
		try {
			if (!Files.exists(this.fileStorageLocation)) {
				Files.createDirectories(this.fileStorageLocation);
				log.info("Directory created: " + fileStorageLocation.toString());
			}
		} catch (Exception ex) {
			log.error("Could not create the directory where the uploaded files will be stored.", ex);
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}

		String fileName = StringUtils.cleanPath(id + "-" + file.getOriginalFilename());
		log.info("Storing file: " + fileName);

		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			log.info("Target location: " + targetLocation.toString());

			try (InputStream targetStream = file.getInputStream()) {
				Files.copy(targetStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
			}

			Map<String, String> response = new HashMap<>();
			response.put("fileName", fileName);
			response.put("filePath", "https://briskshop.store/uploads/" + pathType + "/" + fileName);
			response.put("message", "File stored successfully");

			return response;

		} catch (IOException ex) {
			log.error("Could not store file " + fileName + ". Please try again!", ex);
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public List<String> ChangeNameImages(List<MultipartFile> files, String directory, int id, String code)
			throws IOException {
		List<String> newFileNames = new ArrayList<>();
		int photoNumber = 1;

		// Create the directory if it doesn't exist
		File uploadDir = new File(directory);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs(); // Create directories if needed
		}

		for (MultipartFile file : files) {
//			String originalFilename = file.getOriginalFilename();
			String newFilename = String.format("%d_code%s-pic%d.jpg", id, code, photoNumber);

			// Create a file instance
			File destinationFile = new File(uploadDir, newFilename);

			// Transfer the file to the new location
			file.transferTo(destinationFile);

			// Add the new file name to the list
			newFileNames.add(newFilename);

			// Increment the photo number for the next file
			photoNumber++;
		}

		// Return the list of new file names
		return newFileNames;
	}

	// Method to get the base path
	public String getBasePath() {
		return basePath;
	}

	public File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			log.error("Error converting the multi-part file to file= ", ex.getMessage());
		}
		return file;
	}

}
