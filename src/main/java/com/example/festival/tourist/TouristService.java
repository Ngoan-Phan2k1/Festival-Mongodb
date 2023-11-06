package com.example.festival.tourist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.festival.exception.NotFoundException;
import com.example.festival.image.Image;
import com.example.festival.image.ImageDTO;
import com.example.festival.image.ImageService;
import com.example.festival.user.User;
import com.example.festival.user.UserRepository;

@Service
public class TouristService {

    @Autowired
    private TouristRepository touristRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public TouristDTO findByUserName(String username) {

        Tourist tourist = touristRepository.findByUsername(username);
        if (tourist != null) {
            Image image = tourist.getImage();
            ImageDTO imageDTO = null;
            if (image != null) {
                imageDTO = new ImageDTO(image.getId(), image.getName(), image.getType());
            }
            TouristDTO touristDTO = new TouristDTO(
                tourist.getId(),
                tourist.getFullname(),
                tourist.getUser().getUsername(),
                tourist.getEmail(),
                //tourist.getPhone(), 
                imageDTO
            );
            return touristDTO;
        }
        return null;
    }

    @Transactional
    public TouristDTO findById(String id) {
        Optional<Tourist> tourist = touristRepository.findById(id);
        if (tourist.isPresent()) {

            Image image = tourist.get().getImage();
            ImageDTO imageDTO = null;
            if (image != null) {
                imageDTO = new ImageDTO(image.getId(), image.getName(), image.getType());
            }
            TouristDTO touristDTO = new TouristDTO(
                tourist.get().getId(),
                tourist.get().getFullname(),
                tourist.get().getUser().getUsername(),
                tourist.get().getEmail(),
                //tourist.get().getPhone(), 
                imageDTO
            );
            return touristDTO;
        }

        return null;
    }

    @Transactional
    public List<TouristDTO> findAll() {

        List<Tourist> tourists = touristRepository.findAll();
        List<TouristDTO> touristDTOs = new ArrayList<>();
        for (Tourist tourist : tourists) {

            Image image = tourist.getImage();
            ImageDTO imageDTO = null;
            if (image != null) {
                imageDTO = new ImageDTO(image.getId(), image.getName(), image.getType());
            }
            TouristDTO touristDTO = new TouristDTO(
                tourist.getId(),
                tourist.getFullname(),
                tourist.getUser().getUsername(),
                tourist.getEmail(),
                //tourist.getPhone(), 
                imageDTO
            );

            touristDTOs.add(touristDTO);
        }

        return touristDTOs;
    }

    @Transactional
    public TouristDTO update(TouristPut tourist, String tourist_id, String image_name) {

        Optional<Tourist> touristDB = touristRepository.findById(tourist_id);
        if (!touristDB.isPresent()) {
            throw new NotFoundException("Không tìm thấy người dùng");
        }

        Optional<Image> new_image = imageService.findByName(image_name);
        Image imageUpdate = null;
        ImageDTO imageDTO = null;
        if (!new_image.isPresent() && touristDB.get().getImage() != null) {
            imageUpdate = touristDB.get().getImage();  //Lấy lại ảnh cũ nếu ko update ảnh mới
            imageDTO = new ImageDTO(imageUpdate.getId(), imageUpdate.getName(), imageUpdate.getType());
        }
        else if (new_image.isPresent()) {
            imageUpdate = new_image.get();
            imageDTO = new ImageDTO(new_image.get().getId(), new_image.get().getName(), new_image.get().getType());  
        }

        touristDB.get().setFullname(tourist.getFullname());
        touristDB.get().setEmail(tourist.getEmail());
        touristDB.get().setImage(imageUpdate);
        
        User user = touristDB.get().getUser();
        user.setUsername(tourist.getUsername());
        userRepository.save(user);
        Tourist tourist_saved = touristRepository.save(touristDB.get());
        TouristDTO touristDTO = new TouristDTO(
            tourist_saved.getId(),
            tourist_saved.getFullname(),
            tourist_saved.getUser().getUsername(),
            tourist_saved.getEmail(),
            imageDTO
        );
        
        return touristDTO;

    }
    
}
