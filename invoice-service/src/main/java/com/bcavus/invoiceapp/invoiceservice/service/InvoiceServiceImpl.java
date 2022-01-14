package com.bcavus.invoiceapp.invoiceservice.service;

import com.bcavus.invoiceapp.invoiceservice.component.ModelConverter;
import com.bcavus.invoiceapp.invoiceservice.component.ModelMapper;
import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.request.CreateInvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.model.Invoice;
import com.bcavus.invoiceapp.invoiceservice.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired
    private final InvoiceRepository invoiceRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ModelConverter modelConverter;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              ModelMapper modelMapper,
                              ModelConverter modelConverter) {
        this.invoiceRepository = invoiceRepository;
        this.modelMapper = modelMapper;
        this.modelConverter = modelConverter;
    }

    @Override
    public InvoiceDTO createInvoice(CreateInvoiceDTO createInvoiceDTO) {

        Invoice createdInvoice = this.invoiceRepository.insert(this.modelConverter.convertToInvoice(createInvoiceDTO));

        logger.info("[InvoiceService/createInvoice]: Successfully created invoice: " + createdInvoice);

        return this.modelMapper.mapToInvoiceDTO(createdInvoice);
    }
}