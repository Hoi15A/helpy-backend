package ch.zhaw.pm3.helpy;

public class JobControllerTest {
    /**
     * Sample data
     *
     *
     * @TestPropertySource("classpath:application.properties")
     * @SpringBootTest(classes = OrderController.class)
     * public class OrderControllerTest {
     *
     *     private static final String ORDER_CREATE_URL = "/order/create";
     *     private static final URL ORDER_RESOURCES = OrderControllerTest.class.getClassLoader().getResource("order/valid");
     *
     *     @Autowired
     *     private OrderController orderController;
     *     @Autowired
     *     WebApplicationContext webApplicationContext;
     *
     *
     *     private MockMvc mvc;
     *     private final HttpHeaders headers = new HttpHeaders();
     *
     *     @BeforeEach
     *     public void setUp() {
     *         mvc = MockMvcBuilders
     *                 .standaloneSetup(orderController)
     *                 .setControllerAdvice(new ApiExceptionHandler())
     *                 .build();
     *         CreateOrder orderTask = mock(CreateOrder.class);
     *         doNothing().when(orderTask).process();
     *         ReflectionTestUtils.setField(orderController, "orderTask", orderTask);
     *         headers.add("X-WC-Webhook-Signature", "95GwJTZgwGBb42JGsxM8sEAEg9RW6JUgJGYmUBS1y4Y=");
     *     }
     *
     *     @Test
     *     public void testCreateOrder() throws Exception {
     *         String order = FileUtils.readFileToString(
     *                 new File(ORDER_RESOURCES.getPath() + "/sig.json"), Charset.defaultCharset());
     *
     *         MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ORDER_CREATE_URL)
     *                 .headers(headers)
     *                 .contentType(MediaType.APPLICATION_JSON_VALUE)
     *                 .content(order))
     *                 .andReturn();
     *
     *         int status = mvcResult.getResponse().getStatus();
     *         assertEquals(HttpStatus.OK, HttpStatus.resolve(status));
     *     }
     *
     *     @Test
     *     public void testUnauthorizedCreate() throws Exception {
     *         String order = FileUtils.readFileToString(
     *                 new File(ORDER_RESOURCES.getPath() + "/order.json"), Charset.defaultCharset());
     *
     *         MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ORDER_CREATE_URL)
     *                 .headers(headers)
     *                 .contentType(MediaType.APPLICATION_JSON_VALUE)
     *                 .contennt(order))
     *                 .andReturn();
     *
     *         int status = mvcResult.getResponse().getStatus();
     *
     *         assertEquals(HttpStatus.BAD_REQUEST, HttpStatus.resolve(status));
     *         assertEquals("Signature does not match", mvcResult.getResponse().getErrorMessage());
     *     }
     *
     *     @Test
     *     public void testWrongBodySyntax() throws Exception {
     *         String order = "[]";
     *         headers.set("X-WC-Webhook-Signature", "k63zwZSFuheYPAT9nvfQSDxIK1WPKTCFAc0qo4Z8J2s=");
     *         MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ORDER_CREATE_URL)
     *                 .headers(headers)
     *                 .contentType(MediaType.APPLICATION_JSON_VALUE)
     *                 .content(order))
     *                 .andReturn();
     *
     *         int status = mvcResult.getResponse().getStatus();
     *
     *         assertEquals(HttpStatus.BAD_REQUEST, HttpStatus.resolve(status));
     *     }
     * }
     *
     */
}
