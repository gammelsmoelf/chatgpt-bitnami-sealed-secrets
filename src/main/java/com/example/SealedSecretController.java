package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;


@RestController
    public class SealedSecretController {

        @PostMapping(value = "/createSealedSecret",produces = "application/json" )

        public ResponseEntity<SealSecretResponse> createSealedSecret(@RequestBody CreateSealedSecretRequest request) throws IOException {

            try {
                // Create the kubeseal command
                ProcessBuilder processBuilder = new ProcessBuilder("echo", "%s".formatted(request.getYamlData()));

                // Set the working directory if needed
                // processBuilder.directory(new File("/path/to/kubeseal"));

                // Start the process
                Process process = processBuilder.start();

                // Read the output of the process
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    StringBuilder output = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append(System.lineSeparator());
                    }
                    process.waitFor(); // Wait for the process to complete

                    return ResponseEntity.ok(new SealSecretResponse(output.toString()));

                }

            } catch (IOException | InterruptedException e) {
                // Handle any exceptions that occur
                return ResponseEntity.internalServerError().body(new SealSecretResponse(e.getMessage()));
            }
        }
    }
