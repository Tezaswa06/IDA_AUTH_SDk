package examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import auth.sdk.java.authenticator.Authenticator;
import auth.sdk.java.utils.ConfigLoader;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Authenticates users using otp.
 * <p>
 * Usage: See {@link auth.sdk.java.authenticator.Authenticator}
 * </p>
 *
 * @author Tezaswa06
 * @version 1.0
 */

public class OtpGenerate {
    public static void main(String[] args) {
        try {
            // Load configuration
            ConfigLoader configLoader = new ConfigLoader();
            Authenticator authenticator = new Authenticator(configLoader.loadConfig(), null);

            // Perform OTP generation
            Map<String, Object> response = authenticator.genOtp(
                    "2139125329", // individual_id
                    "UIN",              // individual_id_type
                    String.valueOf(Optional.empty()),       // txnId
                    true,               // email
                    true                // phone
            );

            // Convert response to JsonNode for easier processing
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.convertValue(response, JsonNode.class);

            // Print response
            System.out.println("Response status: 200");
            System.out.println("Response body: " + responseNode.toPrettyString());
            // Check for errors
            if (responseNode.has("errors")) {
                for (JsonNode error : responseNode.get("errors")) {
                    System.out.println(error.get("errorCode").asText() + " : " + error.get("errorMessage").asText());
                }
                System.exit(1);
            }

            // Print response
            System.out.println("Response status: 200");
            System.out.println("Response body: " + responseNode.toPrettyString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}