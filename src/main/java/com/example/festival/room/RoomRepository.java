package com.example.festival.room;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.festival.image.Image;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    
    Boolean existsByName(String name);
    //List<Room> findByHotelId(String hotel_id);

    @Query("{'hotel.id': ?0}")
    List<Room> findRoomsByHotelId(String hotelId);
}
