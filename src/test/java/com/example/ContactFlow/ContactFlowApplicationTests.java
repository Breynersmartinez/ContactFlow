package com.example.ContactFlow;

import com.example.ContactFlow.entity.Contact;
import com.example.ContactFlow.repository.ContactRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Testcontainers
@SpringBootTest
class ContactFlowApplicationTests {

	@Container
	public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
			.withDatabaseName("testdb")
			.withUsername("test")
			.withPassword("test");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Autowired
	private ContactRepository contactRepository;

	@Test
	void shouldSaveContact() {
		Contact contact = new Contact();
		contact.setName("Breiner  Martinez");
		contact.setEmail("breinersmartinezmunoz@gmail.com");

		Contact saved = contactRepository.save(contact);

		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getName()).isEqualTo("Breiner Martinez");
	}

}
