/**
 * 
 */
package com.crossover.techtrial.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import com.crossover.techtrial.dto.TopDriverDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.RideRepository;

/**
 * @author crossover
 *
 */
@Service
public class RideServiceImpl implements RideService{

  @Autowired
  RideRepository rideRepository;
  
  public Ride save(Ride ride) {
    return rideRepository.save(ride);
  }
  
  public Ride findById(Long rideId) {
    Optional<Ride> optionalRide = rideRepository.findById(rideId);
    if (optionalRide.isPresent()) {
      return optionalRide.get();
    }else return null;
  }

  @Override
  public List<TopDriverDTO> findTopDrivers(Long count, LocalDateTime startTime, LocalDateTime endTime) {

    String formatString = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);

    List<TopDriverDTO> topDrivers = new ArrayList<TopDriverDTO>();

    Date startDate = java.sql.Timestamp.valueOf(startTime);
    Date endDate = java.sql.Timestamp.valueOf(endTime);

    String startDateStr = simpleDateFormat.format(startDate);
    String endDateStr = simpleDateFormat.format(endDate);

    Pageable topCount = PageRequest.of(0, count.intValue());
    topDrivers.addAll(rideRepository.find(startDateStr, endDateStr, topCount));
    return topDrivers;
  }
}
