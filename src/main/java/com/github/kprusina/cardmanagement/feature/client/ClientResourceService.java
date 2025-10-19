package com.github.kprusina.cardmanagement.feature.client;

import com.github.kprusina.cardmanagement.exception.SoftException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClientResourceService {
  private final ClientRepository clientRepository;

  public Client save(Client client) {
    return clientRepository.save(client);
  }

  @Transactional(readOnly = true)
  public Optional<Client> findByOptionalOib(String oib) {
    return clientRepository.findByOib(oib);
  }

  @Transactional
  public void deleteByOib(String oib) {
    int deleted = clientRepository.deleteByOib(oib);
    if (deleted == 0) {
      throw new SoftException("client.notFound");
    }
    log.info("Deleted client with OIB={}", oib);
  }
}
