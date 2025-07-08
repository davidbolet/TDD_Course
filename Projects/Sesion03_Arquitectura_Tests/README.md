# REST API Testing

## 1. Purpose of REST API Testing

REST API tests ensure that endpoints respond correctly to HTTP requests. They validate the behavior of controllers, request mappings, status codes, and payloads.

## 2. What to Test in a REST API

Key areas to test:\
\- HTTP status codes (200 OK, 201 Created, 400 Bad Request, etc.)\
\- Request and response payloads.\
\- Validation of input data.\
\- Behavior for GET, POST, PUT, DELETE methods.\
\- Edge cases and error handling.

## 3. Testing with MockMvc

Spring Boot provides \`MockMvc\` for testing controllers without starting the entire server.\
Benefits:\
\- Fast feedback.\
\- Tests run in isolation.\
Typical test flow:\
1\) Build a mock HTTP request.\
2\) Perform the request using \`mockMvc\`.\
3\) Assert the status, headers, and body.

## 4. Example: Testing a GET Endpoint

Example using \`MockMvc\`:\
\`\`\`java\
@Test\
void shouldReturnCustomerById() throws Exception {\
&#x20;   mockMvc.perform(get("/api/customers/1"))\
&#x20;          .andExpect(status().isOk())\
&#x20;          .andExpect(jsonPath("$.id").value(1))\
&#x20;          .andExpect(jsonPath("$.name").value("John"));\
}\
\`\`\`

## 5. Example: Testing POST, PUT, DELETE

POST:\
\`\`\`java\
mockMvc.perform(post("/api/customers")\
&#x20;  .contentType(MediaType.APPLICATION\_JSON)\
&#x20;  .content("{\\"name\\": \\"Alice\\"}"))\
&#x20;  .andExpect(status().isCreated());\
\`\`\`\
PUT and DELETE follow the same structure: build request, set headers, verify response.

## 6. Validating Payloads

Use \`jsonPath\` to assert specific fields in JSON responses.\
Validate nested objects, arrays, and field values.\
Also test input validation: send invalid data and expect 400 Bad Request.

## 7. Tips for Maintainable REST Tests

\- Use \`@WebMvcTest\` to load only the web layer.\
\- Mock dependencies to isolate the controller logic.\
\- Keep tests small and focused on one scenario.\
\- Name tests clearly to describe expected behavior.

## 8. Key Takeaways

\- REST API tests confirm correct behavior of endpoints.\
\- \`MockMvc\` is powerful for isolated controller tests.\
\- Validate status codes, headers, and payloads.\
\- Write clear, maintainable tests for each HTTP method.
