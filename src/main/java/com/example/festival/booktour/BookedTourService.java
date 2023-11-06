package com.example.festival.booktour;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.festival.exception.BookedTourException;
import com.example.festival.exception.NotFoundException;
import com.example.festival.room.RoomDTO;
import com.example.festival.room.RoomService;
import com.example.festival.tour.TourDTO;
import com.example.festival.tour.TourService;
import com.example.festival.tourist.TouristDTO;
import com.example.festival.tourist.TouristService;

@Service
public class BookedTourService {

    @Autowired
    private BookedTourRepository bookedTourRepository;

    @Autowired
    private TouristService touristService;

    @Autowired
    private TourService tourService;

    @Autowired
    private RoomService roomService;


    public Optional<BookedTour> findById(String id) {
        return bookedTourRepository.findById(id);
    }
    
    @Transactional
    public BookedTourDTO add(BookedTour bookedTour) {

        TourDTO tour = tourService.findById(bookedTour.getTour().getId());
        //Optional<Tourist> tourist = touristService.findById(bookedTour.getTourist().getId());
        TouristDTO touristDTO = touristService.findById(bookedTour.getTourist().getId());

        if (tour == null) {
            throw new NotFoundException("Không tìm thấy tour" );
        }

        if (touristDTO == null) {
            throw new NotFoundException("Không tìm thấy người đặt" );
        }

        Optional<BookedTour> existBooked = bookedTourRepository.findByTourIdAndTouristId(tour.getId(), touristDTO.getId());
        if (existBooked.isPresent()) {
            throw new BookedTourException("Bạn đã đặt tour này, vui lòng kiểm tra lại");
        }

        int bookedAdult = bookedTour.getBookedAdult();
        int bookedChild = bookedTour.getBookedChild();
        int bookedBaby = bookedTour.getBookedBaby();

        int bookedTotal = bookedAdult + bookedChild + bookedBaby;

        int capacity = tour.getCapacity();
        int currentBooked = tour.getBooked();

        int canBooked = capacity - currentBooked;

        if (bookedTotal > capacity || bookedTotal + currentBooked > capacity) {
            throw new BookedTourException("Xin lỗi quý khách, số chỗ hiện tại chỉ còn " + canBooked );
        }

        TourDTO tourUpdate = tourService.updateTourBooked(tour.getId(), bookedTotal + currentBooked);
        RoomDTO roomDTO = roomService.findById(bookedTour.getRoom().getId());
        BookedTour bookedTourDB = bookedTourRepository.save(bookedTour);
        BookedTourDTO bookedTourDTO = new BookedTourDTO(
            bookedTourDB.getId(),
            bookedTourDB.getBookedAdult(),
            bookedTourDB.getBookedChild(),
            bookedTourDB.getBookedBaby(),
            bookedTourDB.getFullname(),
            bookedTourDB.getEmail(),
            bookedTourDB.getAddress(),
            bookedTour.getNote(),
            bookedTour.getNumRoom(),
            bookedTourDB.getPhone(),
            bookedTourDB.getIsCheckout(),
            bookedTourDB.getDateOfBooking(),
            tourUpdate,
            roomDTO
        );

        return bookedTourDTO;
    }

    public List<BookedTourDTO> findAll() {
        List<BookedTour> bookedTours = bookedTourRepository.findAll();
        List<BookedTourDTO> bookedTourDTOs = new ArrayList<>();
        for (BookedTour bookedTour: bookedTours) {
            TourDTO tourDTO = tourService.findById(bookedTour.getTour().getId());
            RoomDTO roomDTO = roomService.findById(bookedTour.getRoom().getId());

             BookedTourDTO bookedTourDTO = new BookedTourDTO(
                bookedTour.getId(),
                bookedTour.getBookedAdult(),
                bookedTour.getBookedChild(),
                bookedTour.getBookedBaby(),
                bookedTour.getFullname(),
                bookedTour.getEmail(),
                bookedTour.getAddress(),
                bookedTour.getNote(),
                bookedTour.getNumRoom(),
                bookedTour.getPhone(),
                bookedTour.getIsCheckout(),
                bookedTour.getDateOfBooking(),
                tourDTO,
                roomDTO
            );
            
            bookedTourDTOs.add(bookedTourDTO);
        }
        return bookedTourDTOs;
    }

    @Transactional
    public BookedTourDTO updateBookedTourCheckout(String bookedtourId,  boolean isCheckout) {
        BookedTour bookedTour = bookedTourRepository.findById(bookedtourId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy booked tour"));

        bookedTour.setIsCheckout(isCheckout);
        BookedTour bookedTourDB = bookedTourRepository.save(bookedTour);

        TourDTO tourDTO = tourService.findById(bookedTourDB.getTour().getId());
        RoomDTO roomDTO = roomService.findById(bookedTour.getRoom().getId());

        BookedTourDTO bookedTourDTO = new BookedTourDTO(
            bookedTourDB.getId(),
            bookedTour.getBookedAdult(),
            bookedTour.getBookedChild(),
            bookedTour.getBookedBaby(),
            bookedTour.getFullname(),
            bookedTour.getEmail(),
            bookedTour.getAddress(),
            bookedTour.getNote(),
            bookedTour.getNumRoom(),
            bookedTourDB.getPhone(),
            bookedTourDB.getIsCheckout(),
            bookedTourDB.getDateOfBooking(),
            tourDTO,
            roomDTO
        );

        return bookedTourDTO;
    }

    // public List<BookedTourDTO> deleteById(String id, String touristId) {
    //     Optional<BookedTour> bookedTour = bookedTourRepository.findById(id);
    //     if (!bookedTour.isPresent()) {
    //         throw new NotFoundException("Không tìm thấy tour đã đặt");
    //     }
    //     Optional<Payment> payment = paymentRepository.findByBookedTourId(id);
    //     if (payment.isPresent()) {
    //         throw new BookedTourException("Tour này đã được thanh toán");
    //     }

    //     TourDTO tourDB = tourService.findById(bookedTour.get().getTour().getId());
    //     Integer bookedAdult = bookedTour.get().getBookedAdult();
    //     Integer bookedChild = bookedTour.get().getBookedChild();
    //     Integer bookedBaby = bookedTour.get().getBookedBaby();
    //     Integer bookedTotal = bookedAdult + bookedChild + bookedBaby;

    //     Integer new_booked = tourDB.getBooked() - bookedTotal;

    //     tourService.updateTourBooked(bookedTour.get().getTour().getId(), new_booked);
    //     bookedTourRepository.deleteById(id);
    //     return findAllByTouristId(touristId);
    // }
}
