package com.github.kprusina.cardmanagement.feature.client;

import com.github.kprusina.cardmanagement.feature.client.request.ClientRequest;
import com.github.kprusina.cardmanagement.feature.client.response.ClientResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

  private final ClientService clientService;

  @Operation(summary = "Create a new client and initiate card creation request.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Client saved and card request created successfully."),
    @ApiResponse(responseCode = "400", description = "Invalid request data (validation failed)."),
    @ApiResponse(responseCode = "409", description = "Client with this OIB already exists."),
    @ApiResponse(
        responseCode = "500",
        description = "Unexpected error occurred. Please try again later.")
  })
  @PostMapping("/card-request")
  public ClientResponse createNewCard(@Valid @RequestBody ClientRequest request) {
    return clientService.createClient(request);
  }

  @Operation(summary = "Fetch client by OIB if exists.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Client found and returned."),
    @ApiResponse(responseCode = "204", description = "Client not found.")
  })
  @GetMapping("/{oib}")
  public ResponseEntity<ClientResponse> getClient(@PathVariable String oib) {
    return clientService.getClientByOib(oib);
  }

  @Operation(
      summary = "Delete a client by OIB",
      description = "Deletes the client with the given OIB. ")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Client deleted successfully."),
    @ApiResponse(responseCode = "404", description = "Client not found.")
  })
  @DeleteMapping("/{oib}")
  public void delete(@PathVariable String oib) {
    clientService.deleteClient(oib);
  }
}
