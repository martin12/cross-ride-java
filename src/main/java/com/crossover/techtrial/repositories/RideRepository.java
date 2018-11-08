/**
 * 
 */
package com.crossover.techtrial.repositories;

import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Ride;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * @author crossover
 *
 */
@RestResource(exported = false)
public interface RideRepository extends CrudRepository<Ride, Long> {

    /**
     * Finds top drivers with a maximum duration of their shared rides and show average distance covered during that time.
     * @param startDate
     * @param endDate
     * @return  A list of drivers (Top Driver DTO) with a maximum duration of their shared rides and show average distance covered during that time.
     *          If no top drivers is found, this method returns an empty list.
     */
    @Query("SELECT new com.crossover.techtrial.dto.TopDriverDTO(" +
            "   p.name, " +
            "   p.email, " +
            "   SUM(TIME_TO_SEC(TIMEDIFF(r.endTime, r.startTime))), " +
            "   MAX(TIME_TO_SEC(TIMEDIFF(r.endTime, r.startTime))), " +
            "   AVG(r.distance)" +
            ") " +
            "FROM Ride r " +
            "INNER JOIN Person p ON r.driver.id = p.id " +
            "WHERE (r.startTime BETWEEN :startDate AND :endDate) OR " +
            "(r.endTime BETWEEN :startDate AND :endDate) " +
            "GROUP BY p.name, p.email " +
            "ORDER BY MAX(TIME_TO_SEC(TIMEDIFF(r.endTime, r.startTime))) DESC")
    List<TopDriverDTO> find(@Param("startDate") String startDate, @Param("endDate") String endDate, Pageable pageable);

}
