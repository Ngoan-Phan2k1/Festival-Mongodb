package com.example.festival.hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.festival.exception.HotelException;
import com.example.festival.exception.NotFoundException;
import com.example.festival.image.Image;
import com.example.festival.image.ImageDTO;
import com.example.festival.image.ImageService;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ImageService imageService;
    
    @Transactional
    public HotelDTO add(Hotel hotel, String imageName) {
        
        Boolean hotelDB = hotelRepository.existsByName(hotel.getName());
        if (hotelDB) {
            throw new HotelException("Khách sạn đã tồn tại");
        }

        Optional<Image> image = imageService.findByName(imageName);
        if (!image.isPresent()) {
            throw new NotFoundException("Ảnh không tồn tại");
        }

        ImageDTO imageDTO = new ImageDTO(image.get().getId(), image.get().getName(), image.get().getType());
        hotel.setImage(image.get());
        Hotel hotelSave = hotelRepository.save(hotel);
        HotelDTO hotelDTO = new HotelDTO(
           hotelSave.getId(),
           hotelSave.getName(),
           hotelSave.getLocation(),
           hotelSave.getIntroduce(),
           hotelSave.getServices(),
           imageDTO
        );
        return hotelDTO;
    }

    @Transactional
    public HotelDTO findById(String id) {
        
        Optional<Hotel> hotelDB = hotelRepository.findById(id);
        if (hotelDB.isPresent()) {

            Image image = hotelDB.get().getImage();
            ImageDTO imageDTO = null;
            if (image != null){
                imageDTO = new ImageDTO(image.getId(), image.getName(), image.getType());
            }
              
            HotelDTO hotelDTO = new HotelDTO(
                hotelDB.get().getId(),
                hotelDB.get().getName(),
                hotelDB.get().getLocation(),
                hotelDB.get().getIntroduce(),
                hotelDB.get().getServices(),
                //hotelDB.get().getRooms(),
                imageDTO
            );
            return hotelDTO;
           
        }
        return null;
    }
}
