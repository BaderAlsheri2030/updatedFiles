package com.example.munafis.Service;


import com.example.munafis.API.ApiException;
import com.example.munafis.Model.Offer;
import com.example.munafis.Model.Orderr;
import com.example.munafis.Model.Product;
import com.example.munafis.Model.Provider;
import com.example.munafis.Repository.OffersRepository;
import com.example.munafis.Repository.OrderRepository;
import com.example.munafis.Repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final OrderRepository orderRepository;
    private final OffersRepository offersRepository;

    public List getAllProviders(){
        return providerRepository.findAll();
    }


    //Register
    public void addProvider(Provider provider){
        providerRepository.save(provider);

    }


    public void updateProvider(Integer id, Provider provider){
        Provider oldProvider=providerRepository.findProviderById(id);
        if(oldProvider==null){
            throw new ApiException("Provider id not found");
        }

        oldProvider.setAddress(provider.getAddress());
        oldProvider.setField(provider.getField());
        oldProvider.setBusinessNumber(provider.getBusinessNumber());
        oldProvider.setOffers(provider.getOffers());
        oldProvider.setServices(provider.getServices());
        oldProvider.setCompanyName(provider.getCompanyName());
        oldProvider.setProducts(provider.getProducts());

        providerRepository.save(oldProvider);

    }


    public void deleteProvider(Integer id){
        Provider provider=providerRepository.findProviderById(id);

        if(provider==null){
            throw new ApiException("Provider id not found");
        }
        providerRepository.delete(provider);
    }

    //ALL
    public Set<Product> getAllProductsByProvider(String providerName){
        Provider provider = providerRepository.findByCompanyName(providerName);
        if(provider==null){
            throw new ApiException("company name not available");
        }
        if(provider.getProducts().isEmpty()){
            throw new ApiException("this provider not have products");
        }
        return provider.getProducts();
    }


    //ALL
    public Set<com.example.munafis.Model.Service> getAllServicesByProvider(String providerName){
        Provider provider = providerRepository.findByCompanyName(providerName);
        if(provider==null){
            throw new ApiException("company name not available");
        }
        if(provider.getServices().isEmpty()){
            throw new ApiException("this provider doesn't have Services");
        }
        return provider.getServices();
    }


    public List<Orderr> getOrderAllByStatus(String status){
        List<Orderr> orders = orderRepository.findAllByStatus(status);
        if(orders.isEmpty()){
            throw new ApiException("no orders");
        }
        return orders;

    }

    //provider view accepted offers
    public List<Offer> viewMyAcceptedOffers(Integer provider_id){

        Provider provider = providerRepository.findProviderById(provider_id);
        if (provider == null){
            throw new ApiException("invalid provider");
        }
        List<Offer> acceptedOffers  = offersRepository.findAllByProviderIdAndStatusEquals(provider.getId(),"accepted");
        if (acceptedOffers.isEmpty()){
            throw new ApiException("sorry, there is no accepted offers now");
        }
        return acceptedOffers;
    }
    public List<Offer> viewMyPendingOffers(Integer provider_id){

        Provider provider = providerRepository.findProviderById(provider_id);
        if (provider == null){
            throw new ApiException("invalid provider");
        }
        List<Offer> pendingOffers  = offersRepository.findAllByProviderIdAndStatusEquals(provider.getId(),"pending");
        if (pendingOffers.isEmpty()){
            throw new ApiException("sorry, there is no pending offers now");
        }
        return pendingOffers;
    }

    public List<Offer> viewMyRejectedOffers(Integer provider_id){

        Provider provider = providerRepository.findProviderById(provider_id);
        if (provider == null){
            throw new ApiException("invalid provider");
        }
        List<Offer> rejectedOffers  = offersRepository.findAllByProviderIdAndStatusEquals(provider.getId(),"rejected");
        if (rejectedOffers.isEmpty()){
            throw new ApiException("there is no rejected");
        }
        return rejectedOffers;
    }


}
