package com.crossover.techtrial.controller;

import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.RideRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.StringWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RideControllerTest {

    private ObjectMapper objectMapper;

    MockMvc mockMvc;

    @Mock
    private RideController rideController;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    RideRepository rideRepository;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testRideShouldBeCreated() throws Exception {

        Person driver1 = new Person();
        driver1.setEmail("test10000000000007@gmail.com");
        driver1.setName("test 7");
        driver1.setRegistrationNumber("47DCT");

        StringWriter stringWriterDriver1 = new StringWriter();
        objectMapper.writeValue(stringWriterDriver1, driver1);

        HttpEntity<Object> driver1Obj = getHttpEntity(driver1);

        ResponseEntity<Person> testDriver1Obj = template.postForEntity(
                "/api/person", driver1Obj, Person.class);

        Person rider1 = new Person();
        rider1.setEmail("test10000000000008@gmail.com");
        rider1.setName("test 8");
        rider1.setRegistrationNumber("48DCT");

        StringWriter stringWriterRider1 = new StringWriter();
        objectMapper.writeValue(stringWriterRider1, rider1);

        HttpEntity<Object> rider1Obj = getHttpEntity(rider1);

        ResponseEntity<Person> testRider1Obj = template.postForEntity(
                "/api/person", rider1Obj, Person.class);

        Ride ride = new Ride();
        ride.setDriver(testDriver1Obj.getBody());
        ride.setRider(testRider1Obj.getBody());
        ride.setDistance(10L);
        ride.setStartTime("2018-11-07 09:30");
        ride.setEndTime("2018-11-07 09:40");

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, ride);

        HttpEntity<Object> rideObj = getHttpEntity(ride);

        ResponseEntity<Ride> testRide = template.postForEntity("/api/ride", rideObj, Ride.class);
        Assert.assertNotNull(testRide.getBody().getId());
        Assert.assertEquals(200,testDriver1Obj.getStatusCode().value());
        Assert.assertEquals(200,testRider1Obj.getStatusCode().value());
        Assert.assertEquals(200,testRide.getStatusCode().value());
    }

    @Test
    public void testRideShouldBeFound() throws Exception {

        Person driver1 = new Person();
        driver1.setEmail("test10000000000009@gmail.com");
        driver1.setName("test 9");
        driver1.setRegistrationNumber("49DCT");

        StringWriter stringWriterDriver1 = new StringWriter();
        objectMapper.writeValue(stringWriterDriver1, driver1);

        HttpEntity<Object> driver1Obj = getHttpEntity(driver1);

        ResponseEntity<Person> testDriver1Obj = template.postForEntity(
                "/api/person", driver1Obj, Person.class);

        Person rider1 = new Person();
        rider1.setEmail("test10000000000010@gmail.com");
        rider1.setName("test 10");
        rider1.setRegistrationNumber("50DCT");

        StringWriter stringWriterRider1 = new StringWriter();
        objectMapper.writeValue(stringWriterRider1, rider1);

        HttpEntity<Object> rider1Obj = getHttpEntity(rider1);

        ResponseEntity<Person> testRider1Obj = template.postForEntity(
                "/api/person", rider1Obj, Person.class);

        Ride ride = new Ride();
        ride.setDriver(testDriver1Obj.getBody());
        ride.setRider(testRider1Obj.getBody());
        ride.setDistance(10L);
        ride.setStartTime("2018-11-07 09:30");
        ride.setEndTime("2018-11-07 09:40");

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, ride);

        HttpEntity<Object> rideObj = getHttpEntity(ride);

        ResponseEntity<Ride> testRide = template.postForEntity("/api/ride", rideObj, Ride.class);

        String url = "/api/ride/" + testRide.getBody().getId();

        ResponseEntity<Ride> response = template.getForEntity(url , Ride.class);

        Assert.assertNotNull(response.getBody().getId());
        Assert.assertEquals(response.getBody().getId(), testRide.getBody().getId());
        Assert.assertEquals(response.getBody(), testRide.getBody());
        Assert.assertEquals(200,testDriver1Obj.getStatusCode().value());
        Assert.assertEquals(200,testRider1Obj.getStatusCode().value());
        Assert.assertEquals(200,testRide.getStatusCode().value());
    }

    @Test
    public void testRideShouldNotBeFound() throws Exception {
        String url = "/api/ride/0";
        ResponseEntity<Ride> response = template.getForEntity(url , Ride.class);
        Assert.assertNull(response.getBody());
        Assert.assertEquals(404,response.getStatusCode().value());
    }

    @Test
    public void testPanelShouldBeRegistered() throws Exception {

        Person driver1 = new Person();
        driver1.setEmail("test10000000000011@gmail.com");
        driver1.setName("test 11");
        driver1.setRegistrationNumber("51DCT");

        StringWriter stringWriterDriver1 = new StringWriter();
        objectMapper.writeValue(stringWriterDriver1, driver1);

        HttpEntity<Object> driver1Obj = getHttpEntity(driver1);

        ResponseEntity<Person> testDriver1Obj = template.postForEntity(
                "/api/person", driver1Obj, Person.class);

        Person rider1 = new Person();
        rider1.setEmail("test10000000000012@gmail.com");
        rider1.setName("test 12");
        rider1.setRegistrationNumber("52DCT");

        StringWriter stringWriterRider1 = new StringWriter();
        objectMapper.writeValue(stringWriterRider1, rider1);

        HttpEntity<Object> rider1Obj = getHttpEntity(rider1);

        ResponseEntity<Person> testRider1Obj = template.postForEntity(
                "/api/person", rider1Obj, Person.class);

        Ride ride = new Ride();
        ride.setDriver(testDriver1Obj.getBody());
        ride.setRider(testRider1Obj.getBody());
        ride.setDistance(10L);
        ride.setStartTime("2018-11-07 09:30");
        ride.setEndTime("2018-11-07 09:40");

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, ride);

        HttpEntity<Object> rideObj = getHttpEntity(ride);

        ResponseEntity<Ride> testRide = template.postForEntity("/api/ride", rideObj, Ride.class);

        //Delete this rdie
        rideRepository.deleteById(testRide.getBody().getId());
        Assert.assertEquals("test 11", testRide.getBody().getDriver().getName());
        Assert.assertEquals("test 12", testRide.getBody().getRider().getName());
        Assert.assertEquals(200,testDriver1Obj.getStatusCode().value());
        Assert.assertEquals(200,testRider1Obj.getStatusCode().value());
        Assert.assertEquals(200,testRide.getStatusCode().value());
    }

    @Test
    public void testTopDriverShouldBeListed() throws Exception {

        Person driver1 = new Person();
        driver1.setEmail("test10000000000013@gmail.com");
        driver1.setName("driver 13");
        driver1.setRegistrationNumber("53DCT");

        StringWriter stringWriterDriver1 = new StringWriter();
        objectMapper.writeValue(stringWriterDriver1, driver1);

        HttpEntity<Object> driver1Obj = getHttpEntity(driver1);

        ResponseEntity<Person> testDriver1Obj = template.postForEntity(
                "/api/person", driver1Obj, Person.class);

        Person driver2 = new Person();
        driver2.setEmail("test10000000000014@gmail.com");
        driver2.setName("driver 14");
        driver2.setRegistrationNumber("54DCT");

        StringWriter stringWriterDriver2 = new StringWriter();
        objectMapper.writeValue(stringWriterDriver2, driver2);

        HttpEntity<Object> driver2Obj = getHttpEntity(driver2);

        ResponseEntity<Person> testDriver2Obj = template.postForEntity(
                "/api/person", driver2Obj, Person.class);

        Person driver3 = new Person();
        driver3.setEmail("test10000000000015@gmail.com");
        driver3.setName("driver 15");
        driver3.setRegistrationNumber("55DCT");

        StringWriter stringWriterDriver3 = new StringWriter();
        objectMapper.writeValue(stringWriterDriver3, driver3);

        HttpEntity<Object> driver3Obj = getHttpEntity(driver3);

        ResponseEntity<Person> testDriver3Obj = template.postForEntity(
                "/api/person", driver3Obj, Person.class);

        Person driver4 = new Person();
        driver4.setEmail("test10000000000016@gmail.com");
        driver4.setName("driver 16");
        driver4.setRegistrationNumber("56DCT");

        StringWriter stringWriterDriver4 = new StringWriter();
        objectMapper.writeValue(stringWriterDriver4, driver4);

        HttpEntity<Object> driver4Obj = getHttpEntity(driver4);

        ResponseEntity<Person> testDriver4Obj = template.postForEntity(
                "/api/person", driver4Obj, Person.class);

        Person driver5 = new Person();
        driver5.setEmail("test10000000000017@gmail.com");
        driver5.setName("driver 17");
        driver5.setRegistrationNumber("57DCT");

        StringWriter stringWriterDriver5 = new StringWriter();
        objectMapper.writeValue(stringWriterDriver5, driver5);

        HttpEntity<Object> driver5Obj = getHttpEntity(driver5);

        ResponseEntity<Person> testDriver5Obj = template.postForEntity(
                "/api/person", driver5Obj, Person.class);

        Person driver6 = new Person();
        driver6.setEmail("test10000000000018@gmail.com");
        driver6.setName("driver 18");
        driver6.setRegistrationNumber("58DCT");

        StringWriter stringWriterDriver6 = new StringWriter();
        objectMapper.writeValue(stringWriterDriver6, driver6);

        HttpEntity<Object> driver6Obj = getHttpEntity(driver6);

        ResponseEntity<Person> testDriver6Obj = template.postForEntity(
                "/api/person", driver6Obj, Person.class);

        Person rider1 = new Person();
        rider1.setEmail("test10000000000019@gmail.com");
        rider1.setName("rider 19");
        rider1.setRegistrationNumber("59DCT");

        StringWriter stringWriterRider1 = new StringWriter();
        objectMapper.writeValue(stringWriterRider1, rider1);

        HttpEntity<Object> rider1Obj = getHttpEntity(rider1);

        ResponseEntity<Person> testRider1Obj = template.postForEntity(
                "/api/person", rider1Obj, Person.class);

        Ride ride1 = new Ride();
        ride1.setDriver(testDriver1Obj.getBody());
        ride1.setRider(testRider1Obj.getBody());
        ride1.setDistance(10L);
        ride1.setStartTime("2018-11-07 09:30");
        ride1.setEndTime("2018-11-07 09:40");

        StringWriter stringWriter1 = new StringWriter();
        objectMapper.writeValue(stringWriter1, ride1);

        HttpEntity<Object> ride1Obj = getHttpEntity(ride1);

        ResponseEntity<Ride> testRide1 = template.postForEntity("/api/ride", ride1Obj, Ride.class);

        Ride ride2 = new Ride();
        ride2.setDriver(testDriver1Obj.getBody());
        ride2.setRider(testRider1Obj.getBody());
        ride2.setDistance(5L);
        ride2.setStartTime("2018-11-07 10:30");
        ride2.setEndTime("2018-11-07 10:40");

        StringWriter stringWriter2 = new StringWriter();
        objectMapper.writeValue(stringWriter2, ride2);

        HttpEntity<Object> ride2Obj = getHttpEntity(ride2);

        ResponseEntity<Ride> testRide2 = template.postForEntity("/api/ride", ride2Obj, Ride.class);

        Ride ride3 = new Ride();
        ride3.setDriver(testDriver1Obj.getBody());
        ride3.setRider(testRider1Obj.getBody());
        ride3.setDistance(30L);
        ride3.setStartTime("2018-11-07 10:35");
        ride3.setEndTime("2018-11-07 12:40");

        StringWriter stringWriter3 = new StringWriter();
        objectMapper.writeValue(stringWriter3, ride3);

        HttpEntity<Object> ride3Obj = getHttpEntity(ride3);

        ResponseEntity<Ride> testRide3 = template.postForEntity("/api/ride", ride3Obj, Ride.class);

        Ride ride4 = new Ride();
        ride4.setDriver(testDriver2Obj.getBody());
        ride4.setRider(testRider1Obj.getBody());
        ride4.setDistance(8L);
        ride4.setStartTime("2018-11-07 8:35");
        ride4.setEndTime("2018-11-07 8:55");

        StringWriter stringWriter4 = new StringWriter();
        objectMapper.writeValue(stringWriter4, ride4);

        HttpEntity<Object> ride4Obj = getHttpEntity(ride4);

        ResponseEntity<Ride> testRide4 = template.postForEntity("/api/ride", ride4Obj, Ride.class);

        Ride ride5 = new Ride();
        ride5.setDriver(testDriver2Obj.getBody());
        ride5.setRider(testRider1Obj.getBody());
        ride5.setDistance(15L);
        ride5.setStartTime("2018-11-07 9:35");
        ride5.setEndTime("2018-11-07 10:55");

        StringWriter stringWriter5 = new StringWriter();
        objectMapper.writeValue(stringWriter5, ride5);

        HttpEntity<Object> ride5Obj = getHttpEntity(ride5);

        ResponseEntity<Ride> testRide5 = template.postForEntity("/api/ride", ride5Obj, Ride.class);

        Ride ride6 = new Ride();
        ride6.setDriver(testDriver3Obj.getBody());
        ride6.setRider(testRider1Obj.getBody());
        ride6.setDistance(2L);
        ride6.setStartTime("2018-11-07 8:35");
        ride6.setEndTime("2018-11-07 8:55");

        StringWriter stringWriter6 = new StringWriter();
        objectMapper.writeValue(stringWriter6, ride6);

        HttpEntity<Object> ride6Obj = getHttpEntity(ride6);

        ResponseEntity<Ride> testRide6 = template.postForEntity("/api/ride", ride6Obj, Ride.class);

        Ride ride7 = new Ride();
        ride7.setDriver(testDriver3Obj.getBody());
        ride7.setRider(testRider1Obj.getBody());
        ride7.setDistance(15L);
        ride7.setStartTime("2018-11-07 9:35");
        ride7.setEndTime("2018-11-07 10:55");

        StringWriter stringWriter7 = new StringWriter();
        objectMapper.writeValue(stringWriter7, ride7);

        HttpEntity<Object> ride7Obj = getHttpEntity(ride7);

        ResponseEntity<Ride> testRide7 = template.postForEntity("/api/ride", ride7Obj, Ride.class);

        Ride ride8 = new Ride();
        ride8.setDriver(testDriver3Obj.getBody());
        ride8.setRider(testRider1Obj.getBody());
        ride8.setDistance(5L);
        ride8.setStartTime("2018-11-07 10:35");
        ride8.setEndTime("2018-11-07 11:55");

        StringWriter stringWriter8 = new StringWriter();
        objectMapper.writeValue(stringWriter8, ride8);

        HttpEntity<Object> ride8Obj = getHttpEntity(ride8);

        ResponseEntity<Ride> testRide8 = template.postForEntity("/api/ride", ride8Obj, Ride.class);

        Ride ride9 = new Ride();
        ride9.setDriver(testDriver3Obj.getBody());
        ride9.setRider(testRider1Obj.getBody());
        ride9.setDistance(20L);
        ride9.setStartTime("2018-11-07 11:00");
        ride9.setEndTime("2018-11-07 12:55");

        StringWriter stringWriter9 = new StringWriter();
        objectMapper.writeValue(stringWriter9, ride9);

        HttpEntity<Object> ride9Obj = getHttpEntity(ride9);

        ResponseEntity<Ride> testRide9 = template.postForEntity("/api/ride", ride9Obj, Ride.class);

        Ride ride10 = new Ride();
        ride10.setDriver(testDriver4Obj.getBody());
        ride10.setRider(testRider1Obj.getBody());
        ride10.setDistance(3L);
        ride10.setStartTime("2018-11-07 9:00");
        ride10.setEndTime("2018-11-07 9:15");

        StringWriter stringWriter10 = new StringWriter();
        objectMapper.writeValue(stringWriter10, ride10);

        HttpEntity<Object> ride10Obj = getHttpEntity(ride10);

        ResponseEntity<Ride> testRide10 = template.postForEntity("/api/ride", ride10Obj, Ride.class);

        Ride ride11 = new Ride();
        ride11.setDriver(testDriver4Obj.getBody());
        ride11.setRider(testRider1Obj.getBody());
        ride11.setDistance(10L);
        ride11.setStartTime("2018-11-07 10:00");
        ride11.setEndTime("2018-11-07 11:00");

        StringWriter stringWriter11 = new StringWriter();
        objectMapper.writeValue(stringWriter11, ride11);

        HttpEntity<Object> ride11Obj = getHttpEntity(ride11);

        ResponseEntity<Ride> testRide11 = template.postForEntity("/api/ride", ride11Obj, Ride.class);

        Ride ride12 = new Ride();
        ride12.setDriver(testDriver5Obj.getBody());
        ride12.setRider(testRider1Obj.getBody());
        ride12.setDistance(6L);
        ride12.setStartTime("2018-11-07 9:00");
        ride12.setEndTime("2018-11-07 9:20");

        StringWriter stringWriter12 = new StringWriter();
        objectMapper.writeValue(stringWriter12, ride12);

        HttpEntity<Object> ride12Obj = getHttpEntity(ride12);

        ResponseEntity<Ride> testRide12 = template.postForEntity("/api/ride", ride12Obj, Ride.class);

        Ride ride13 = new Ride();
        ride13.setDriver(testDriver5Obj.getBody());
        ride13.setRider(testRider1Obj.getBody());
        ride13.setDistance(10L);
        ride13.setStartTime("2018-11-07 10:00");
        ride13.setEndTime("2018-11-07 11:00");

        StringWriter stringWriter13 = new StringWriter();
        objectMapper.writeValue(stringWriter13, ride13);

        HttpEntity<Object> ride13Obj = getHttpEntity(ride13);

        ResponseEntity<Ride> testRide13 = template.postForEntity("/api/ride", ride13Obj, Ride.class);

        Ride ride14 = new Ride();
        ride14.setDriver(testDriver6Obj.getBody());
        ride14.setRider(testRider1Obj.getBody());
        ride14.setDistance(10L);
        ride14.setStartTime("2018-11-07 10:00");
        ride14.setEndTime("2018-11-07 11:00");

        StringWriter stringWriter14 = new StringWriter();
        objectMapper.writeValue(stringWriter14, ride14);

        HttpEntity<Object> ride14Obj = getHttpEntity(ride14);

        ResponseEntity<Ride> testRide14 = template.postForEntity("/api/ride", ride14Obj, Ride.class);

        ResponseEntity<List<TopDriverDTO>> response = template.exchange("/api/top-rides?max=2&startTime=2018-11-07T09:30:00&endTime=2018-11-07T10:30:59",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TopDriverDTO>>() {});

        assertThat(response.getBody().size() > 0);
        Assert.assertEquals(200,testDriver1Obj.getStatusCode().value());
        Assert.assertEquals(200,testDriver2Obj.getStatusCode().value());
        Assert.assertEquals(200,testDriver3Obj.getStatusCode().value());
        Assert.assertEquals(200,testDriver4Obj.getStatusCode().value());
        Assert.assertEquals(200,testDriver5Obj.getStatusCode().value());
        Assert.assertEquals(200,testDriver6Obj.getStatusCode().value());
        Assert.assertEquals(200,testRider1Obj.getStatusCode().value());
        Assert.assertEquals(200,testRide1.getStatusCode().value());
        Assert.assertEquals(200,testRide2.getStatusCode().value());
        Assert.assertEquals(200,testRide3.getStatusCode().value());
        Assert.assertEquals(200,testRide4.getStatusCode().value());
        Assert.assertEquals(200,testRide5.getStatusCode().value());
        Assert.assertEquals(200,testRide6.getStatusCode().value());
        Assert.assertEquals(200,testRide7.getStatusCode().value());
        Assert.assertEquals(200,testRide8.getStatusCode().value());
        Assert.assertEquals(200,testRide9.getStatusCode().value());
        Assert.assertEquals(200,testRide10.getStatusCode().value());
        Assert.assertEquals(200,testRide11.getStatusCode().value());
        Assert.assertEquals(200,testRide12.getStatusCode().value());
        Assert.assertEquals(200,testRide13.getStatusCode().value());
        Assert.assertEquals(200,testRide14.getStatusCode().value());

    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }

}