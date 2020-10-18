package com.example.ComputerSecurity.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.example.ComputerSecurity.dto.requestDto.SignUpRequest;
import com.example.ComputerSecurity.dto.responseDto.SignInResponse;
import com.example.ComputerSecurity.dto.responseDto.SignUpResponse;
import com.example.ComputerSecurity.entities.PasswordMetadata;
import com.example.ComputerSecurity.entities.User;
import com.example.ComputerSecurity.repositories.PasswordMetadataRepository;
import com.example.ComputerSecurity.utils.RandomStringGenerator;
import com.example.ComputerSecurity.utils.SHAEncryption;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    final String USER_TABLE_NAME = "users";

    @Autowired
    final AmazonDynamoDB client;
    @Autowired
    final DynamoDB clientWrapper;

    @Autowired
    final PasswordMetadataRepository passwordMetadataRepository;
    final Table table;

    public AuthenticationService(final AmazonDynamoDB client, final DynamoDB clientWrapper, PasswordMetadataRepository passwordMetadataRepository) {
        this.client = client;
        this.clientWrapper = clientWrapper;
        this.table = clientWrapper.getTable(USER_TABLE_NAME);
        this.passwordMetadataRepository = passwordMetadataRepository;
    }


    public SignInResponse isValidUser(String email, String password) throws Exception {
        SignInResponse signInResponse = new SignInResponse();
//        Item item = table.getItem("email", email);
        User user = getUserWithEmail(email);
        if (user == null) {
            signInResponse.setValidUser(false);
            signInResponse.setErrorMsg("No user found with given email");
            return signInResponse;
        }
        String correctPassword = user.getPassword();
        String dynamicSalt = getUserDynamiSalt(user.getId());
        System.out.println("dynamic salt = " + password + dynamicSalt);
        boolean isPasswordCorrect = correctPassword.equals(SHAEncryption.toHexString(SHAEncryption.getSHA(password + dynamicSalt)));
        if (isPasswordCorrect) {
            signInResponse.setValidUser(true);
        } else {
            signInResponse.setValidUser(false);
            signInResponse.setErrorMsg("Incorrect password");
        }
        return signInResponse;
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest) throws Exception {
        SignUpResponse signUpResponse = new SignUpResponse();
        User existingUser = getUserWithEmail(signUpRequest.getEmail());
        if (existingUser != null) {
            signUpResponse.setUserCreated(false);
            signUpResponse.setErrorMsg("User already exists");
            return signUpResponse;
        }
        String userId = RandomStringGenerator.getRandomString();
        String password = signUpRequest.getPassword();
        String dynamicSalt = RandomStringGenerator.getRandomString();
        System.out.println("dynamic salt signup= " + dynamicSalt);

        //store dynamic  salt
        passwordMetadataRepository.save(new PasswordMetadata(userId, dynamicSalt));

        String passwordEncrypted = SHAEncryption.toHexString(SHAEncryption.getSHA(password + dynamicSalt));
        Item user = new Item().withPrimaryKey("id", userId).
                withString("email", signUpRequest.getEmail())
                .withString("name", signUpRequest.getName())
                .withString("password", passwordEncrypted)
                .withString("mobile", signUpRequest.getMobile())
                .withBoolean("primeUser", signUpRequest.isPrimeUser());

        PutItemOutcome putItemOutcome = table.putItem(user);
        signUpResponse.setUserCreated(true);
        return signUpResponse;
    }

    private String getUserDynamiSalt(String userId) throws Exception {
        Optional<PasswordMetadata> passwordMetadata = passwordMetadataRepository.findById(userId);
        if (!passwordMetadata.isPresent()) {
            throw new Exception("No Salt for user:" + userId + ", internal error. Please contact us to resolve.");
        }
        return passwordMetadata.get().getDynamicSalt();

    }

    private User getUserWithEmail(final String email) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":email", new AttributeValue().withS(email));
        final ScanRequest scanRequest = new ScanRequest();
        scanRequest.withTableName(USER_TABLE_NAME)
                .withFilterExpression("email = :email")
                .withExpressionAttributeValues(eav);
        ScanResult items = client.scan(scanRequest);
        List<Map<String, AttributeValue>> users = items.getItems();
        if (users == null || users.size() == 0) {
            return null;
        }
        Map<String, AttributeValue> userAttributeValueMap = users.get(0);
        return mapAttributesToUserConverter(userAttributeValueMap);
    }

    private User mapAttributesToUserConverter(Map<String, AttributeValue> userAttributeValueMap) {
        User user = new User();
        user.setId(userAttributeValueMap.get("id").getS());
        user.setEmail(userAttributeValueMap.get("email").getS());
        AttributeValue nameValue = userAttributeValueMap.get("name");
        user.setName(nameValue != null ? nameValue.getS() : null);
        AttributeValue mobileValue = userAttributeValueMap.get("mobile");
        user.setMobile(mobileValue != null ? mobileValue.getS() : null);
        user.setPassword(userAttributeValueMap.get("password").getS());
        return user;

    }


}
