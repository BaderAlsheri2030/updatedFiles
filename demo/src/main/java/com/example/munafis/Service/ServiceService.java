package com.example.munafis.Service;


import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.ServiceDTO;
import com.example.munafis.Model.Provider;
import com.example.munafis.Model.Service;
import com.example.munafis.Repository.ProviderRepository;
import com.example.munafis.Repository.ServiceRepository;
import lombok.RequiredArgsConstructor;


import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {


    private final ServiceRepository serviceRepository;
    private final ProviderRepository providerRepository;


    //All
    public List getAllServices() {
        return serviceRepository.findAll();
    }

    public void addService(ServiceDTO serviceDTO) {
        Provider provider = providerRepository.findProviderById(serviceDTO.getProvider_id());
        if (provider == null) {
            throw new ApiException("provider Id not found");
        }
        Service service = new Service(null, serviceDTO.getServiceName(), serviceDTO.getServiceType(), serviceDTO.getServiceDetails(), serviceDTO.getPrice(), provider, null);
        serviceRepository.save(service);
    }

    public void updateService(Integer id, ServiceDTO serviceDTO) {

        Service oldService = serviceRepository.findServiceById(id);
        if (oldService == null) {
            throw new ApiException("Service Id not found");
        }

        oldService.setServiceName(serviceDTO.getServiceName());
        oldService.setServiceDetails(serviceDTO.getServiceDetails());
        oldService.setServiceType(serviceDTO.getServiceType());
        oldService.setPrice(serviceDTO.getPrice());
        serviceRepository.save(oldService);

    }

    public void deleteService(Integer id) {
        Service service = serviceRepository.findServiceById(id);
        if (service == null) {
            throw new ApiException("service Id not found");
        }
        serviceRepository.delete(service);
    }


    //All
    public List getAllByOrderByPrice(){
        List<Service> services =serviceRepository.findAllByOrderByPrice();
        if(services==null){
            throw new ApiException("no services");
        }
        return services;
    }

    public List getServicesByName(String name){
        List<Service> services =serviceRepository.findServicesByServiceName(name);
        if(services.isEmpty()){
            throw new ApiException("no services same this name");
        }
        return services;
    }
}



