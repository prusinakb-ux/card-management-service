package com.github.kprusina.cardmanagement.feature.client.mapper;

import com.github.kprusina.cardmanagement.feature.client.Client;
import com.github.kprusina.cardmanagement.feature.client.request.ClientRequest;
import com.github.kprusina.cardmanagement.feature.client.response.ClientResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

  Client toEntity(ClientRequest request);

  ClientResponse toResponse(Client client);

  ClientRequest toRequest(Client client);
}
