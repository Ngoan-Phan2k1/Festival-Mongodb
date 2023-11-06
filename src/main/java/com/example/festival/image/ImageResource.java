package com.example.festival.image;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/image")
public class ImageResource {
    
    @Autowired
	private ImageService service;

    @PostMapping
	public ResponseEntity<ImageDTO> uploadImage(
		//@RequestParam(value = "name", required = false) String name,
		@RequestParam("image") MultipartFile file
		) throws IOException {

		ImageDTO uploadImage = service.uploadImage(file);
		if (uploadImage != null) {
			return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
		}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		//throw new NotFoundException("Upload failed");
	}

    @GetMapping("/download/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName){

		byte[] imageData=service.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}

    @DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
