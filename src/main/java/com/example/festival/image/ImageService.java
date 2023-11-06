package com.example.festival.image;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.festival.exception.NotFoundException;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    public ImageDTO uploadImage(MultipartFile file) throws IOException {

        Optional<Image> dbImageData = imageRepository.findByName(file.getOriginalFilename());
        if (dbImageData.isPresent()) {
            throw new NotFoundException("Ảnh đã tồn tại");
        }
        Image imageData = imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageConfig.compressImage(file.getBytes()))
                .build()
                );
        if (imageData != null) {
            ImageDTO imageDTO = new ImageDTO(imageData.getId(), imageData.getName(), imageData.getType());
            return imageDTO;
            //return "File ảnh upload thành công : " + file.getOriginalFilename();
        }
        return null;
    }

    @Transactional
    public byte[] downloadImage(String fileName) {
        Optional<Image> dbImageData = imageRepository.findByName(fileName);
        if (!dbImageData.isPresent()) {
            throw new NotFoundException("Ảnh không tồn tại");
        }

        byte[] images=ImageConfig.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    @Transactional
    public void deleteById(String id) {
        imageRepository.deleteById(id);
    }

    @Transactional
    public Optional<Image> findByName(String fileName) {
        return imageRepository.findByName(fileName);
    }

}
