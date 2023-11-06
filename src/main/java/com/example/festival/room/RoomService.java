package com.example.festival.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.festival.exception.NotFoundException;
import com.example.festival.exception.RoomException;
import com.example.festival.hotel.Hotel;
import com.example.festival.hotel.HotelDTO;
import com.example.festival.hotel.HotelRepository;
import com.example.festival.hotel.HotelService;
import com.example.festival.image.Image;
import com.example.festival.image.ImageDTO;
import com.example.festival.image.ImageService;

@Service
public class RoomService {
    
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private HotelService hotelService;

    @Transactional
    public RoomDTO add(Room room, String imageName) {

        String hotel_id = room.getHotel().getId();
        Optional<Hotel> hotelDB = hotelRepository.findById(hotel_id);
        if (!hotelDB.isPresent()) {
            //throw new NotFoundException("Khách sạn không tồn tại");
        }

        List<Room> rooms = roomRepository.findRoomsByHotelId(hotel_id);
        for (Room roomHotel : rooms) {
            if (roomHotel.getName().equals(room.getName())) {
                throw new RoomException("Tên phòng đã tồn tại trong khách sạn");
            }
        }

        Optional<Image> image = imageService.findByName(imageName);
        if (!image.isPresent()) {
            throw new NotFoundException("Ảnh không tồn tại");
        }
        ImageDTO imageDTO = new ImageDTO(image.get().getId(), image.get().getName(), image.get().getType());
        
        room.setHotel(hotelDB.get());
        room.setImage(image.get());
        Room roomDB = roomRepository.save(room);

        Image imgHotel = hotelDB.get().getImage();
        ImageDTO imgHDto = new ImageDTO(
            imgHotel.getId(), 
            imgHotel.getName(), 
            imgHotel.getType()
        );

        HotelDTO hotelDTO = new HotelDTO(
            hotelDB.get().getId(),
            hotelDB.get().getName(),
            hotelDB.get().getLocation(),
            hotelDB.get().getIntroduce(),
            hotelDB.get().getServices(),
            imgHDto
        );
        
        RoomDTO roomDTO = new RoomDTO(
            roomDB.getId(),
            roomDB.getHotel().getId(),
            roomDB.getName(), 
            roomDB.getPrice(), 
            roomDB.getServices(), 
            imageDTO,
            hotelDTO
        );

        return roomDTO;
    }

    @Transactional
    public List<RoomDTO> findByHotelId(String hotel_id) {

        List<Room> rooms = roomRepository.findRoomsByHotelId(hotel_id);
        List<RoomDTO> roomDTOs = new ArrayList<>();
        HotelDTO hotelDTO = hotelService.findById(hotel_id);
        for (Room room : rooms) {

            Image image = room.getImage();
            ImageDTO imageDTO = new ImageDTO(image.getId(), image.getName(), image.getType());

            RoomDTO roomDTO = new RoomDTO(
                room.getId(),
                room.getHotel().getId(),
                room.getName(),
                room.getPrice(),
                room.getServices(),
                imageDTO,
                hotelDTO
            );
            roomDTOs.add(roomDTO);
        }
        return roomDTOs;
    }

    @Transactional
    public RoomDTO findById(String id) {
        Optional<Room> roomDB = roomRepository.findById(id);
        
        if (!roomDB.isPresent()) {
            throw new NotFoundException("Không tìm thấy phòng");
        }
        HotelDTO hotelDTO = hotelService.findById(roomDB.get().getHotel().getId());

        Image image = roomDB.get().getImage();
        ImageDTO imageDTO = new ImageDTO(image.getId(), image.getName(), image.getType());
        RoomDTO roomDTO = new RoomDTO(
            roomDB.get().getId(),
            roomDB.get().getHotel().getId(),
            roomDB.get().getName(),
            roomDB.get().getPrice(),
            roomDB.get().getServices(),
            imageDTO,
            hotelDTO
        );
        return roomDTO;
    }

    @Transactional
    public List<RoomDTO> deleteById(String hotel_id , String room_id) {

        Optional<Hotel> hotelDB = hotelRepository.findById(hotel_id);
        if (!hotelDB.isPresent()) {
            throw new NotFoundException("Không tìm thấy khách sạn");
        }

        Optional<Room> roomDB = roomRepository.findById(room_id);
        if (!roomDB.isPresent()) {
            throw new NotFoundException("Không tìm thấy phòng");
        }

        roomRepository.deleteById(room_id);
        return findByHotelId(hotel_id);
    }
}
