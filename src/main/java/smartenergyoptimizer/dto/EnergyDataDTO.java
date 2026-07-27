package smartenergyoptimizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class EnergyDataDTO {

    @NotBlank(message = "Device name is required")
    private String deviceName;

    @Positive(message = "Units must be greater than 0")
    private double unitsConsumed;

  

    @NotBlank(message = "Date is required")
    private String date;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public double getUnitsConsumed() {
        return unitsConsumed;
    }

    public void setUnitsConsumed(double unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
    }

   


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}