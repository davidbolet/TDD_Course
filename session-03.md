# Session 3: REST API Testing

## Objectives

- Test REST controllers in Spring Boot using MockMvc.
- Validate response status codes and payloads.
- Handle request/response serialization.
- Write tests for basic CRUD endpoints.

---

## 1. MockMvc for Controller Testing

`MockMvc` allows testing your controllers without running a real server.

Setup with `@WebMvcTest`:

```java
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;
}
```

---

## 2. Testing GET Endpoint

Example controller:

```java
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
```

Test for `GET /customers/1`:

```java
@Test
void shouldReturnCustomerIfExists() throws Exception {
    Customer customer = new Customer(1L, "Alice");

    when(customerService.findById(1L)).thenReturn(Optional.of(customer));

    mockMvc.perform(get("/customers/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("Alice"));
}
```

---

## 3. Testing POST Endpoint

Controller:

```java
@PostMapping
public ResponseEntity<Customer> create(@RequestBody Customer customer) {
    Customer saved = service.save(customer);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
}
```

Test:

```java
@Test
void shouldCreateCustomer() throws Exception {
    Customer input = new Customer(null, "Bob");
    Customer saved = new Customer(1L, "Bob");

    when(customerService.save(any())).thenReturn(saved);

    mockMvc.perform(post("/customers")
           .contentType(MediaType.APPLICATION_JSON)
           .content("{"name":"Bob"}"))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.id").value(1));
}
```

---

## 4. Testing PUT and DELETE

Controller examples:

```java
@PutMapping("/{id}")
public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer c) {
    return service.update(id, c)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
}
```

Tests:

```java
@Test
void shouldUpdateCustomer() throws Exception {
    Customer updated = new Customer(1L, "Updated");

    when(customerService.update(eq(1L), any())).thenReturn(Optional.of(updated));

    mockMvc.perform(put("/customers/1")
           .contentType(MediaType.APPLICATION_JSON)
           .content("{"name":"Updated"}"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("Updated"));
}

@Test
void shouldDeleteCustomer() throws Exception {
    mockMvc.perform(delete("/customers/1"))
           .andExpect(status().isNoContent());
}
```

---

## 5. Exercise: Full CRUD Test Suite

### Task
Create a full test class for `CustomerController`:
- `GET /customers/{id}` – found and not found
- `POST /customers` – successful creation
- `PUT /customers/{id}` – update
- `DELETE /customers/{id}` – deletion

Use `@WebMvcTest`, `@MockBean`, and `MockMvc`.

---

## Summary

You now know how to:
- Write REST controller tests with `MockMvc`
- Assert JSON payloads and HTTP status codes
- Build full test coverage for CRUD APIs

Next session: Learn how to mock dependencies with Mockito effectively.
