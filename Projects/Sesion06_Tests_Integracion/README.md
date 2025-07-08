# Integration and Data Tests

## 1. Purpose of Integration Tests

Integration tests verify that multiple components work together correctly. They test interactions between layers: controllers, services, repositories, and the database.

## 2. When to Write Integration Tests

Use integration tests for scenarios where:\
\- You need to check real database operations.\
\- You want to ensure that Spring context wiring is correct.\
\- You test data consistency and transactions.

## 3. Using @SpringBootTest

\`@SpringBootTest\` loads the full application context for end-to-end testing. It can start the web server and database connection if needed.\
Best for verifying full workflows.

## 4. Using @DataJpaTest

\`@DataJpaTest\` focuses on JPA components (repositories) only. It configures an in-memory database (H2) by default and rolls back transactions after each test.\
Useful for verifying queries and persistence logic.

## 5. Example: Service + Repository Integration

Example scenario:\
\- \`UserService\` calls \`UserRepository\` to save or fetch data.\
\- Use real \`UserRepository\` with an embedded database.\
\- Arrange: Insert test data.\
\- Act: Call service method.\
\- Assert: Verify data is stored or retrieved as expected.\
Example:\
\`\`\`java\
@SpringBootTest\
class UserServiceIT {\
\
&#x20; @Autowired UserService service;\
&#x20; @Autowired UserRepository repository;\
\
&#x20; @Test\
&#x20; void shouldSaveAndFindUser() {\
&#x20;   User user = new User("Alice");\
&#x20;   service.save(user);\
&#x20;   User found = repository.findByName("Alice");\
&#x20;   assertNotNull(found);\
&#x20; }\
}\
\`\`\`

## 6. Using Embedded Databases

Spring Boot supports embedded databases like H2 for fast local tests. This avoids the need for an external database during testing.\
Use H2 or Postgres containers for realistic tests.

## 7. Transaction Rollback

By default, tests with \`@DataJpaTest\` roll back transactions after each test. This keeps your test database clean and repeatable.\
Use \`@Transactional\` with \`@SpringBootTest\` for similar behavior.

## 8. Tips for Reliable Integration Tests

\- Keep integration tests fast by limiting data volume.\
\- Use clear data setup and teardown.\
\- Separate unit and integration tests in different folders.\
\- Run integration tests in CI pipelines for confidence.

## 9. Key Takeaways

\- Integration tests check real interactions between layers.\
\- \`@SpringBootTest\` loads full context; \`@DataJpaTest\` focuses on JPA.\
\- Use embedded databases for isolation.\
\- Transactions and rollbacks keep tests clean and reliable.
