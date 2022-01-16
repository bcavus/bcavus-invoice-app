package com.bcavus.invoiceapp.invoiceservice.service;

import com.bcavus.invoiceapp.invoiceservice.component.ModelConverter;
import com.bcavus.invoiceapp.invoiceservice.component.ModelMapper;
import com.bcavus.invoiceapp.invoiceservice.domain.InvoiceDomain;
import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.PaginatedInvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.PaginationMetadata;
import com.bcavus.invoiceapp.invoiceservice.dto.request.CreateInvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.exception.NoInvoiceFoundException;
import com.bcavus.invoiceapp.invoiceservice.model.Invoice;
import com.bcavus.invoiceapp.invoiceservice.model.InvoiceStatus;
import com.bcavus.invoiceapp.invoiceservice.repository.InvoiceRepository;
import com.bcavus.invoiceapp.invoiceservice.service.messaging.message.InvoiceUserValidationMessage;
import com.bcavus.invoiceapp.invoiceservice.service.messaging.producer.InvoiceMessageProducerService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired
    private final InvoiceRepository invoiceRepository;

    @Autowired
    private final InvoiceDomain invoiceDomain;

    @Autowired
    private final InvoiceMessageProducerService<InvoiceUserValidationMessage> invoiceMessageProducerService;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ModelConverter modelConverter;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              InvoiceDomain invoiceDomain,
                              InvoiceMessageProducerService<InvoiceUserValidationMessage> invoiceMessageProducerService,
                              ModelMapper modelMapper,
                              ModelConverter modelConverter) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceDomain = invoiceDomain;
        this.invoiceMessageProducerService = invoiceMessageProducerService;
        this.modelMapper = modelMapper;
        this.modelConverter = modelConverter;
    }

    @Override
    public InvoiceDTO createInvoice(CreateInvoiceDTO createInvoiceDTO) {

        Invoice createdInvoice = this.invoiceRepository.insert(this.modelConverter.convertToInvoice(createInvoiceDTO));

        logger.info("[InvoiceService/createInvoice]: Successfully created invoice: " + createdInvoice);

        this.invoiceMessageProducerService.sendMessage(InvoiceUserValidationMessage.builder()
                .invoiceId(createdInvoice.getId())
                .userEmail(createdInvoice.getEmail())
                .expenseAmount(createdInvoice.getAmount())
                .valid(false)
                .build());

        return this.modelMapper.mapToInvoiceDTO(createdInvoice);
    }

    @Override
    public InvoiceDTO getInvoiceById(@NonNull String invoiceId) {
        if(invoiceId.isEmpty()) {
            logger.warn("[InvoiceService/getInvoiceById]: invoiceId cannot be null or empty");

            throw new IllegalArgumentException("invoiceId cannot be null or empty");
        }

        Optional<Invoice> foundInvoice = this.invoiceRepository.findById(invoiceId);

        if(foundInvoice.isEmpty()) {
            logger.warn("[InvoiceService/getInvoiceById]: Cannot find any invoice with given id: " + invoiceId);

            throw new NoInvoiceFoundException("Cannot find any invoice with given invoiceId: " + invoiceId);
        }

        logger.info("[InvoiceService/getInvoiceById]: Successfully retrieved invoice: " + foundInvoice.get());

        return this.modelMapper.mapToInvoiceDTO(foundInvoice.get());
    }

    @Override
    public PaginatedInvoiceDTO getAllInvoices(@NonNull PaginationMetadata metadata) {

        if(!invoiceDomain.isPaginationValid(metadata)) {
            logger.warn("[InvoiceService/getAllInvoices]: Invalid metadata provided: " + metadata);

            throw new IllegalArgumentException("Invalid metadata provided.");
        }

        final Pageable pageable = PageRequest.of(metadata.getPage(), metadata.getSize());

        final Page<Invoice> pagedInvoices = this.invoiceRepository.findAll(pageable);

        logger.info("[InvoiceService/getAllInvoices]: Successfully retrieved list of invoices with given metadata");

        return PaginatedInvoiceDTO.builder()
                .invoices(pagedInvoices.stream().map(this.modelMapper::mapToInvoiceDTO).collect(Collectors.toList()))
                .paginationMetadata(PaginationMetadata.builder()
                        .page(metadata.getPage())
                        .size(metadata.getSize())
                        .totalPageNumber(pagedInvoices.getTotalPages())
                        .total(pagedInvoices.getTotalElements())
                        .build())
                .build();
    }

    @Override
    public InvoiceDTO updateInvoiceStatusById(@NonNull String invoiceId, @NonNull InvoiceStatus status) {

        InvoiceDTO foundInvoice = this.getInvoiceById(invoiceId);

        InvoiceDTO updatedInvoiceDTO = this.invoiceDomain.updateInvoiceStatus(status, foundInvoice);

        Invoice updatedInvoice = this.invoiceRepository.save(this.modelConverter.convertToInvoice(updatedInvoiceDTO));

        logger.info("[InvoiceService/updateInvoiceStatusById]: Successfully updated invoice with given id: " + invoiceId);

        return this.modelMapper.mapToInvoiceDTO(updatedInvoice);
    }

}