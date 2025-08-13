package pl.lodz.p.it.functionalfood.investigator.util.converters;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs.*;
import pl.lodz.p.it.functionalfood.investigator.entities.Producer;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs.CreateProducerDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs.ProducerDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs.ProducerListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs.ProducerOnListDTO;

import java.util.ArrayList;
import java.util.List;

public class ProducerConverter {
    public static ProducerDTO createProducerDTO(Producer producer) {
        return new ProducerDTO(producer.getId(), producer.getName(), producer.getAddress(), producer.getCountryCode(), producer.getNIP(), producer.getRMSD(), producer.getContact());
    }

    public static ProducerListDTO createProducerListDTO(List<Producer> producers) {
        List<ProducerOnListDTO> producerDTOs = new ArrayList<>();
        for(Producer producer : producers) {
            producerDTOs.add(createProducerOnListDTO(producer));
        }
        return new ProducerListDTO(producerDTOs);
    }

    private static ProducerOnListDTO createProducerOnListDTO(Producer producer) {
        return new ProducerOnListDTO(producer.getId(), producer.getName(), producer.getCountryCode());
    }

    public static Producer createProducerFromCreateProducerDTO(CreateProducerDTO createProducerDTO) {
        return new Producer(createProducerDTO.getName(), createProducerDTO.getAddress(), createProducerDTO.getCountryCode(), createProducerDTO.getNIP(), createProducerDTO.getRMSD(), createProducerDTO.getContact());
    }
}
