package com.sunny.service.messageboard.rest;

import com.sunny.service.messageboard.MessageBoardApplication;
import com.sunny.service.messageboard.exception.ExceptionMessages;
import com.sunny.service.messageboard.repository.domain.ClientDTO;
import com.sunny.service.messageboard.repository.domain.MessageDTO;
import com.sunny.service.messageboard.rest.util.MessageBoardControllerTestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("FieldCanBeLocal")
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = MessageBoardApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class MessageBoardAPITest {

    private final int NUMBER_OF_RECORDS = 1000;
    private final String MESSAGE_TEXT_ONE = "hello world1";
    private final String MESSAGE_TEXT2 = "hello world2";
    private final String MESSAGE_TEXT3 = "hello world3";
    private final String MESSAGE_TITLE1 = "hi1";
    private final String MESSAGE_TITLE2 = "hi2";
    private final String MESSAGE_TITLE3 = "hi3";
    private final String MESSAGE_TEXT4 = "hello world4";
    private final String MESSAGE_TEXT5 = "hello world5";
    private final String MESSAGE_TEXT6 = "hello world6";
    private final String MESSAGE_TITLE4 = "hi4";
    private final String MESSAGE_TITLE5 = "hi5";
    private final String MESSAGE_TITLE6 = "hi6";
    private final String TEST_CLIENT_DESCRIPTION = "test client1";
    private final String TEST_CLIENT_NAME = "sunny1";
    private final String MESSAGE_TITLE = "hi";
    private final String MESSAGE_TEXT_MODIFIED = "hello world modified";
    private final String MESSAGE_TITLE_MODIFIED = "hi mod";
    private final Logger logger = LoggerFactory.getLogger(MessageBoardAPITest.class);

    private final String HELLO_WORLD_TEXT = "hello world";
    private final static String TITLE = "hi";
    private final String SEPARATOR = "/";
    @Autowired
    private MockMvc mockMvc;

    private static final String MESSAGE_BOARD_RESOURCE_PATH = "/api/v1/messageboard/messages";

    private static final String MESSAGE_BOARD_CLIENT_RESOURCE_PATH = "/api/v1/messageboard/clients";

    private static final String TEST_LONG_CLIENT_NAME = "testClientadadadkad;lkadkas;ldkas;ldkas;ldk;lasd";


    private static final String LONG_DESCRIPTION = "dasd;lasdkdk;ladk;lasda;lsd kasd ad;lakdadka kdadkad ;" +
            "ladk adk;lad k;lasd k;las dk;las ;lasdk;lasd;lasdk;adk;la ;asd";

    private static final String MESSAGE_TEXT_LONG = "hello world afafaafafasfasfasfafa " +
            "afasfasfasfasfa ada asasffasasfaafafafasfasfasfasfasfaskfj;kasfjasfjasfjasfjasjfasjfkasfjasjfasjfasjfk" +
            "lasfjklasjf;kasfj;" +
            "kasfj;kasfj;lasfj;lasfj;lasfjlasfjasl;fk;asdmfkc;la,kf;lasfkcasfk;cdrfk;lcqkw;ldfk;lsdfk;ds,fk;c,fkc;qf" +
            "k;cqfke;cqkewf;cqkew;flqkewcfkfewkcqew,fkcq;lwefkc;lqkewfwfk;lqewfkc;lfkec;lewkf;ckewcfkeqwfkc;lewfkcq" +
            ";fk;" +
            "qewkf;lcqew;fckewfcqew;fk";

    private static final String MESSAGE_TITLE_LONG = "My first message ad';ada';d';asdlas'dlas';dlas';dlas';dlas" +
            "';dlas'" +
            ";d';asdl';asdlas;dlas;dlasdlas";

    private static final String TEXT_LENGTH_EXCEEDED_MESSAGE = "Validation error MESSAGE_TEXT_MAX_LENGTH_EXCEEDED";

    private static final String TITLE_LENGTH_EXCEEDED_MESSAGE = "Validation error MESSAGE_TITLE_MAX_LENGTH_EXCEEDED";

    private static final String CLIENT_DESCRIPTION_LENGTH_EXCEEDED_MESSAGE = "Validation error CLIENT_DESCRIPTION_TEXT_TOO_LONG";

    private static final String CLIENT_NAME_LENGTH_EXCEEDED_MESSAGE = "Validation error CLIENT_NAME_LENGTH_EXCEEDED";

    private static final String CLIENT_NAME_MISSING = "Validation error CLIENT_NAME_EMPTY";

  private static final String CLIENT_PAYLOAD_MISSING_CLIENT_NAME =
      "{\n"
          + "\t\"clientDescription\": \"trial client\",\n"
          + "\t\"clientType\": \"USER\" \n"
          + "}";

    @Test
    public void testGetAllMessagesEmpty() throws Exception {
        this.mockMvc.perform(get(MESSAGE_BOARD_RESOURCE_PATH))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testCreateMessage() throws Exception {
        //Create client
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        //Post message
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        MvcResult mvcResult = postToMessageBoard(messageResourceUrl, HELLO_WORLD_TEXT, TITLE,201);
        long messageId = getMessageId(mvcResult);
        deleteMessage(messageResourceUrl,messageId,204);
    }


    private MvcResult postToMessageBoard(String messageResourceUrl, String text, String title,int expectedStatus) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders
                .post(messageResourceUrl)
                .content(MessageBoardControllerTestUtils.getMessageAsJsonString(text, title))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(expectedStatus))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageText").
                        value(text))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageTitle").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").exists()).andReturn();
    }

    private void postToMessageBoardFailureCase(String messageResourceUrl, String text, String title,
                                               int expectedHttpStatusCode,
                                               String statusText) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(messageResourceUrl)
                .content(MessageBoardControllerTestUtils.getMessageAsJsonString(text, title))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(expectedHttpStatusCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").
                        value(statusText)).andReturn();
    }

    private long createClient(String clientDescription,String clientName) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post(MESSAGE_BOARD_CLIENT_RESOURCE_PATH)
                .content(MessageBoardControllerTestUtils.getClientAsJsonString(clientDescription, clientName))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientName").
                        value(clientName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").exists()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ClientDTO messageBoardClient = MessageBoardControllerTestUtils.convertFrom(content,
                ClientDTO.class);
        return messageBoardClient.getClientId();
    }

    private void createClientFailureCase(String clientDescription,String clientName,
                                         String expectedMesage,
                                         int expectedHttpStatus) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(MESSAGE_BOARD_CLIENT_RESOURCE_PATH)
                .content(MessageBoardControllerTestUtils.getClientAsJsonString(clientDescription, clientName))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(expectedHttpStatus))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").
                        value(expectedMesage));
    }

    private void createClientNoClientNameFailureCase(int expectedHttpStatus,String expectedMessage) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(MESSAGE_BOARD_CLIENT_RESOURCE_PATH)
                .content(CLIENT_PAYLOAD_MISSING_CLIENT_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(expectedHttpStatus)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.message").
                value(expectedMessage));
    }

    @Test
    public void updateMessage() throws Exception {
        //Create client
        String clientName = getClientName();
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        MvcResult mvcResult;
        String content;
        //Post message
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        mvcResult = postToMessageBoard(messageResourceUrl, HELLO_WORLD_TEXT, MESSAGE_TITLE,201);
        long messageId = getMessageId(mvcResult);
        //Modify MessageDTO
        messageResourceUrl = messageResourceUrl + SEPARATOR + messageId;
        updateMessage(messageResourceUrl, MESSAGE_TEXT_MODIFIED, MESSAGE_TITLE_MODIFIED);
        String baseUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        deleteMessage(baseUrl,messageId,204);
    }

    private long getMessageId(MvcResult mvcResult) throws UnsupportedEncodingException {
        String content;
        content = mvcResult.getResponse().getContentAsString();
        MessageDTO message = MessageBoardControllerTestUtils.convertFrom(content, MessageDTO.class);
        return message.getMessageId();
    }

    private String getClientName() {
        return TEST_CLIENT_NAME + System.currentTimeMillis();
    }

    private String getLongClientName() {
        return TEST_LONG_CLIENT_NAME + System.currentTimeMillis();
    }

    private void updateMessage(String messageResourceUrl, String text, String title) throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders
                .put(messageResourceUrl)
                .content(MessageBoardControllerTestUtils.getMessageAsJsonString(text,title))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageText").
                        value(text))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageTitle").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").exists()).andReturn();
    }

    private void updateMessageFailure(String messageResourceUrl,
                                      String text,
                                      String title,
                                      int expectedHttpStatus,
                                      String message) throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders
                .put(messageResourceUrl)
                .content(MessageBoardControllerTestUtils.getMessageAsJsonString(text,title))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(expectedHttpStatus))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").
                        value(message));
    }

    @Test
    public void testDeleteMessage() throws Exception {
        //Create client
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        MvcResult mvcResult;
        String content;
        //Post message
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        mvcResult = postToMessageBoard(messageResourceUrl, HELLO_WORLD_TEXT,TITLE,201);
        long messageId = getMessageId(mvcResult);
        //Delete MessageDTO
        deleteMessage(messageResourceUrl, messageId,204);
    }

    private void deleteMessage(String messageResourceUrl, long messageId, int expectedHttpStatus) throws Exception {
        messageResourceUrl = messageResourceUrl + SEPARATOR + messageId;
        this.mockMvc.perform( MockMvcRequestBuilders
                .delete(messageResourceUrl))
                .andExpect(status().is(expectedHttpStatus));
    }

    @Test
    public void testGetAllMessagesSingleClient() throws Exception {
        //Create client
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        MvcResult mvcResult;
        //Post message
        List<Long> messageIds = new ArrayList<>();
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        mvcResult = postToMessageBoard(messageResourceUrl, MESSAGE_TEXT_ONE, MESSAGE_TITLE1,201);
        messageIds.add(getMessageId(mvcResult));
        mvcResult = postToMessageBoard(messageResourceUrl, MESSAGE_TEXT2, MESSAGE_TITLE2,201);
        messageIds.add(getMessageId(mvcResult));
        mvcResult = postToMessageBoard(messageResourceUrl, MESSAGE_TEXT3, MESSAGE_TITLE3,201);
        messageIds.add(getMessageId(mvcResult));
        List messages = getMessages();
        Assert.assertNotNull(messages);
        Assert.assertTrue(messages.size() >= 3);
        int count = getMessageCount(
                new TestMessageBoardStateHolderBuilder().setMessages(messages).setS(MESSAGE_TEXT_ONE).
                        setHi1(MESSAGE_TITLE1).setS2(MESSAGE_TEXT2).
                        setHi2(MESSAGE_TITLE2).setS3(MESSAGE_TEXT3).
                        setHi3(MESSAGE_TITLE3).createMessageBoardStateHolder());
        Assert.assertEquals(3, count);
        messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        deleteMessages(messageIds, messageResourceUrl);
    }

    private int getMessageCount(MessageBoardStateHolder messageBoardStateHolder) {
        int count = 0;
        for (int i = 0; i < messageBoardStateHolder.getMessages().size(); i++) {
            Object message = messageBoardStateHolder.getMessages().get(i);
            if (message instanceof Map) {
                String text = (String) (((Map) message).get("messageText"));
                String title = (String) (((Map) message).get("messageTitle"));
                if ((text.equals(messageBoardStateHolder.getS()) && title.equals(messageBoardStateHolder.getHi1()))
                        || (text.equals(messageBoardStateHolder.getS2()) && title.equals(messageBoardStateHolder.getHi2()))
                        || (text.equals(messageBoardStateHolder.getS3()) && title.equals(messageBoardStateHolder.getHi3()))) {
                    count++;
                }
            }
        }
        return count;
    }

    @Test
    public void testGetAllMessagesMultipleClient() throws Exception {
        //Create client
        long clientId1 = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId1 > 0);
        List<Long> messageIdsClient1 = new ArrayList<>();
        List<Long> messageIdsClient2 = new ArrayList<>();
        MvcResult mvcResult;
        String content;
        ClientDTO messageBoardClient;
        //Post message
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId1;
        mvcResult = postToMessageBoard(messageResourceUrl, MESSAGE_TEXT_ONE, "hi1",201);
        messageIdsClient1.add(getMessageId(mvcResult));
        //Create client
        messageBoardClient = getClientDTO();
        long clientId2 = messageBoardClient.getClientId();
        Assert.assertTrue(clientId2 > 0);
        //Post message
        messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId1;
        mvcResult = postToMessageBoard(messageResourceUrl, MESSAGE_TEXT4, MESSAGE_TITLE4,201);
        messageIdsClient1.add(getMessageId(mvcResult));
        messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId2;
        mvcResult = postToMessageBoard(messageResourceUrl, MESSAGE_TEXT5, MESSAGE_TITLE5,201);
        messageIdsClient2.add(getMessageId(mvcResult));
        messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId1;
        mvcResult = postToMessageBoard(messageResourceUrl, MESSAGE_TEXT6, MESSAGE_TITLE6,201);
        messageIdsClient1.add(getMessageId(mvcResult));
        List messages = getMessages();
        Assert.assertNotNull(messages);
        Assert.assertTrue(messages.size() >= 3);
        int count = getMessageCount(
                new TestMessageBoardStateHolderBuilder().setMessages(messages).setS(MESSAGE_TEXT4).setHi1(MESSAGE_TITLE4).setS2(MESSAGE_TEXT5).setHi2(MESSAGE_TITLE5).setS3(MESSAGE_TEXT6).setHi3(MESSAGE_TITLE6).createMessageBoardStateHolder());
        Assert.assertEquals(3, count);
        messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId1;
        deleteMessages(messageIdsClient1, messageResourceUrl);
        messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId2;
        deleteMessages(messageIdsClient2,messageResourceUrl);
    }

    private void deleteMessages(List<Long> messageIdsClient1, String messageResourceUrl) throws Exception {
        for (long messageId : messageIdsClient1) {
            deleteMessage(messageResourceUrl, messageId, 204);
        }
    }

    private List getMessages() throws Exception {
        String messageResourceUrl;
        MvcResult mvcResult;
        messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH;
        mvcResult = this.mockMvc.perform(get(messageResourceUrl)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        return MessageBoardControllerTestUtils.
                convertFrom(mvcResult.getResponse().getContentAsString(),
                        List.class);
    }

    private ClientDTO getClientDTO() throws Exception {
        MvcResult mvcResult;
        String content;
        ClientDTO messageBoardClient;
        mvcResult = this.mockMvc.perform( MockMvcRequestBuilders
                .post(MESSAGE_BOARD_CLIENT_RESOURCE_PATH)
                .content(MessageBoardControllerTestUtils.getClientAsJsonString(TEST_CLIENT_DESCRIPTION,
                        TEST_CLIENT_NAME))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientName").
                        value(TEST_CLIENT_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").exists()).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        messageBoardClient = MessageBoardControllerTestUtils.convertFrom(content,
                ClientDTO.class);
        return messageBoardClient;
    }

    @Test
    public void testCreateMessageInvalidClientId() throws Exception {
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + (-1);
    postToMessageBoardFailureCase(
        messageResourceUrl,
        HELLO_WORLD_TEXT,
            MESSAGE_TITLE,
        400,
            ExceptionMessages.BUSINESS_CONSTRAINTS_VIOLATED);
    }

    @Test
    public void testDeleteMessageInvalidMessageId() throws Exception {
        //Create client
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        MvcResult mvcResult;
        String content;
        //Post message
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        mvcResult = postToMessageBoard(messageResourceUrl, HELLO_WORLD_TEXT,TITLE,201);
        content = mvcResult.getResponse().getContentAsString();
        MessageDTO message = MessageBoardControllerTestUtils.convertFrom(content, MessageDTO.class);
        //Delete MessageDTO
        deleteMessage(messageResourceUrl, -1,404);
    }

    @Test
    public void updateMessageInvalidMessageId() throws Exception {
        //Create client
        String clientName = getClientName();
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        MvcResult mvcResult;
        String content;
        //Modify MessageDTO
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId + SEPARATOR + (-1);
        updateMessageFailure(messageResourceUrl,"hello world modified","hi mod",404,
                "Resource not found MESSAGE_NOT_FOUND");
    }

    @Test
    public void testDeleteMessageClientNotOwner() throws Exception {
        //Create client
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        MvcResult mvcResult;
        String content;
        //Post message
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        mvcResult = postToMessageBoard(messageResourceUrl, HELLO_WORLD_TEXT,TITLE,201);
        long messageId = getMessageId(mvcResult);
        //Delete MessageDTO
        long differentClientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + differentClientId;
        deleteMessage(messageResourceUrl, messageId,400);
    }

    @Test
    public void testCreateMessageTextTooLong() throws Exception {
        //Create client
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        //Post message
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        postToMessageBoardFailureCase(messageResourceUrl, MESSAGE_TEXT_LONG, TITLE,400,
                TEXT_LENGTH_EXCEEDED_MESSAGE);
    }

    @Test
    public void testCreateMessageTitleTooLong() throws Exception {
        //Create client
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        //Post message
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        postToMessageBoardFailureCase(messageResourceUrl, HELLO_WORLD_TEXT, MESSAGE_TITLE_LONG,400,
                TITLE_LENGTH_EXCEEDED_MESSAGE);
    }

    @Test
    public void updateMessageTextTooLong() throws Exception {
        //Create client
        String clientName = getClientName();
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        MvcResult mvcResult;
        String content;
        //Post message
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        mvcResult = postToMessageBoard(messageResourceUrl, HELLO_WORLD_TEXT,TITLE,201);
        long messageId = getMessageId(mvcResult);
        //Modify MessageDTO
        messageResourceUrl = messageResourceUrl + SEPARATOR + messageId;
        updateMessageFailure(messageResourceUrl,MESSAGE_TEXT_LONG,"hi mod",400,
                TEXT_LENGTH_EXCEEDED_MESSAGE);
    }

    @Test
    public void updateMessageTitleTooLong() throws Exception {
        //Create client
        String clientName = getClientName();
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        MvcResult mvcResult;
        String content;
        //Post message
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        mvcResult = postToMessageBoard(messageResourceUrl, HELLO_WORLD_TEXT,TITLE,201);
        long messageId = getMessageId(mvcResult);
        //Modify MessageDTO
        messageResourceUrl = messageResourceUrl + SEPARATOR + messageId;
        updateMessageFailure(messageResourceUrl, HELLO_WORLD_TEXT,MESSAGE_TITLE_LONG,400,
                TITLE_LENGTH_EXCEEDED_MESSAGE);
    }

    @Test
    public void createNewClientTest() throws Exception {
        String clientName = getClientName();
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(MESSAGE_BOARD_CLIENT_RESOURCE_PATH)
                .content(MessageBoardControllerTestUtils.getClientAsJsonString(TEST_CLIENT_DESCRIPTION, clientName))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientName").
                        value(clientName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientDescription").value(TEST_CLIENT_DESCRIPTION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").exists());
    }


    @Test
    public void createClientDuplicate() throws Exception {
        String clientName = getClientName();
        createClient(TEST_CLIENT_DESCRIPTION,clientName);
        createClientFailureCase(TEST_CLIENT_DESCRIPTION,clientName,
                ExceptionMessages.BUSINESS_CONSTRAINTS_VIOLATED,
                400);
    }


    @Test
    public void createNewClientNameTooLongTest() throws Exception {
        String clientName = getLongClientName();
        createClientFailureCase(TEST_CLIENT_DESCRIPTION,clientName,CLIENT_NAME_LENGTH_EXCEEDED_MESSAGE,400);
    }

    @Test
    public void createNewClientDescriptionTooLongTest() throws Exception {
        String clientName = getClientName();
        createClientFailureCase(LONG_DESCRIPTION,clientName,CLIENT_DESCRIPTION_LENGTH_EXCEEDED_MESSAGE,400);
    }

    @Test
    public void createNewClientNullClientName() throws Exception {
        String clientName = null;
        createClientFailureCase(TEST_CLIENT_DESCRIPTION,clientName,CLIENT_NAME_MISSING,400);
    }

    @Test
    public void createNewClientMissingClientName() throws Exception {
        createClientNoClientNameFailureCase(400,CLIENT_NAME_MISSING);
    }


    @Test
    public void createLargerNumberOfMessagesTest() throws Exception {
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        //Post message
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        long startTime = System.currentTimeMillis();
        List<Long> messageIds = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_RECORDS; i++){
            MvcResult mvcResult = postToMessageBoard(messageResourceUrl, HELLO_WORLD_TEXT, TITLE,201);
            messageIds.add(getMessageId(mvcResult));
        }
        long endTime = System.currentTimeMillis();
        logger.info("Time taken to create messages = " + (endTime - startTime) + " millis ");
        startTime = System.currentTimeMillis();
        verifyMessageBoardSize(NUMBER_OF_RECORDS);
        endTime = System.currentTimeMillis();
        logger.info("Time taken to get all messages = " + (endTime - startTime) + " millis ");
        deleteMessages(messageIds,messageResourceUrl);
    }

    private void verifyMessageBoardSize(int minimumMessageBoardSize) throws Exception {
        String messageResourceUrl;
        messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH;
        MvcResult mvcResult = this.mockMvc.perform(get(messageResourceUrl)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        List messages = MessageBoardControllerTestUtils.
                convertFrom(mvcResult.getResponse().getContentAsString(),
                        List.class);
        Assert.assertNotNull(messages);
        Assert.assertTrue(messages.size() >= minimumMessageBoardSize);
    }

    @Test
    public void updateLargerNumberOfMessagesTest() throws Exception {
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        //Post message
        List<Long> messageIds = postMessages(clientId);
        getAllMessages();
        updateAllMessages(clientId, messageIds);
    }

    private void updateAllMessages(long clientId, List<Long> messageIds) throws Exception {
        long startTime = System.currentTimeMillis();
        String messagebaseUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        for(long messageId : messageIds){
            String url = messagebaseUrl + SEPARATOR + messageId;
            updateMessage(url,HELLO_WORLD_TEXT,TITLE);
        }
        long endTime = System.currentTimeMillis();
        logger.info("Time taken to update all messages = " + (endTime - startTime) + " millis ");
    }

    private void getAllMessages() throws Exception {
        long startTime = System.currentTimeMillis();
        verifyMessageBoardSize(NUMBER_OF_RECORDS);
        long endTime = System.currentTimeMillis();
        logger.info("Time taken to get all messages = " + (endTime - startTime) + " millis ");
    }

    @Test
    public void deleteLargerNumberOfMessagesTest() throws Exception {
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        //Post message
        List<Long> messageIds = postMessages(clientId);
        getAllMessages();
        deleteAllMessages(clientId, messageIds);
    }

    private void deleteAllMessages(long clientId, List<Long> messageIds) throws Exception {
        long startTime = System.currentTimeMillis();
        String messagebaseUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        for(long messageId : messageIds){
            String url = messagebaseUrl + SEPARATOR;
            deleteMessage(url,messageId,204);
        }
        long endTime = System.currentTimeMillis();
        logger.info("Time taken to delete all messages = " + (endTime - startTime) + " millis ");
    }

    private List<Long> postMessages(long clientId) throws Exception {
        String messageResourceUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        long startTime = System.currentTimeMillis();
        List<Long> messageIds = new ArrayList<>();
        postContinuousMessages(messageResourceUrl, messageIds);
        long endTime = System.currentTimeMillis();
        logger.info("Time taken to create messages = " + (endTime - startTime) + " millis ");
        return messageIds;
    }

    private void postContinuousMessages(String messageResourceUrl, List<Long> messageIds) throws Exception {
        for (int i = 0; i < NUMBER_OF_RECORDS; i++) {
            MvcResult mvcResult = postToMessageBoard(messageResourceUrl,
                    HELLO_WORLD_TEXT,
                    TITLE, 201);
            String content = mvcResult.getResponse().getContentAsString();
            MessageDTO message = MessageBoardControllerTestUtils.convertFrom(content, MessageDTO.class);
            long messageId = message.getMessageId();
            messageIds.add(messageId);
        }
    }

    @Test
    public void testGetAllMessages() throws Exception {
        long clientId = createClient(TEST_CLIENT_DESCRIPTION,getClientName());
        Assert.assertTrue(clientId > 0);
        //Post message
        List<Long> messageIds = postMessages(clientId);
        getAllMessages();
        String messageUrl = MESSAGE_BOARD_RESOURCE_PATH + SEPARATOR + clientId;
        deleteMessages(messageIds,messageUrl);
    }
}