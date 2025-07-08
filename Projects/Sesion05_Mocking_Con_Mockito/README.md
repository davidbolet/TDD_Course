# Mocking with Mockito

## 1. Purpose of Mocking

Mocking allows you to isolate the unit under test by replacing real dependencies with fake ones. This makes tests faster, more reliable, and easier to maintain.

## 2. What is Mockito?

Mockito is the most popular mocking framework in the Java ecosystem. It provides annotations and methods to create, configure, and verify mocks.

## 3. Common Annotations

\- \`@Mock\`: Creates a mock instance.\
\- \`@InjectMocks\`: Injects mock dependencies into the class under test.\
\- \`@BeforeEach\`: Initializes mocks before each test with \`MockitoAnnotations.openMocks(this);\`.

## 4. Stubbing Behavior

Stubbing means defining what a mock should return when a method is called.\
Example:\
\`\`\`java\
when(repository.findById(1L)).thenReturn(Optional.of(user));\
\`\`\`

## 5. Verifying Interactions

Mockito allows you to verify that certain methods were called with specific arguments.\
Example:\
\`\`\`java\
verify(repository).save(user);\
verifyNoMoreInteractions(repository);\
\`\`\`

## 6. Argument Matchers

Use matchers to check calls with flexible arguments.\
Example:\
\`\`\`java\
when(service.findByName(anyString())).thenReturn(user);\
verify(service).findByName(eq("Alice"));\
\`\`\`

## 7. Example: Service with Mocked Repository

Typical scenario:\
\- A \`UserService\` depends on \`UserRepository\`.\
\- In the test, \`UserRepository\` is mocked.\
\- Arrange: Define mock behavior.\
\- Act: Call the service method.\
\- Assert: Verify output and interactions.\
Example:\
\`\`\`java\
@Mock UserRepository repository;\
@InjectMocks UserService service;\
\
@Test\
void shouldSaveUser() {\
&#x20;   User user = new User("Alice");\
&#x20;   service.saveUser(user);\
&#x20;   verify(repository).save(user);\
}\
\`\`\`

## 8. Best Practices for Mocking

\- Mock only what you own: don’t mock third-party APIs unnecessarily.\
\- Avoid over-mocking: some classes are better tested with real dependencies.\
\- Use constructor injection to make mocking easier.\
\- Keep tests readable and focused on behavior.

## 9. Key Takeaways

\- Mockito makes it easy to isolate units.\
\- Use \`@Mock\`, \`@InjectMocks\`, and verify behavior.\
\- Stubbing defines expected outputs for given inputs.\
\- Verifications check that the right interactions happen.
