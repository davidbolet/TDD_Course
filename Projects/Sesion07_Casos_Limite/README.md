# Edge Cases & Transactional

## 1. Importance of Testing Edge Cases

Edge cases are unusual or extreme inputs that might cause unexpected behavior. Testing edge cases helps ensure the application handles invalid or unexpected data safely.

## 2. Common Edge Cases

Examples include:\
\- Null or empty values.\
\- Very large or very small numbers.\
\- Special characters or unexpected input formats.\
\- Boundary conditions (e.g., max/min allowed values).

## 3. Validating Inputs

Validate inputs in controllers or service methods. Use annotations like \`@Valid\` and constraints like \`@NotNull\`, \`@Size\` to enforce validation rules.

## 4. Writing Tests for Invalid Inputs

Write tests to check how the system responds to invalid data:\
\- Send requests with missing fields.\
\- Use values that violate constraints.\
\- Verify the response status code is 400 Bad Request.\
\- Verify the error message is clear and useful.

## 5. Transactional Tests

Transactional tests ensure that database changes are rolled back after each test. This keeps the test database clean and repeatable.\
\- Use \`@Transactional\` on test classes or methods.\
\- Combine with \`@Rollback\` if needed.\
\- \`@DataJpaTest\` includes rollback by default.

## 6. Example: Test with Rollback

Example scenario:\
\`\`\`java\
@SpringBootTest\
@Transactional\
class UserServiceTransactionalTest {\
\
&#x20; @Autowired UserService service;\
\
&#x20; @Test\
&#x20; void shouldRollbackTransaction() {\
&#x20;   User user = new User("EdgeCase");\
&#x20;   service.save(user);\
&#x20;   // After test, data will be rolled back automatically\
&#x20; }\
}\
\`\`\`

## 7. Combining Edge Case and Transactional Tests

Combine input validation tests with transactional behavior:\
\- Test that invalid data does not persist to the database.\
\- Verify that if an exception occurs, the transaction is rolled back.

## 8. Tips for Reliable Tests

\- Cover typical cases and edge cases.\
\- Use descriptive test names: \`shouldReturnBadRequestWhenInputIsInvalid\`.\
\- Keep tests isolated to avoid side effects.

## 9. Key Takeaways

\- Edge cases help find hidden bugs.\
\- Transactional tests keep the database clean.\
\- Validate inputs properly to prevent invalid data.\
\- Rollbacks ensure tests are repeatable and safe.
