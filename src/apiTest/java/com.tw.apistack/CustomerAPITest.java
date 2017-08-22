package com.tw.apistack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.tw.apistack.config.Constants;
import com.tw.apistack.core.address.model.Address;
import com.tw.apistack.core.customer.model.Customer;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;

/**
 * Created by rsma on 22/08/2017.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles(Constants.SPRING_PROFILE_TEST)
@SpringBootTest(classes = ApiStackApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerAPITest {
    private static final String API_PATH = "/api/customers";
    @Value("${local.server.port}")
    private int port;

    @Before
    public void setup() {
        RestAssured.port = this.port;
        RestAssured.baseURI = "http://localhost";

    }

    //getAll
    @Test
    public void should_get_status_200_when_call_customers_get_all() throws Exception {
        validator_customer_size_and_status_is_200_when_call_customers(2);
    }

    //getOne-success
    @Test
    public void should_get_status_200_when_call_customers_with_path_0_get_one_success() throws Exception {
        validator_customer_and_status_is_200_When_call_customers("firstname0","lastname0", 0L);
    }

    //getOne-fail
    @Test
    public void should_get_status_404_when_call_customers_with_path_3_get_one_fail() throws Exception {
        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get(API_PATH + "/3")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    //create-success
    @Test
    public void should_get_status_201_when_call_customers_create_success() throws Exception {
        Customer customer = new Customer("firstName","lastName");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(customer);
        System.out.println(json);

        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(customer)
                .when()
                .post(API_PATH)
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        validator_customer_size_and_status_is_200_when_call_customers(3);
    }

    //create-fail
    @Test
    public void should_get_status_400_when_call_customers_create_fail() throws Exception {
        Customer customer = new Customer("firstName--overLength","lastName-----overLength");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(customer);
        System.out.println(json);

        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(customer)
                .when()
                .post(API_PATH)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

        validator_customer_size_and_status_is_200_when_call_customers(2);
    }

    //updateOne-success
    @Test
    public void should_get_status_200_when_call_customers_with_path_0_update_one_success() throws Exception {
        Customer customer = new Customer("firstName-put","lastName-put");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(customer);
        System.out.println(json);

        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(customer)
                .when()
                .put(API_PATH + "/0")
                .then()
                .statusCode(HttpStatus.SC_OK);

        validator_customer_and_status_is_200_When_call_customers("firstName-put","lastName-put", 0L);
    }

    //updateOne-fail
    @Test
    public void should_get_status_404_when_call_customers_with_path_3_update_one_fail() throws Exception {
        Customer customer = new Customer("firstName-put","lastName-put");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(customer);
        System.out.println(json);

        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(customer)
                .when()
                .put(API_PATH + "/3")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    //deleteOne-success
    @Test
    public void should_get_status_200_when_call_customers_with_path_0_delete_one_success() throws Exception {

        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete(API_PATH + "/0")
                .then()
                .statusCode(HttpStatus.SC_OK);

        validator_customer_size_and_status_is_200_when_call_customers(1);
    }

    //deleteOne-fail
    @Test
    public void should_get_status_404_when_call_customers_with_path_3_delete_one_fail() throws Exception {

        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete(API_PATH + "/3")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);

        validator_customer_size_and_status_is_200_when_call_customers(2);
    }

    //---------------------------------------------------------------------------------------------------------
    //Address

    //getAllByCustomerId
    @Test
    public void should_get_status_200_when_call_addresses_with_path_0_get_all() throws Exception {
        validator_addresses_size_and_status_is_200_when_call_addresses_with_path_customer_id(0L,2);
    }

    //getOneByAddressIdAndCustomerId-success
    @Test
    public void should_get_status_200_when_call_addresses_with_path_0_and_0_get_One() throws Exception {
        validator_address_and_status_is_200_When_call_addresses_with_path_customer_id_and_address_id("chengdu",0L,0L);
    }

    //getOneByAddressIdAndCustomerId-fail
    @Test
    public void should_get_status_404_when_call_addresses_with_path_0_and_2_get_One() throws Exception {
        RestAssured.
                given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get(API_PATH + "/" + 0 + "/addresses" + "/" + 2)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    //updateOneByAddressIdAndCustomerId-success
    @Test
    public void should_get_status_200_when_call_addresses_with_path_0_and_0_update_one_success() throws Exception {
        Address address = new Address("chengdu-put");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(address);
        System.out.println(json);

        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(address)
                .when()
                .put(API_PATH + "/" + 0 + "/addresses" + "/" + 0)
                .then()
                .statusCode(HttpStatus.SC_OK);
        validator_address_and_status_is_200_When_call_addresses_with_path_customer_id_and_address_id("chengdu-put",0L,0L);
    }

    //updateOneByAddressIdAndCustomerId-fail
    @Test
    public void should_get_status_404_when_call_addresses_with_path_0_and_2_update_one_fail() throws Exception {
        Address address = new Address("chengdu-put");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(address);
        System.out.println(json);

        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(address)
                .when()
                .put(API_PATH + "/" + 0 + "/addresses" + "/" + 2)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    //postOneByAddressIdAndCustomerId-success
    @Test
    public void should_get_status_201_when_call_addresses_with_path_0_and_0_create_one_success() throws Exception {
        Address address = new Address("chengdu-create");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(address);
        System.out.println(json);

        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(address)
                .when()
                .post(API_PATH + "/" + 0 + "/addresses")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
        validator_addresses_size_and_status_is_200_when_call_addresses_with_path_customer_id(0L,3);
        validator_address_and_status_is_200_When_call_addresses_with_path_customer_id_and_address_id("chengdu-create",0L,3L);
    }

    //deleteOneByAddressIdAndCustomerId-success
    @Test
    public void should_get_status_200_when_call_addresses_with_path_0_and_2_delete_One() throws Exception {
        RestAssured.
                given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .delete(API_PATH + "/" + 0 + "/addresses" + "/" + 0)
                .then()
                .statusCode(HttpStatus.SC_OK);
        validator_addresses_size_and_status_is_200_when_call_addresses_with_path_customer_id(0L,1);
    }


    private void validator_customer_and_status_is_200_When_call_customers(String firstName, String lastName, Long id) throws Exception {
        RestAssured.
                given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get(API_PATH + "/" +id)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("content.firstName", is(firstName))
                .body("content.lastName", is(lastName))
                .body("_links.addresses.href", is("http://localhost:" + port + "/api/customers/" + id + "/addresses"));
    }

    private void validator_customer_size_and_status_is_200_when_call_customers(int size){

        RestAssured.
                given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get(API_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("size()", is(size));
    }

    private void validator_addresses_size_and_status_is_200_when_call_addresses_with_path_customer_id(Long customerId,int size) throws Exception {

        RestAssured.
                given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get(API_PATH + "/" + customerId + "/addresses")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("size()", is(size));
    }

    private void validator_address_and_status_is_200_When_call_addresses_with_path_customer_id_and_address_id(String city, Long customerId, Long addressId) throws Exception {
        RestAssured.
                given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get(API_PATH + "/" + customerId + "/addresses" + "/" + addressId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("content.city", is(city));
    }

}
