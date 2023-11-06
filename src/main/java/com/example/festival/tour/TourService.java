package com.example.festival.tour;

import com.example.festival.exception.NotFoundException;
import com.example.festival.festival.Festival;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.festival.festival.FestivalRepositoty;
import com.example.festival.festival.FestivalService;
import com.example.festival.hotel.Hotel;
import com.example.festival.hotel.HotelDTO;
import com.example.festival.hotel.HotelRepository;
import com.example.festival.hotel.HotelService;
import com.example.festival.image.Image;
import com.example.festival.image.ImageDTO;
import com.example.festival.image.ImageService;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private FestivalService festivalService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ImageService imageService;
    
    public TourDTO add(Tour tour, String imageName) {
        Optional<Festival> festival = festivalService.findById(tour.getFestival().getId());

        Optional<Image> image = imageService.findByName(imageName);
        if (!image.isPresent()) {
            throw new NotFoundException("Ảnh không tồn tại");
        }

        List<Hotel> hotels = tour.getHotels();
        List<Hotel> hotels_save = new ArrayList<Hotel>();
        List<HotelDTO> hotelDTOs = new ArrayList<HotelDTO>();
        for (Hotel hotel : hotels) {

            Optional<Hotel> hotelDB = hotelRepository.findById(hotel.getId());
            if (!hotelDB.isPresent()) {
                //throw new NotFoundException("Khách sạn không tồn tại id " + hotel.getId());
            }
            
            HotelDTO hotelDTO = hotelService.findById(hotel.getId());
            hotelDTOs.add(hotelDTO);
            hotels_save.add(hotelDB.get());
        }
        
        ImageDTO imageDTO = new ImageDTO(image.get().getId(), image.get().getName(), image.get().getType());
        tour.setImage(image.get());
        tour.setFestival(festival.get());
        tour.setHotels(hotels_save);
        Tour tourDB = tourRepository.save(tour);
        TourDTO tourDTO = new TourDTO(
            tourDB.getId(), 
            tourDB.getName(), 
            tourDB.getFromWhere(),
            tourDB.getToWhere(), 
            tourDB.getDescription(),
            tourDB.getFromDate(),
            tourDB.getToDate(),
            tourDB.getPriceAdult(),
            tourDB.getPriceChild(),
            tourDB.getPriceBaby(),
            tourDB.getCapacity(),
            tourDB.getBooked(),
            tourDB.getFestival(),
            imageDTO,
            hotelDTOs
        );
        return tourDTO;

    }

    public List<TourDTO> findTours() {
        List<Tour> tours = tourRepository.findAll();
        List<TourDTO> tourDTOs = new ArrayList<>();

        for (Tour tour : tours) {

            List<Hotel> hotels = tour.getHotels();
            List<HotelDTO> hotelDTOs = new ArrayList<>();
            for (Hotel hotel : hotels) {
                HotelDTO hotelDTO = hotelService.findById(hotel.getId());
                hotelDTOs.add(hotelDTO);
            }

            Image image = tour.getImage();
            ImageDTO imageDTO = new ImageDTO(image.getId(), image.getName(), image.getType());

            TourDTO tourDTO = new TourDTO(
                tour.getId(), 
                tour.getName(), 
                tour.getFromWhere(),
                tour.getToWhere(), 
                tour.getDescription(),
                tour.getFromDate(),
                tour.getToDate(),
                tour.getPriceAdult(),
                tour.getPriceChild(),
                tour.getPriceBaby(),
                tour.getCapacity(),
                tour.getBooked(),
                tour.getFestival(),
                imageDTO,
                hotelDTOs
            );
            tourDTOs.add(tourDTO);
        }
        return tourDTOs;
    }

    @Transactional
    public TourDTO findById(String id) {
        
        Optional<Tour> tourDB = tourRepository.findById(id);
        if (tourDB.isPresent()) {
            List<Hotel> hotels = tourDB.get().getHotels();
            List<HotelDTO> hotelDTOs = new ArrayList<HotelDTO>();
            for (Hotel hotel : hotels) {

                HotelDTO hotelDTO = hotelService.findById(hotel.getId());
                hotelDTOs.add(hotelDTO);
            }

            Image image = tourDB.get().getImage();
            ImageDTO imageDTO = new ImageDTO(image.getId(), image.getName(), image.getType());
            TourDTO tourDTO = new TourDTO(
                tourDB.get().getId(), 
                //tourDB.get().getFestival().getId(), 
                tourDB.get().getName(), 
                tourDB.get().getFromWhere(),
                tourDB.get().getToWhere(), 
                tourDB.get().getDescription(),
                tourDB.get().getFromDate(),
                tourDB.get().getToDate(),
                tourDB.get().getPriceAdult(),
                tourDB.get().getPriceChild(),
                tourDB.get().getPriceBaby(),
                tourDB.get().getCapacity(),
                tourDB.get().getBooked(),
                tourDB.get().getFestival(),
                imageDTO,
                hotelDTOs
            );
            return tourDTO;
        }
        return null;
    }

    @Transactional
    public TourDTO updateTourBooked(String tourId, Integer newBookedValue) {

        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour"));   

        tour.setBooked(newBookedValue);
        Tour tourDB = tourRepository.save(tour);
        Image image = tourDB.getImage();
        ImageDTO imageDTO = null;
        if (image != null) {
            imageDTO = new ImageDTO(image.getId(), image.getName(), image.getType());
        }

        List<Hotel> hotels = tourDB.getHotels();
        List<HotelDTO> hotelDTOs = new ArrayList<>();
        for (Hotel hotel : hotels) {
            HotelDTO hotelDTO = hotelService.findById(hotel.getId());
            hotelDTOs.add(hotelDTO);
        }

        TourDTO tourDTO = new TourDTO(
            tourDB.getId(), 
            //tourDB.getFestival().getId(), 
            tourDB.getName(), 
            tourDB.getFromWhere(),
            tourDB.getToWhere(), 
            tourDB.getDescription(),
            tourDB.getFromDate(),
            tourDB.getToDate(),
            tourDB.getPriceAdult(),
            tourDB.getPriceChild(),
            tourDB.getPriceBaby(),
            tourDB.getCapacity(),
            tourDB.getBooked(),
            tourDB.getFestival(),
            imageDTO,
            hotelDTOs
        );

        return tourDTO;
    }
}
