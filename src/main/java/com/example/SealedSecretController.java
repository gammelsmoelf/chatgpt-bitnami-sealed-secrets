package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;


@RestController
    public class SealedSecretController {

        @PostMapping(value = "/createSealedSecret",produces = "application/json" )

        public ResponseEntity<SealSecretResponse> createSealedSecret(@RequestBody CreateSealedSecretRequest request) {

            try {
                // Create the kubeseal command
                String pathToInputFile = "/tmp/unsealed-secret.yaml";
                BufferedWriter writer = new BufferedWriter(new FileWriter(pathToInputFile));
                writer.write(request.getYamlData());
                System.out.println(request.getYamlData());
                ProcessBuilder processBuilder = new ProcessBuilder("kubeseal", "--scope","cluster-wide","--format","yaml","--cert","/mycert.pem", "--secret-file",pathToInputFile);
                System.out.println("1");
                // Set the working directory if needed
                //processBuilder.directory(new File("/tmp"));

                writer.close();
                // Start the process
                Process process = processBuilder.start();
                process.waitFor(); // Wait for the process to complete


                // Read the output of the process
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    System.out.println("2");
                    StringBuilder output = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append(System.lineSeparator());
                    }
                    System.out.println(output);
                    return ResponseEntity.ok(new SealSecretResponse(output.toString()));
                }

            } catch (IOException | InterruptedException e) {
                // Handle any exceptions that occur
                e.printStackTrace();
                return ResponseEntity.internalServerError().body(new SealSecretResponse(e.getMessage()));
            }
        }
    }
