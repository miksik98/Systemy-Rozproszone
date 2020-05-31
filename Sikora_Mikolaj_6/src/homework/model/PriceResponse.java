package homework.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class PriceResponse extends PriceRequest {
    public enum ResponseType{
        NO_PRICES, OK
    }
    private ResponseType responseType;
    private int occurrences;
    private String productName;

    public PriceResponse(int value, ResponseType responseType, int occurrences, String productName) {
        super(value);
        this.responseType = responseType;
        this.occurrences = occurrences;
        this.productName = productName;
    }

    @Override
    public String toString() {
        if(occurrences>0){
            if(getResponseType() == PriceResponse.ResponseType.NO_PRICES)
                return String.format("No prices for '%s' | occurrences: %d", getProductName(), getOccurrences());
            else return String.format("Received price for product '%s': %d | occurrences: %d", getProductName(), getValue(), getOccurrences());
        }
        else{
            if(getResponseType() == PriceResponse.ResponseType.NO_PRICES)
                return String.format("No prices for '%s'", getProductName());
            else return String.format("Received price for product '%s': %d", getProductName(), getValue());

        }
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getProductName() {
        return productName;
    }

    public int getOccurrences() {
        return occurrences;
    }
}
