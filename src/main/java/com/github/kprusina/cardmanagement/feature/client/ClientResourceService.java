package com.github.kprusina.cardmanagement.feature.client;

import com.github.kprusina.cardmanagement.exception.SoftException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClientResourceService {
  private final ClientRepository clientRepository;

  public Client save(Client client) {
    try {
      return clientRepository.save(client);
    } catch (DataIntegrityViolationException ex) {

      throw new SoftException(
          HttpStatus.CONFLICT, "DUPLICATE_OIB", client.getOib(), "client.duplicateOib");
    }
  }

  @Transactional(readOnly = true)
  public Optional<Client> findByOptionalOib(String oib) {
    return clientRepository.findByOib(oib);
  }

  @Transactional
  public void deleteByOib(String oib) {
    int deleted = clientRepository.deleteByOib(oib);
    if (deleted == 0) {
      throw new SoftException(HttpStatus.NOT_FOUND, "CLIENT_NOT_FOUND", oib, "client.notFound");
    }
    log.info("Deleted client with OIB={}", oib);
  }
}
