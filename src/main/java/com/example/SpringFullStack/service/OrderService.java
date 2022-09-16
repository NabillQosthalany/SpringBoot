package com.example.SpringFullStack.service;

import com.example.SpringFullStack.DTO.Checkout.CheckoutItemDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;


    public Session createSession(List<CheckoutItemDTO> checkoutItemDTOList) throws StripeException {
        //Success and failure urls

        String successURL = baseURL + "payment/success";

        String failureURL = baseURL + "payment/failed";

    Stripe.apiKey = apiKey;

       List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();

        for (CheckoutItemDTO checkoutItemDTO: checkoutItemDTOList){
            sessionItemList.add(createSessionLineItem(checkoutItemDTO));
        }
        SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
               .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)

                .setCancelUrl(failureURL)
                .setSuccessUrl(successURL)
                .addAllLineItem(sessionItemList)
                .build();
                return Session.create(sessionCreateParams);

    }

    private SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDTO checkoutItemDTO) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(checkoutItemDTO))
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDTO.getQuantity())))
                .build();

    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDTO checkoutItemDTO) {

        return SessionCreateParams.LineItem.PriceData.builder()
               .setCurrency("usd")
              .setUnitAmount((long)checkoutItemDTO.getPrice()*100)
               .setProductData(
                      SessionCreateParams.LineItem.PriceData.ProductData.builder()
                               .setName(checkoutItemDTO.getProductName())
                               .build()
                ).build();
   }

}
