/**
 * 
 */
package com.crossover.techtrial.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

/**
 * @author kshah
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

  private ObjectMapper objectMapper;

  MockMvc mockMvc;
  
  @Mock
  private PersonController personController;
  
  @Autowired
  private TestRestTemplate template;
  
  @Autowired
  PersonRepository personRepository;
  
  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    objectMapper = new ObjectMapper();
  }
  
  @Test
  public void testPanelShouldBeRegistered() throws Exception {
    HttpEntity<Object> person = getHttpEntity(
        "{\"name\": \"test 1\", \"email\": \"test10000000000001@gmail.com\"," 
            + " \"registrationNumber\": \"41DCT\",\"registrationDate\":\"2018-08-08T12:12:12\" }");
    ResponseEntity<Person> response = template.postForEntity(
        "/api/person", person, Person.class);
    //Delete this user
    personRepository.deleteById(response.getBody().getId());
    Assert.assertEquals("test 1", response.getBody().getName());
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  @Test
  public void testPanelShouldBeFound() throws Exception {
    HttpEntity<Object> person = getHttpEntity(
            "{\"name\": \"test 200\", \"email\": \"test10000000000200@gmail.com\","
                    + " \"registrationNumber\": \"87DCT\",\"registrationDate\":\"2018-08-08T12:12:12\" }");
    ResponseEntity<Person> response = template.postForEntity(
            "/api/person", person, Person.class);
    //Find this user
    Optional<Person> person1 = personRepository.findById(response.getBody().getId());
    Assert.assertTrue(person1.isPresent());
    Assert.assertEquals(person1.get().getId(), response.getBody().getId());
    Assert.assertEquals(person1.get().getName(), response.getBody().getName());
    Assert.assertEquals(person1.get().getEmail(), response.getBody().getEmail());
    Assert.assertEquals(person1.get().getRegistrationNumber(), response.getBody().getRegistrationNumber());
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  @Test
  public void testPersonShouldBeCreated() throws Exception {

    Person p = new Person();
    p.setEmail("test10000000000002@gmail.com");
    p.setName("test 2");
    p.setRegistrationNumber("42DCT");

    StringWriter stringWriter = new StringWriter();
    objectMapper.writeValue(stringWriter, p);
    HttpEntity<Object> person = getHttpEntity(p);

    ResponseEntity<Person> response = template.postForEntity(
            "/api/person", person, Person.class);
    Assert.assertNotNull(response.getBody().getId());
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  @Test
  public void testPersonShouldNotBeCreated() throws Exception {

    Person p = new Person();
    p.setName("test 100");
    p.setRegistrationNumber("60DCT");

    StringWriter stringWriter = new StringWriter();
    objectMapper.writeValue(stringWriter, p);
    HttpEntity<Object> person = getHttpEntity(p);

    ResponseEntity<Person> response = template.postForEntity(
            "/api/person", person, Person.class);
    Assert.assertNull(response.getBody().getId());
    Assert.assertEquals(400,response.getStatusCode().value());
  }

  @Test
  public void testPersonsShouldBeListed() throws Exception {

    Person p1 = new Person();
    p1.setEmail("test10000000000003@gmail.com");
    p1.setName("test 3");
    p1.setRegistrationNumber("43DCT");

    Person p2 = new Person();
    p2.setEmail("test10000000000004@gmail.com");
    p2.setName("test 4");
    p2.setRegistrationNumber("44DCT");

    Person p3 = new Person();
    p3.setEmail("test10000000000005@gmail.com");
    p3.setName("test 5");
    p3.setRegistrationNumber("45DCT");

    StringWriter stringWriter1 = new StringWriter();
    objectMapper.writeValue(stringWriter1, p1);

    StringWriter stringWriter2 = new StringWriter();
    objectMapper.writeValue(stringWriter2, p2);

    StringWriter stringWriter3 = new StringWriter();
    objectMapper.writeValue(stringWriter3, p3);

    HttpEntity<Object> person1 = getHttpEntity(p1);
    HttpEntity<Object> person2 = getHttpEntity(p2);
    HttpEntity<Object> person3 = getHttpEntity(p3);

    ResponseEntity<Person> response1 = template.postForEntity(
            "/api/person", person1, Person.class);
    ResponseEntity<Person> response2 = template.postForEntity(
            "/api/person", person2, Person.class);
    ResponseEntity<Person> response3 = template.postForEntity(
            "/api/person", person3, Person.class);

    ResponseEntity<List<Person>> response = template.exchange("/api/person",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Person>>() {});

    assertThat(response.getBody().size() > 0);
    Assert.assertEquals(200,response1.getStatusCode().value());
    Assert.assertEquals(200,response2.getStatusCode().value());
    Assert.assertEquals(200,response3.getStatusCode().value());
  }

  @Test
  public void testPersonShouldBeFound() throws Exception {

    Person p = new Person();
    p.setEmail("test10000000000006@gmail.com");
    p.setName("test 6");
    p.setRegistrationNumber("46DCT");

    StringWriter stringWriter = new StringWriter();
    objectMapper.writeValue(stringWriter, p);

    HttpEntity<Object> person = getHttpEntity(p);

    ResponseEntity<Person> testPerson = template.postForEntity(
            "/api/person", person, Person.class);

    String url = "/api/person/" + testPerson.getBody().getId();

    ResponseEntity<Person> response = template.getForEntity(url , Person.class);
    Assert.assertNotNull(response.getBody().getId());
    Assert.assertEquals(response.getBody().getId(), testPerson.getBody().getId());
    Assert.assertEquals(testPerson.getBody(), response.getBody());
    Assert.assertEquals(testPerson.getBody().hashCode(), response.getBody().hashCode());
    Assert.assertEquals(200,testPerson.getStatusCode().value());
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  @Test
  public void testPersonShouldNotBeFound() throws Exception {
    String url = "/api/person/0";
    ResponseEntity<Person> response = template.getForEntity(url , Person.class);
    Assert.assertNull(response.getBody());
    Assert.assertEquals(404,response.getStatusCode().value());
  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }

}
