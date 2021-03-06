package com.bancopichincha.inventario.service;

import com.bancopichincha.inventario.domain.Cliente;
import com.bancopichincha.inventario.repository.ClienteRepository;
import com.bancopichincha.inventario.repository.TransaccionRepository;
import com.bancopichincha.inventario.service.dto.TransaccionInfo;
import com.bancopichincha.inventario.service.params.RangeDateParam;
import com.bancopichincha.inventario.web.rest.errors.ResourceNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CsvExportService {

    private final Logger log = LoggerFactory.getLogger(CsvExportService.class);

    private final TransaccionRepository transaccionRepository;

    private final ClienteRepository clienteRepository;

    public CsvExportService(TransaccionRepository transaccionRepository, ClienteRepository clienteRepository) {
        this.transaccionRepository = transaccionRepository;
        this.clienteRepository = clienteRepository;
    }

    public void writeTransaccionesToCsv(HttpServletResponse response, RangeDateParam dateParam, Long clientId) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(clientId);
        Cliente cliente = clienteOptional.orElseThrow(() -> new ResourceNotFoundException(URI.create("/api/transaccions/reporte"), "Cliente no encontrado", "No se encontro el cliente con el identificador " + clientId));
        List<TransaccionInfo> transaccions = transaccionRepository.findTransaccionByClienteAndFechaBetween(cliente, dateParam.getStartDate(), dateParam.getEndDate());
        try (CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.POSTGRESQL_CSV)) {
            csvPrinter.printRecord("ID", "Fecha", "Tienda", "Producto");
            for (TransaccionInfo transaccion : transaccions) {
                csvPrinter.printRecord(transaccion.getId(), transaccion.getFecha(), transaccion.getCantidad(), transaccion.getTienda().getNombre(), transaccion.getProducto().getName());
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }
}
